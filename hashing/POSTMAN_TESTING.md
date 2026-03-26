# Postman Testing Guide: Secure PAN Identity Service

This guide provides step-by-step instructions on how to test the REST API endpoints using Postman. Ensure that your **Spring Boot application** is running (listening on port `8080`) and your **PostgreSQL database** is active before proceeding.

## General Setup

The application uses state-less JWT authentication. Most endpoints require an `Authorization` header with a valid token. We will first retrieve a token from the `Auth` controller and use it for subsequent requests.

To make testing easier:
1. Open Postman.
2. In the top right corner, click on an **Environment** (or create a new one).
3. Set a new variable `JWT_TOKEN`. We will populate this once we log in.

---

### Step 1: Authentication (Login)
Retrieve the JWT token which gives you access to the Identity APIs.

* **Method:** `POST`
* **URL:** `http://localhost:8080/api/v1/auth/login`
* **Headers:**
  * `Content-Type`: `application/json`
* **Body (raw, JSON):**
  ```json
  {
    "username": "testuser",
    "password": "password123"
  }
  ```
* **Steps after execution:** 
  1. Once you send this request, you will receive a response body containing `data.token`.
  2. Copy that `token`.
  3. (Optional) In Postman environments, paste the token value into the `JWT_TOKEN` variable you created. For the rest of the requests, you can use `Bearer {{JWT_TOKEN}}` in the Authorization header. Otherwise, paste it manually as `Bearer <token>`.

---

### Step 2: Submit a PAN Card
Save your PAN data to the system. The PAN will be hashed and logically stored.

* **Method:** `POST`
* **URL:** `http://localhost:8080/api/v1/identity/pan`
* **Headers:**
  * `Authorization`: `Bearer <your_token_here>` *(or `Bearer {{JWT_TOKEN}}`)*
  * `Content-Type`: `application/json`
* **Body (raw, JSON):**
  ```json
  {
    "pan": "ABCDE1234F"
  }
  ```
  *(Ensure the value matches the pattern: 5 uppercase letters, 4 digits, 1 uppercase letter)*
* **Expected Response:** 
  A `201 Created` status with the system-generated `personId` UUID and the masked PAN. **Copy the `personId`** from the response for Step 5!

---

### Step 3: Retrieve Masked PAN
Fetch the masked version of the PAN associated with your user session.

* **Method:** `GET`
* **URL:** `http://localhost:8080/api/v1/identity/pan`
* **Headers:**
  * `Authorization`: `Bearer <your_token_here>`
* **Expected Response:** 
  A `200 OK` status with the currently active user's `maskedPan`.

---

### Step 4: Verify PAN Data
Check if a plain-text PAN correctly matches the securely hashed cryptographic record stored for your profile.

* **Method:** `POST`
* **URL:** `http://localhost:8080/api/v1/identity/pan/verify`
* **Headers:**
  * `Authorization`: `Bearer <your_token_here>`
  * `Content-Type`: `application/json`
* **Body (raw, JSON):**
  ```json
  {
    "pan": "ABCDE1234F"
  }
  ```
* **Expected Response:**
  A boolean value of `true` if the submitted PAN matches the hashed record, and `false` if it does not.

---

### Step 5: Soft Delete PAN Record
Logically soft-delete a saved PAN record, maintaining the record in the database but making it inactive and inaccessible.

* **Method:** `DELETE`
* **URL:** `http://localhost:8080/api/v1/identity/{personId}?reason=closing_account`
  *(Replace `{personId}` with the UUID you obtained from the response in Step 2)*
* **Headers:**
  * `Authorization`: `Bearer <your_token_here>`
* **Expected Response:**
  A `200 OK` status confirming the PAN record has been deleted locally (soft-delete).
