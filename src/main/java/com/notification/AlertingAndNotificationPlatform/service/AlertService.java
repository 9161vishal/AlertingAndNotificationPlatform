package com.notification.AlertingAndNotificationPlatform.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notification.AlertingAndNotificationPlatform.model.Alert;
import com.notification.AlertingAndNotificationPlatform.model.AudienceType;
import com.notification.AlertingAndNotificationPlatform.model.Severity;
import com.notification.AlertingAndNotificationPlatform.model.Status;
import com.notification.AlertingAndNotificationPlatform.model.UserAlertPreference;
import com.notification.AlertingAndNotificationPlatform.repository.AlertRepository;
import com.notification.AlertingAndNotificationPlatform.repository.UserAlertPreferenceRepository;

@Service
public class AlertService {
	@Autowired
	 private AlertRepository alertRepository;
	
	@Autowired
	private UserAlertPreferenceRepository userAlertPreferenceRepository;

	 public Alert createAlert(Alert alert) {
	     alert.setActive(true);
	     return alertRepository.save(alert);
	 }

	 public List<Alert> getActiveAlerts() {
	     return alertRepository.findAll().stream()
	             .filter(Alert::isActive)
	             .collect(Collectors.toList());
	 }

	 public Alert getAlertById(Long id) {
	     return alertRepository.findById(id).orElse(null);
	 }
	 
	 public Alert updateAlert(Long alertId, Alert updatedAlert) {
		    Alert alert = alertRepository.findById(alertId)
		                     .orElseThrow(() -> new RuntimeException("Alert not found: " + alertId));
		    alert.setTitle(updatedAlert.getTitle());
		    alert.setMessage(updatedAlert.getMessage());
		    alert.setSeverity(updatedAlert.getSeverity());
		    alert.setStartTime(updatedAlert.getStartTime());
		    alert.setExpiryTime(updatedAlert.getExpiryTime());
		    alert.setReminderFrequencyHours(updatedAlert.getReminderFrequencyHours());
		    alert.setAudienceType(updatedAlert.getAudienceType());
		    alert.setAudienceId(updatedAlert.getAudienceId());
		    alert.setActive(updatedAlert.isActive()); // toggle active/archived status
		    return alertRepository.save(alert);
		}
	 
	 public List<Alert> filterAlerts(Severity severity, Boolean active, AudienceType audienceType) {
		    return alertRepository.findAll().stream()
		            .filter(a -> severity == null || a.getSeverity() == severity)
		            .filter(a -> active == null || a.isActive() == active)
		            .filter(a -> audienceType == null || a.getAudienceType() == audienceType)
		            .collect(Collectors.toList());
		}
	 
	 public Map<String, Long> getAlertSnoozeStats(Long alertId) {
		    Alert alert = alertRepository.findById(alertId)
		            .orElseThrow(() -> new RuntimeException("Alert not found: " + alertId));
		    List<UserAlertPreference> prefs = userAlertPreferenceRepository.findByAlertAndStatus(alert, Status.UNREAD);
		    long snoozedCount = prefs.stream()
		            .filter(pref -> pref.getSnoozedDay() != null && pref.getSnoozedDay().equals(LocalDate.now()))
		            .count();
		    long total = prefs.size();
		    return Map.of(
		            "totalUsers", total,
		            "snoozedToday", snoozedCount,
		            "recurring", total - snoozedCount
		    );
		}

}
