package com.ummbatin.service_management.controllers;

import com.stripe.exception.StripeException;
import com.ummbatin.service_management.dtos.PaymentRequestDto;
import com.ummbatin.service_management.models.Payment;
import com.ummbatin.service_management.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Integer id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        return paymentService.savePayment(payment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/resident/{residentId}")
    public List<Payment> getPaymentsByResidentId(@PathVariable Integer residentId) {
        return paymentService.getPaymentsByResidentId(residentId);
    }

    // New endpoint for frontend to create stripe checkout session and get payment link
    @PostMapping("/stripe/create-checkout-session")
    public ResponseEntity<String> createCheckoutSession(@RequestBody PaymentRequestDto dto) {
        try {
            String sessionUrl = paymentService.createStripeCheckoutSession(dto);
            return ResponseEntity.ok(sessionUrl);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Stripe session");
        }
    }
}
