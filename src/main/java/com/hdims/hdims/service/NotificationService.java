package com.hdims.hdims.service;

import com.hdims.hdims.model.Notification;
import com.hdims.hdims.model.User;
import com.hdims.hdims.repository.NotificationRepository;
import com.hdims.hdims.repository.UserRepository;
import com.hdims.hdims.util.EmailService;
import com.hdims.hdims.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public Notification createNotification(Long userId, Notification notification) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        notification.setUser(user);
        notification.setTimestamp(LocalDateTime.now());
        Notification savedNotification = notificationRepository.save(notification);
        
        // Send email notification (optional)
        emailService.sendEmail(user.getEmail(), notification.getTitle(), notification.getMessage());
        
        return savedNotification;
    }

    public List<Notification> getNotificationsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return notificationRepository.findByUser(user);
    }
}
