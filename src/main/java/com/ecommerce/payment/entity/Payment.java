package com.ecommerce.payment.entity;

import com.ecommerce.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", unique = true)
    private Order order;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    private String provider = "STRIPE";

    // Ex: id du PaymentIntent Stripe (pi_xxx)
    private String transactionRef;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum PaymentStatus {
        PENDING, SUCCESS, FAILED
    }
}
