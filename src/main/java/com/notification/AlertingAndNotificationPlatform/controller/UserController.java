package com.notification.AlertingAndNotificationPlatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.AlertingAndNotificationPlatform.dto.AlertWithStatus;
import com.notification.AlertingAndNotificationPlatform.model.Alert;
import com.notification.AlertingAndNotificationPlatform.model.User;
import com.notification.AlertingAndNotificationPlatform.service.AlertService;
import com.notification.AlertingAndNotificationPlatform.service.UserAlertPreferenceService;
import com.notification.AlertingAndNotificationPlatform.service.UserAlertService;
import com.notification.AlertingAndNotificationPlatform.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
    private UserService userService;
	
	@Autowired
    private AlertService alertService;
	
	@Autowired
    private UserAlertPreferenceService preferenceService;
	
	@Autowired
    private UserAlertService userAlertService;
	
    @GetMapping("/{userId}/alerts")
    public List<AlertWithStatus> getUserActiveAlerts(@PathVariable Long userId) {
        return userAlertService.getUserActiveAlerts(userId);
    }
    
    @GetMapping("/{userId}/alerts/snoozed")
    public List<Alert> getUserSnoozedAlerts(@PathVariable Long userId) {
        return userAlertService.getUserSnoozedAlerts(userId);
    }

    @PostMapping("/{userId}/alerts/{alertId}/snooze")
    public void snoozeAlert(@PathVariable Long userId, @PathVariable Long alertId) {
        User user = userService.getUserById(userId);
        Alert alert = alertService.getAlertById(alertId);
        preferenceService.snooze(user, alert);
    }

    @PostMapping("/{userId}/alerts/{alertId}/read")
    public void markRead(@PathVariable Long userId, @PathVariable Long alertId) {
        User user = userService.getUserById(userId);
        Alert alert = alertService.getAlertById(alertId);
        preferenceService.markRead(user, alert);
    }
}
