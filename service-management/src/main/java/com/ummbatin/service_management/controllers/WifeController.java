package com.ummbatin.service_management.controllers;

import com.ummbatin.service_management.models.Wife;
import com.ummbatin.service_management.services.WifeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wives")
public class WifeController {
    @Autowired
    private WifeService wifeService;

    @GetMapping("/resident/{residentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESIDENT')")
    public List<Wife> getWivesByResident(@PathVariable Long residentId) {
        return wifeService.getWivesByResidentId(residentId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Wife createWife(@RequestBody Wife wife) {
        return wifeService.createWife(wife);
    }
}
