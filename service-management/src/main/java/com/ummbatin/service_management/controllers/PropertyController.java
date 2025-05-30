package com.ummbatin.service_management.controllers;

import com.ummbatin.service_management.models.Property;
import com.ummbatin.service_management.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    @GetMapping("/resident/{residentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESIDENT')")
    public List<Property> getPropertiesByResident(@PathVariable Integer residentId) {
        return propertyService.getPropertiesByResidentId(residentId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Property createProperty(@RequestBody Property property) {
        return propertyService.createProperty(property);
    }
}