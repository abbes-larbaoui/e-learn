package dz.kyrios.core.listener;

import dz.kyrios.core.event.SubscriptionCreatedEvent;
import dz.kyrios.core.service.SessionScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SubscriptionEventListener {

    private final SessionScheduleService sessionScheduleService;

    public SubscriptionEventListener( SessionScheduleService sessionScheduleService) {
        this.sessionScheduleService = sessionScheduleService;
    }

    @EventListener
    @Async  // Runs asynchronously
    public void handleSubscriptionCreated(SubscriptionCreatedEvent event) {
        log.info("Just get the event with the id : {}", event.subscriptionId());
        sessionScheduleService.createSessionsForSubscription(event.subscriptionId());
    }
}
