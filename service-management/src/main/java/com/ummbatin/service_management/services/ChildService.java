package com.ummbatin.service_management.services;

import com.ummbatin.service_management.models.Child;
import com.ummbatin.service_management.repositories.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildService {
    @Autowired
    private ChildRepository childRepository;

    public List<Child> getChildrenByResidentId(Integer residentId) {  // Changed from Long to Integer
        return childRepository.findByResident_ResidentId(residentId);  // Updated method call
    }

    public Child createChild(Child child) {
        return childRepository.save(child);
    }
}