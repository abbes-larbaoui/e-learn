package dz.kyrios.core.service.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentService implements PaymentService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Override
    public String createCheckoutSession(Long subscriptionId, String currency, Double amount) {
        Stripe.apiKey = stripeSecretKey;

        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://yourapp.com/payment-success?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl("https://yourapp.com/payment-failed")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(currency)
                                                    .setUnitAmount((long) (amount * 100)) // Convert to cents
                                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName("Subscription Plan")
                                                            .setDescription("Student Subscription for language online real time course")
                                                            .build())
                                                    .build())
                                    .build())
                    .putMetadata("subscriptionId", subscriptionId.toString())
                    .build();

            Session session = Session.create(params);
            return session.getUrl(); // Return the URL to redirect the user to Stripe
        } catch (StripeException e) {
            throw new RuntimeException("Stripe checkout session creation failed", e);
        }
    }

}
