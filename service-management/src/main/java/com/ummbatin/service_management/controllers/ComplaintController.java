package com.ummbatin.service_management.controllers;

import com.ummbatin.service_management.models.Complaint;
import com.ummbatin.service_management.services.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {
    @Autowired
    private ComplaintService complaintService;

    // Resident can view their complaints
    @GetMapping("/resident/{residentId}")
    @PreAuthorize("hasAnyRole('RESIDENT', 'ADMIN')")
    public List<Complaint> getComplaintsByResident(@PathVariable Long residentId) {
        return complaintService.getComplaintsByResidentId(residentId.intValue());
    }

    // Resident can create a complaint
    @PostMapping
    @PreAuthorize("hasAnyRole('RESIDENT', 'ADMIN')")
    public Complaint createComplaint(@RequestBody Complaint complaint) {
        return complaintService.createComplaint(complaint);
    }

    // Only Admin can update complaint status
    @PutMapping("/{complaintId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public Complaint updateComplaintStatus(@PathVariable Long complaintId, @RequestParam String status) {
        return complaintService.updateComplaintStatus(complaintId, status);
    }
}
