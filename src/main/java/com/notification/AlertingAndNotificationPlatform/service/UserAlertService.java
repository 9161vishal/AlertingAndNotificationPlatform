package com.notification.AlertingAndNotificationPlatform.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notification.AlertingAndNotificationPlatform.dto.AlertWithStatus;
import com.notification.AlertingAndNotificationPlatform.model.Alert;
import com.notification.AlertingAndNotificationPlatform.model.Status;
import com.notification.AlertingAndNotificationPlatform.model.User;
import com.notification.AlertingAndNotificationPlatform.model.UserAlertPreference;
import com.notification.AlertingAndNotificationPlatform.repository.AlertRepository;
import com.notification.AlertingAndNotificationPlatform.repository.UserAlertPreferenceRepository;
import com.notification.AlertingAndNotificationPlatform.repository.UserRepository;

@Service
public class UserAlertService {
	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private AlertRepository alertRepository;

	    @Autowired
	    private UserAlertPreferenceRepository preferenceRepository;

	    public List<AlertWithStatus> getUserActiveAlerts(Long userId) {
	        User user = userRepository.findById(userId).orElse(null);
	        if (user == null) return List.of();

	        List<Alert> alerts = alertRepository.findAll().stream()
	                .filter(Alert::isActive)
	                .filter(alert -> {
	                    if (alert.getAudienceType() == null) return false;
	                    switch (alert.getAudienceType()) {
	                        case ORGANIZATION:
	                            return true;
	                        case TEAM:
	                            return alert.getAudienceId() != null && alert.getAudienceId().equals(String.valueOf(user.getTeamId()));
	                        case USER:
	                            return alert.getAudienceId() != null && alert.getAudienceId().equals(String.valueOf(user.getId()));
	                        default:
	                            return false;
	                    }
	                }).collect(Collectors.toList());

	        List<AlertWithStatus> result = new ArrayList<>();
	        for (Alert alert : alerts) {
	            UserAlertPreference pref = preferenceRepository.findByUserAndAlert(user, alert).orElse(null);
	            boolean snoozed = pref != null && pref.getSnoozedDay() != null && pref.getSnoozedDay().isEqual(LocalDate.now());
	            Status status = (pref != null) ? pref.getStatus() : Status.UNREAD;
	            result.add(new AlertWithStatus(alert, status, snoozed));
	        }

	        return result;
	    }

	    public List<Alert> getUserSnoozedAlerts(Long userId) {
	        User user = userRepository.findById(userId).orElse(null);
	        if (user == null) return List.of();

	        List<UserAlertPreference> prefs = preferenceRepository.findAll().stream()
	                .filter(p -> p.getUser().equals(user) && p.getSnoozedDay() != null)
	                .collect(Collectors.toList());

	        return prefs.stream()
	                    .map(UserAlertPreference::getAlert)
	                    .collect(Collectors.toList());
	    }
}
