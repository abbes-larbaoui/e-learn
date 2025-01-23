package dz.kyrios.core.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Payment {

    private Long id;

    private StudentSubscription subscription;

    private double amount;

    private String currency;

    private String transactionId;

    private LocalDateTime timestamp;

    private String status;


}
