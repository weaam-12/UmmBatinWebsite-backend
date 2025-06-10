package com.ummbatin.service_management.repositories;

import com.ummbatin.service_management.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByResident_ResidentId(Integer residentId);
    List<Payment> findByProperty_PropertyId(Integer propertyId);
    List<Payment> findByStatus(String status);
    Optional<Payment> findByResident_ResidentIdAndServiceIdAndTransactionId(
            Integer residentId,
            Long serviceId,
            String transactionId
    );


}
