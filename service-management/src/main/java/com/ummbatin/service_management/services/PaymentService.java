package com.ummbatin.service_management.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import com.ummbatin.service_management.models.Payment;
import com.ummbatin.service_management.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;


    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public PaymentIntent createPaymentIntent(Long amountInCents) throws StripeException {
        // Set API key for Stripe
        Stripe.apiKey = stripeApiKey;

        // Create parameters for the payment intent
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)            // amount in cents
                .setCurrency("usd")
                .addPaymentMethodType("card")       // accept card payments
                .build();

        // Create the PaymentIntent object
        return PaymentIntent.create(params);

    }

    public void markPaymentAsCompleted(String residentId, String serviceId, String paymentIntentId) {
        Optional<Payment> optionalPayment = paymentRepository.findByResident_ResidentIdAndServiceIdAndTransactionId(
                Integer.parseInt(residentId),
                Long.parseLong(serviceId),
                paymentIntentId
        );


        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            payment.setStatus("COMPLETED");
            payment.setPaymentDate(LocalDateTime.now());
            paymentRepository.save(payment);
        } else {
            System.out.println("Payment not found for residentId: " + residentId);
        }
    }


}
