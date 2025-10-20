package com.notification.AlertingAndNotificationPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AlertingAndNotificationPlatformApplication {

	public static void main(String[] args) {
		System.out.println("Starting Alerting Notification App...");
		SpringApplication.run(AlertingAndNotificationPlatformApplication.class, args);
		System.out.println("End Alerting Notification App...");
	}

}
