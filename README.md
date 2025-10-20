# Alerting Notification Platform

## Project Overview

This Spring Boot backend manages organizational alerts and notifications. Admins create, update, archive, and schedule alerts for organization, team, or individual users. End users get relevant alerts, can snooze reminders, and set read/unread status. Recurring reminders continue until a user snoozes for the day or the alert expires.

---

## Features

- Admin: create/update/archive alerts; enable/disable reminders; filter and track alerts.
- End User: receive notifications (org/team/user audience); recurring reminders every 2 hours; snooze alerts; dashboard and history views; mark read/unread.
- PostgreSQL + Flyway for migrations.
- Scheduler logic for reminders and snooze expiry.
- Clean modular design (JPA, Lombok, Maven).

---

## Technology Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- Maven
- PostgreSQL (`AlertNotificationDB`)
- Flyway
- Lombok

---

## Setup Instructions

1. **Database Setup:**
   - Create a PostgreSQL database named `AlertNotificationDB`.
   - Note your DB username and password.

2. **Project Setup:**
   - Import the project into Eclipse or your preferred IDE as a Maven project.

3. **Configuration:**
   - Edit `src/main/resources/application.yaml` with your database connection info:
     ```
     spring:
       datasource:
         url: jdbc:postgresql://localhost:5432/AlertNotificationDB
         username: your_db_user
         password: your_db_password
       jpa:
         hibernate:
           ddl-auto: none
         show-sql: true
       flyway:
         enabled: true
         locations: classpath:db/migration
         baseline-on-migrate: true
     ```

4. **Run Migrations:**
   - Flyway will run migration scripts in `src/main/resources/db/migration` as serialized 
   version to prepare your schema.

5. **Run Application:**
   - Start your app by running `AlertingNotificationPlatformApplication.java`.
   - REST endpoints exposed via the embedded server.

---

## API Endpoints

### Admin

- `POST /api/admin/alerts` — Create alert
- `PUT /api/admin/alerts/{id}` — Update/Archive/Enable/Disable alert
- `GET /api/admin/alerts` — List active alerts
- `GET /api/admin/alerts/filter` — Filter by severity/status/audience
- `GET /api/admin/alerts/{id}/stats` — Alert delivery and snooze stats

### User

- `GET /api/user/{userId}/alerts` — Dashboard: active alerts for user
- `GET /api/user/{userId}/alerts/snoozed` — User’s snoozed alert history
- `POST /api/user/{userId}/alerts/{alertId}/snooze` — Snooze for today
- `POST /api/user/{userId}/alerts/{alertId}/read` — Mark alert as read

---

## Project Structure

- `/src/main/java/` — model, repository, service, controller, dto packages
- `/src/main/resources/` — Flyway migration scripts, `application.yaml`
- `pom.xml` — Maven setup

---

## Future Enhancements

- Authentication and authorization
- Multi-channel notifications
- Admin/User dashboards
- Analytics and reporting

---

## License

For educational or demonstration use.

