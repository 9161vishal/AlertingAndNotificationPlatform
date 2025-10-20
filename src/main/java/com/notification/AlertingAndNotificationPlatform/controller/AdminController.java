package com.notification.AlertingAndNotificationPlatform.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notification.AlertingAndNotificationPlatform.model.Alert;
import com.notification.AlertingAndNotificationPlatform.model.AudienceType;
import com.notification.AlertingAndNotificationPlatform.model.Severity;
import com.notification.AlertingAndNotificationPlatform.service.AlertService;



@RestController
@RequestMapping("/api/admin/alerts")
public class AdminController {
		@Autowired
	    private AlertService alertService;
	    

	    @PostMapping
	    public Alert createAlert(@RequestBody Alert alert) {
	    		System.out.println(alert);
	        return alertService.createAlert(alert);
	    }

	    @GetMapping
	    public List<Alert> getAllAlerts() {
	        return alertService.getActiveAlerts();
	    }
	    
	    @PutMapping("/{alertId}")
	    public Alert updateAlert(@PathVariable Long alertId, @RequestBody Alert updatedAlert) {
	        return alertService.updateAlert(alertId, updatedAlert);
	    }

	    @GetMapping("/filter")
	    public List<Alert> filterAlerts(
	            @RequestParam(required = false) Severity severity,
	            @RequestParam(required = false) Boolean active,
	            @RequestParam(required = false) AudienceType audienceType) {
	        return alertService.filterAlerts(severity, active, audienceType);
	    }

	    @GetMapping("/{alertId}/stats")
	    public Map<String, Long> getAlertSnoozeStats(@PathVariable Long alertId) {
	        return alertService.getAlertSnoozeStats(alertId);
	    }
	    
	
}
