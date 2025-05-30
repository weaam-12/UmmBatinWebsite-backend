package com.ummbatin.service_management.repositories;

import com.ummbatin.service_management.models.WaterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WaterReadingRepository extends JpaRepository<WaterReading, Long> {

    // Option 1: Using property path (recommended)
    List<WaterReading> findByProperty_PropertyId(Long propertyId);

    // Option 2: Using @Query (alternative)
    // @Query("SELECT w FROM WaterReading w WHERE w.property.propertyId = :propertyId")
    // List<WaterReading> findByPropertyId(@Param("propertyId") Long propertyId);
}