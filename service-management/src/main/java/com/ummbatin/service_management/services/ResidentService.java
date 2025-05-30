package com.ummbatin.service_management.services;

import com.ummbatin.service_management.models.Resident;
import com.ummbatin.service_management.repositories.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResidentService {
    @Autowired
    private ResidentRepository residentRepository;

    public Resident createResident(Resident resident) {
        return residentRepository.save(resident);
    }

    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    public Optional<Resident> getResidentByEmail(String email) {
        return residentRepository.findByEmail(email);
    }

    // Changed from Long to Integer
    public Resident updateResident(Integer id, Resident updatedResident) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resident not found"));
        resident.setName(updatedResident.getName());
        resident.setEmail(updatedResident.getEmail());
        resident.setNationalId(updatedResident.getNationalId());
        return residentRepository.save(resident);
    }

    // Changed from Long to Integer
    public void deleteResident(Integer id) {
        residentRepository.deleteById(id);
    }
}