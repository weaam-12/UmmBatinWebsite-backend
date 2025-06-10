package com.ummbatin.service_management.services;

import com.ummbatin.service_management.models.Wife;
import com.ummbatin.service_management.repositories.WifeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WifeService {
    @Autowired
    private WifeRepository wifeRepository;

    public List<Wife> getWivesByResidentId(Long residentId) {
        return wifeRepository.findByResident_ResidentId(residentId.intValue());
    }

    public Wife createWife(Wife wife) {
        return wifeRepository.save(wife);
    }
}