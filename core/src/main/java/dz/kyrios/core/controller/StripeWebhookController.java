package dz.kyrios.core.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionListParams;
import dz.kyrios.core.entity.Payment;
import dz.kyrios.core.service.StudentSubscriptionService;
import dz.kyrios.core.statics.PaymentStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@RestController
@RequestMapping("/webhooks")
public class StripeWebhookController {

    @Value("${stripe.secret.webhook}")
    private String stripeSecretWebhook;

    private final StudentSubscriptionService studentSubscriptionService;

    public StripeWebhookController(StudentSubscriptionService studentSubscriptionService) {
        this.studentSubscriptionService = studentSubscriptionService;
    }

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            // Verify the event
            Event event = Webhook.constructEvent(payload, sigHeader, stripeSecretWebhook);

            // Initialize variables
            String subscriptionId = null;
            PaymentIntent paymentIntent = null;
            Session session = null;
            Payment payment = null;
            LocalDateTime paymentTime = null;

            // Handle different event types
            switch (event.getType()) {
                case "checkout.session.completed":
                    log.info("checkout session completed");
                    break;

                case "payment_intent.succeeded":
                    paymentIntent = (PaymentIntent) event.getData().getObject();
                    session = Session.list(SessionListParams.builder()
                            .setPaymentIntent(paymentIntent.getId())
                            .build()).getData().get(0); // Retrieve the session associated with the PaymentIntent
                    subscriptionId = session.getMetadata().get("subscriptionId");

                    payment = createPaymentFromPaymentIntent(paymentIntent, PaymentStatus.SUCCESS);

                    if (subscriptionId != null) {
                        log.info("Processing subscriptionId: {}", subscriptionId);
                        studentSubscriptionService.updateSubscriptionStatus(Long.valueOf(subscriptionId), payment);
                    } else {
                        log.warn("No subscriptionId found in metadata for session: {}", session != null ? session.getId() : "N/A");
                    }

                    break;
                case "payment_intent.payment_failed":
                    paymentIntent = (PaymentIntent) event.getData().getObject();
                    session = Session.list(SessionListParams.builder()
                            .setPaymentIntent(paymentIntent.getId())
                            .build()).getData().get(0); // Retrieve the session associated with the PaymentIntent
                    subscriptionId = session.getMetadata().get("subscriptionId");
                    payment = createPaymentFromPaymentIntent(paymentIntent, PaymentStatus.FAILED);

                    if (subscriptionId != null) {
                        log.info("Processing subscriptionId: {}", subscriptionId);
                        studentSubscriptionService.updateSubscriptionStatus(Long.valueOf(subscriptionId), payment);
                    } else {
                        log.warn("No subscriptionId found in metadata for session: {}", session != null ? session.getId() : "N/A");
                    }
                    break;

                default:
                    log.warn("Unhandled event type: {}", event.getType());
                    return ResponseEntity.ok("Received (unhandled event type)");
            }

            return ResponseEntity.ok("Received");

        } catch (SignatureVerificationException e) {
            log.error("Invalid Stripe signature", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        } catch (StripeException e) {
            log.error("Stripe API error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Stripe API error");
        } catch (Exception e) {
            log.error("Webhook processing error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Webhook error");
        }
    }

    private Payment createPaymentFromPaymentIntent(PaymentIntent paymentIntent, PaymentStatus status) {
        Payment payment = new Payment();
        payment.setPaymentId(paymentIntent.getId());
        payment.setAmount(Double.valueOf(paymentIntent.getAmount()) / 100); // Convert from cents to dollars
        payment.setCurrency(paymentIntent.getCurrency());
        payment.setStatus(status);

        LocalDateTime paymentTime = Instant.ofEpochSecond(paymentIntent.getCreated())
                .atZone(ZoneId.of("UTC")) // Use UTC for consistency
                .toLocalDateTime();
        payment.setPaymentTime(paymentTime);

        return payment;
    }
}
