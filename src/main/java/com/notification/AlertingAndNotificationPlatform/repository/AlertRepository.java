package com.notification.AlertingAndNotificationPlatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notification.AlertingAndNotificationPlatform.model.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long>{

}
