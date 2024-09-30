package com.hdims.hdims.service;

import com.hdims.hdims.model.Notification;
import com.hdims.hdims.model.User;
import com.hdims.hdims.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduledNotificationService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    // Example: Send daily health tips at 9 AM every day
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendDailyHealthTips() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            Notification notification = Notification.builder()
                    .title("Daily Health Tip")
                    .message("Stay hydrated and exercise regularly!")
                    .user(user)
                    .timestamp(LocalDateTime.now())
                    .build();
            notificationService.createNotification(user.getId(), notification);
        }
    }
}
