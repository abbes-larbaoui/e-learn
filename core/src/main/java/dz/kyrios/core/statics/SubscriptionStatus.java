package dz.kyrios.core.statics;

public enum SubscriptionStatus {
    PENDING, // in the creation of subscription before payment confirmation
    ACTIVE, // when the payment is success
    EXPIRED, // when all sessions are complete
    CANCELED // student can cancel the subscription TODO: need more details (with refund or without)
}
