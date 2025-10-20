package com.notification.AlertingAndNotificationPlatform.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.notification.AlertingAndNotificationPlatform.model.Alert;
import com.notification.AlertingAndNotificationPlatform.model.User;
import com.notification.AlertingAndNotificationPlatform.model.UserAlertPreference;
import com.notification.AlertingAndNotificationPlatform.repository.UserAlertPreferenceRepository;
import com.notification.AlertingAndNotificationPlatform.repository.UserRepository;

@Component
public class ReminderScheduler {
		@Autowired
	 	private AlertService alertService;
		
		@Autowired
	    private UserRepository userRepository;
		
		@Autowired
	    private NotificationService notificationService;
		
		@Autowired
	    private UserAlertPreferenceRepository preferenceRepository;

	    // Schedule task for every 2 hours for reminders
	    @Scheduled(fixedRate = 7200000)
	    public void sendReminders() {
	        List<Alert> alerts = alertService.getActiveAlerts();

	        for (Alert alert : alerts) {
	            // For simplicity, get all users; extend logic for audience filtering
	            List<User> users = getUsersForAlert(alert);

	            for (User user : users) {
	                UserAlertPreference pref = preferenceRepository.findByUserAndAlert(user, alert).orElse(null);

	                if (pref == null || hasSnoozeExpired(pref)) {
	                    notificationService.deliver(alert, user);
	                }
	            }
	        }
	    }
	    
	    // Helper method: fetch users based on alert audience type
	    private List<User> getUsersForAlert(Alert alert) {
	        switch (alert.getAudienceType()) {
	            case ORGANIZATION:
	                // For simplicity, assume all users belong to the org
	                return userRepository.findAll();

	            case TEAM:
	                Long teamId;
	                try {
	                    teamId = Long.parseLong(alert.getAudienceId());
	                } catch (NumberFormatException e) {
	                    return List.of();  // no users if invalid team id
	                }
	                return userRepository.findAll().stream()
	                        .filter(u -> teamId.equals(u.getTeamId()))
	                        .collect(Collectors.toList());

	            case USER:
	                Long userId;
	                try {
	                    userId = Long.parseLong(alert.getAudienceId());
	                } catch (NumberFormatException e) {
	                    return List.of();  // no users if invalid user id
	                }
	                return userRepository.findById(userId).map(List::of).orElse(List.of());

	            default:
	                return List.of();
	        }
	    }

	    // 24-hour snooze expiration logic/auto reset
	    private boolean hasSnoozeExpired(UserAlertPreference pref) {
	        if (pref.getSnoozedDay() == null) return true;
	        return !LocalDate.now().isEqual(pref.getSnoozedDay());
	    }
}
