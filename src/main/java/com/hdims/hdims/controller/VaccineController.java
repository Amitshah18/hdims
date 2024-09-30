package com.hdims.hdims.controller;

import com.hdims.hdims.model.Vaccine;
import com.hdims.hdims.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/api/vaccines")
public class VaccineController {
    
    @Autowired
    private VaccineService vaccineService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<Vaccine> addVaccine(@PathVariable Long userId, 
                                            @Validated @RequestBody Vaccine vaccine) {
        Vaccine createdVaccine = vaccineService.addVaccine(userId, vaccine);
        return ResponseEntity.status(201).body(createdVaccine);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Vaccine>> getVaccinesByUser(@PathVariable Long userId) {
        List<Vaccine> vaccines = vaccineService.getVaccinesByUserId(userId);
        return ResponseEntity.ok(vaccines);
    }

    @PutMapping("/{vaccineId}")
    public ResponseEntity<Vaccine> updateVaccine(@PathVariable Long vaccineId, 
                                               @Validated @RequestBody Vaccine vaccine) {
        Vaccine updatedVaccine = vaccineService.updateVaccine(vaccineId, vaccine);
        return ResponseEntity.ok(updatedVaccine);
    }

    @DeleteMapping("/{vaccineId}")
    public ResponseEntity<Void> deleteVaccine(@PathVariable Long vaccineId) {
        vaccineService.deleteVaccine(vaccineId);
        return ResponseEntity.noContent().build();
    }
}
