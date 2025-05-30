package com.ummbatin.service_management.repositories;

import com.ummbatin.service_management.models.Wife;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WifeRepository extends JpaRepository<Wife, Long> {
    // Correct method using the proper property path
    List<Wife> findByResident_ResidentId(Integer residentId);

    // Alternative using @Query
    // @Query("SELECT w FROM Wife w WHERE w.resident.residentId = :residentId")
    // List<Wife> findByResidentId(@Param("residentId") Integer residentId);
}