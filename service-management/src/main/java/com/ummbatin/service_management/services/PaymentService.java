package com.ummbatin.service_management.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.billingportal.Session;
import com.ummbatin.service_management.dtos.PaymentRequestDto;
import com.ummbatin.service_management.models.Payment;
import com.ummbatin.service_management.models.Property;
import com.ummbatin.service_management.models.Resident;
import com.ummbatin.service_management.repositories.PaymentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Stripe API key should be set in application.properties and injected
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Integer id) {
        return paymentRepository.findById(id);
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void deletePayment(Integer id) {
        paymentRepository.deleteById(id);
    }

    public List<Payment> getPaymentsByResidentId(Integer residentId) {
        return paymentRepository.findByResident_ResidentId(residentId);
    }

    // 1. Create Stripe checkout session & save payment with status PENDING
    public String createStripeCheckoutSession(PaymentRequestDto dto) throws StripeException {
        // Prepare Stripe params
        Map<String, Object> params = new HashMap<>();
        params.put("payment_method_types", List.of("card"));
        params.put("mode", "payment");
        params.put("success_url", dto.getSuccessUrl());
        params.put("cancel_url", dto.getCancelUrl());

        List<Object> lineItems = new ArrayList<>();
        Map<String, Object> lineItem = new HashMap<>();
        lineItem.put("price_data", Map.of(
                "currency", "usd",
                "product_data", Map.of("name", "Service Payment"),
                "unit_amount", Math.round(dto.getAmount() * 100) // convert to cents
        ));
        lineItem.put("quantity", 1);
        lineItems.add(lineItem);

        params.put("line_items", lineItems);

        // Add metadata to link payment with resident, service etc.
        Map<String, String> metadata = new HashMap<>();
        metadata.put("resident_id", dto.getResidentId().toString());
        metadata.put("service_id", dto.getServiceId().toString());
        params.put("metadata", metadata);

        // Create Stripe checkout session
        Session session = Session.create(params);

        // Save payment in DB with status PENDING and paymentLink as session url
        Payment payment = new Payment();

        // You must load Resident and Property entities by id here (inject ResidentService/PropertyService or repositories)
        Resident resident = new Resident(); // load real resident entity by dto.getResidentId()
        resident.setResidentId((long) dto.getResidentId().intValue());
        payment.setResident(resident);

        Property property = new Property(); // load real property entity by dto.getPropertyId()
        property.setPropertyId(dto.getPropertyId().intValue());
        payment.setProperty(property);

        payment.setServiceId(dto.getServiceId());
        payment.setAmount(dto.getAmount());
        payment.setDate(LocalDate.now());
        payment.setStatus("PENDING");
        payment.setPaymentLink(session.getUrl());

        paymentRepository.save(payment);

        return session.getUrl();
    }

    // 2. Update payment status on webhook success
    public void markPaymentAsCompleted(String residentId, String serviceId, String paymentIntentId) {
        List<Payment> payments = paymentRepository.findByResident_ResidentId(Integer.parseInt(residentId));
        for (Payment payment : payments) {
            if (payment.getServiceId().toString().equals(serviceId) && "PENDING".equals(payment.getStatus())) {
                payment.setStatus("COMPLETED");
                payment.setTransactionId(paymentIntentId);
                payment.setPaymentDate(LocalDateTime.now());
                paymentRepository.save(payment);
                break;
            }
        }
    }
}
