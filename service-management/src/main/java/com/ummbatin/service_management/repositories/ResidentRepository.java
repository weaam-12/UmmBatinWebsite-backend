package com.ummbatin.service_management.repositories;

import com.ummbatin.service_management.models.Property;
import com.ummbatin.service_management.models.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Integer> {
    Optional<Resident> findByEmail(String email);
    // Other resident-specific queries
}