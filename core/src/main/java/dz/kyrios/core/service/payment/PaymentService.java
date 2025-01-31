package dz.kyrios.core.service.payment;

public interface PaymentService {
    String createCheckoutSession(Long subscriptionId, String currency, Double amount);
}

