package com.ummbatin.service_management.controllers;

import com.ummbatin.service_management.models.Child;
import com.ummbatin.service_management.services.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/children")
public class ChildController {
    @Autowired
    private ChildService childService;

    @GetMapping("/resident/{residentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESIDENT')")
    public List<Child> getChildrenByResident(@PathVariable Integer residentId) {  // Changed from Long to Integer
        return childService.getChildrenByResidentId(residentId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Child createChild(@RequestBody Child child) {
        return childService.createChild(child);
    }
}