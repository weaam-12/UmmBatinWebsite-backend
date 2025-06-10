package com.ummbatin.service_management.controllers;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.ummbatin.service_management.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeEvent(@RequestBody String payload,
                                                    @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook signature verification failed.");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            Session session;
            try {
                session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to deserialize session object");
            }

            if (session != null) {
                Map<String, String> metadata = session.getMetadata();
                String residentId = metadata != null ? metadata.get("resident_id") : null;
                String serviceId = metadata != null ? metadata.get("service_id") : null;
                String paymentIntentId = session.getPaymentIntent();

                // Call service method to update payment record based on Stripe event
                paymentService.markPaymentAsCompleted(residentId, serviceId, paymentIntentId);
            }
        }

        return ResponseEntity.ok("Received");
    }
}
