package com.hdims.hdims.service;

import com.hdims.hdims.model.HealthRecord;
import com.hdims.hdims.model.User;
import com.hdims.hdims.repository.HealthRecordRepository;
import com.hdims.hdims.repository.UserRepository;
import com.hdims.hdims.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class HealthRecordService {
    
    @Autowired
    private HealthRecordRepository healthRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public HealthRecord addHealthRecord(Long userId, HealthRecord healthRecord) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        healthRecord.setUser(user);
        return healthRecordRepository.save(healthRecord);
    }

    public List<HealthRecord> getHealthRecordsByUserId(Long userId) {
        return healthRecordRepository.findByUserId(userId);
    }

    @Transactional
    public HealthRecord updateHealthRecord(Long recordId, HealthRecord updatedRecord) {
        HealthRecord existingRecord = healthRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("HealthRecord not found with id: " + recordId));
        existingRecord.setDisease(updatedRecord.getDisease());
        existingRecord.setTreatment(updatedRecord.getTreatment());
        return healthRecordRepository.save(existingRecord);
    }

    @Transactional
    public void deleteHealthRecord(Long recordId) {
        HealthRecord existingRecord = healthRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("HealthRecord not found with id: " + recordId));
        healthRecordRepository.delete(existingRecord);
    }
}
