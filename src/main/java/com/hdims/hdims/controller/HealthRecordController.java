package com.hdims.hdims.controller;

import com.hdims.hdims.model.HealthRecord;
import com.hdims.hdims.service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/api/health-records")
public class HealthRecordController {
    
    @Autowired
    private HealthRecordService healthRecordService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<HealthRecord> addHealthRecord(@PathVariable Long userId, 
                                                        @Validated @RequestBody HealthRecord healthRecord) {
        HealthRecord createdRecord = healthRecordService.addHealthRecord(userId, healthRecord);
        return ResponseEntity.status(201).body(createdRecord);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HealthRecord>> getHealthRecordsByUser(@PathVariable Long userId) {
        List<HealthRecord> records = healthRecordService.getHealthRecordsByUserId(userId);
        return ResponseEntity.ok(records);
    }

    @PutMapping("/{recordId}")
    public ResponseEntity<HealthRecord> updateHealthRecord(@PathVariable Long recordId, 
                                                           @Validated @RequestBody HealthRecord healthRecord) {
        HealthRecord updatedRecord = healthRecordService.updateHealthRecord(recordId, healthRecord);
        return ResponseEntity.ok(updatedRecord);
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteHealthRecord(@PathVariable Long recordId) {
        healthRecordService.deleteHealthRecord(recordId);
        return ResponseEntity.noContent().build();
    }
}
