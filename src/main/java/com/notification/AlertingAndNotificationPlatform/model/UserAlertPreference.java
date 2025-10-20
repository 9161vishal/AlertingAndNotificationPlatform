package com.notification.AlertingAndNotificationPlatform.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class UserAlertPreference {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

	 @ManyToOne
	 private User user;

	 @ManyToOne
	 private Alert alert;

	 @Enumerated(EnumType.STRING)
	 private Status status = Status.UNREAD;

	 private LocalDate snoozedDay;
}
