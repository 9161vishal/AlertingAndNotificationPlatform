package com.notification.AlertingAndNotificationPlatform.service;

import org.springframework.stereotype.Service;

import com.notification.AlertingAndNotificationPlatform.model.Alert;
import com.notification.AlertingAndNotificationPlatform.model.User;

@Service
public class NotificationService {
	public void deliver(Alert alert, User user) {
        // Placeholder for actual delivery (In-app, email, push)
        System.out.println("Sending alert '" + alert.getTitle() + "' to user " + user.getName());
    }
}
