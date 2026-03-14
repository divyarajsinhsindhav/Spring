package com.tss.springcore.basics.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

@Configurable
@ComponentScan(basePackages = "com.tss.springcore.basics")
public class AppConfig {
}
