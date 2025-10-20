package com.notification.AlertingAndNotificationPlatform.dto;

import com.notification.AlertingAndNotificationPlatform.model.Alert;
import com.notification.AlertingAndNotificationPlatform.model.Status;

public class AlertWithStatus {
	private Alert alert;
    private Status status;
    private boolean snoozed;

    public AlertWithStatus(Alert alert, Status status, boolean snoozed) {
        this.alert = alert;
        this.status = status;
        this.snoozed = snoozed;
    }

    public Alert getAlert() {
        return alert;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isSnoozed() {
        return snoozed;
    }
}
