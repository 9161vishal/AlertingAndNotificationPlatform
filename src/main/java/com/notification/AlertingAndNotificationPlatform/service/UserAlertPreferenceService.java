package com.notification.AlertingAndNotificationPlatform.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notification.AlertingAndNotificationPlatform.model.Alert;
import com.notification.AlertingAndNotificationPlatform.model.Status;
import com.notification.AlertingAndNotificationPlatform.model.User;
import com.notification.AlertingAndNotificationPlatform.model.UserAlertPreference;
import com.notification.AlertingAndNotificationPlatform.repository.UserAlertPreferenceRepository;


@Service
public class UserAlertPreferenceService {
	@Autowired
	private UserAlertPreferenceRepository repository;

    public void snooze(User user, Alert alert) {
        UserAlertPreference pref = repository.findByUserAndAlert(user, alert)
                .orElse(new UserAlertPreference());
        pref.setUser(user);
        pref.setAlert(alert);
        pref.setSnoozedDay(LocalDate.now());
        pref.setStatus(pref.getStatus() != null ? pref.getStatus() : Status.UNREAD);
        repository.save(pref);
    }

    public void markRead(User user, Alert alert) {
        UserAlertPreference pref = repository.findByUserAndAlert(user, alert)
                .orElse(new UserAlertPreference());
        pref.setUser(user);
        pref.setAlert(alert);
        pref.setStatus(Status.READ);
        repository.save(pref);
    }

    public List<User> getUsersByAlertAndStatus(Alert alert, Status status) {
        return repository.findByAlertAndStatus(alert, status).stream()
                .map(UserAlertPreference::getUser)
                .collect(Collectors.toList());
    }
}
