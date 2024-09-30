package com.hdims.hdims.controller;

import com.hdims.hdims.model.Notification;
import com.hdims.hdims.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<Notification> createNotification(@PathVariable Long userId, 
                                                           @Validated @RequestBody Notification notification) {
        Notification createdNotification = notificationService.createNotification(userId, notification);
        return ResponseEntity.status(201).body(createdNotification);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUser(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }
}
