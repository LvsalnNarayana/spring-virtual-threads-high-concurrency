package com.example.invoice_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "invoice")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "invoice_number", nullable = false, unique = true)
    private String invoiceNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status;

    @Column(name = "pdf_url")
    private String pdfUrl;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "requested_at", nullable = false)
    private Instant requestedAt;

    @Column(name = "generated_at")
    private Instant generatedAt;

    @Column(name = "failure_reason")
    private String failureReason;

    // getters / setters later
}
