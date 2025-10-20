package com.notification.AlertingAndNotificationPlatform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notification.AlertingAndNotificationPlatform.model.Alert;
import com.notification.AlertingAndNotificationPlatform.model.Status;
import com.notification.AlertingAndNotificationPlatform.model.User;
import com.notification.AlertingAndNotificationPlatform.model.UserAlertPreference;

public interface UserAlertPreferenceRepository extends JpaRepository<UserAlertPreference, Long>{
	Optional<UserAlertPreference> findByUserAndAlert(User user, Alert alert);
    List<UserAlertPreference> findByAlertAndStatus(Alert alert, Status status);
}
