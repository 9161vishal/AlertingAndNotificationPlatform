package com.notification.AlertingAndNotificationPlatform.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Alert {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String title;

	    @Column(columnDefinition = "TEXT")
	    private String message;

	    @Enumerated(EnumType.STRING)
	    private Severity severity;

	    private LocalDateTime startTime;
	    private LocalDateTime expiryTime;

	    private int reminderFrequencyHours;

	    @Enumerated(EnumType.STRING)
	    private AudienceType audienceType;

	    private String audienceId;

	    private boolean active = true;
	}