package com.hdims.hdims.service;

import com.hdims.hdims.model.User;
import com.hdims.hdims.model.Vaccine;
import com.hdims.hdims.repository.UserRepository;
import com.hdims.hdims.repository.VaccineRepository;
import com.hdims.hdims.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class VaccineService {
    
    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Vaccine addVaccine(Long userId, Vaccine vaccine) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        vaccine.setUser(user);
        return vaccineRepository.save(vaccine);
    }

    public List<Vaccine> getVaccinesByUserId(Long userId) {
        return vaccineRepository.findByUserId(userId);
    }

    @Transactional
    public Vaccine updateVaccine(Long vaccineId, Vaccine updatedVaccine) {
        Vaccine existingVaccine = vaccineRepository.findById(vaccineId)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccine not found with id: " + vaccineId));
        existingVaccine.setName(updatedVaccine.getName());
        existingVaccine.setDoseNumber(updatedVaccine.getDoseNumber());
        existingVaccine.setDateAdministered(updatedVaccine.getDateAdministered());
        return vaccineRepository.save(existingVaccine);
    }

    @Transactional
    public void deleteVaccine(Long vaccineId) {
        Vaccine existingVaccine = vaccineRepository.findById(vaccineId)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccine not found with id: " + vaccineId));
        vaccineRepository.delete(existingVaccine);
    }
}
