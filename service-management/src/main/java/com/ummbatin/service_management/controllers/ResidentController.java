package com.ummbatin.service_management.controllers;

import com.ummbatin.service_management.models.Resident;
import com.ummbatin.service_management.services.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Resident createResident(@RequestBody Resident resident) {
        return residentService.createResident(resident);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Resident> getAllResidents() {
        return residentService.getAllResidents();
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('RESIDENT', 'ADMIN')")
    public Resident getResidentProfile(@RequestParam String email) {
        return residentService.getResidentByEmail(email)
                .orElseThrow(() -> new RuntimeException("Resident not found"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Resident updateResident(@PathVariable Long id, @RequestBody Resident resident) {
        return residentService.updateResident(id, resident);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteResident(@PathVariable Long id) {
        residentService.deleteResident(id);
    }
}