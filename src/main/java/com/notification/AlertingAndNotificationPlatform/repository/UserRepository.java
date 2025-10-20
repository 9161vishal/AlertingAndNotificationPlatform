package com.notification.AlertingAndNotificationPlatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notification.AlertingAndNotificationPlatform.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
