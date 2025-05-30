package com.ummbatin.service_management.services;

import com.ummbatin.service_management.models.Property;
import com.ummbatin.service_management.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    public List<Property> getPropertiesByResidentId(Integer residentId) {  // Change to Integer
        return propertyRepository.findByResident_ResidentId(residentId);  // Update method name
    }

    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }
}