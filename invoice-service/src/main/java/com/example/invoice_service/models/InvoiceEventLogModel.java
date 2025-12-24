package com.example.invoice_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceEventLogModel {

    /**
     * Unique identifier for the event log entry.
     */
    private UUID id;

    /**
     * Associated invoice identifier.
     */
    private UUID invoiceId;

    /**
     * Type of event that occurred.
     * Example: INVOICE_REQUESTED, PDF_GENERATED, EVENT_PUBLISHED
     */
    private String eventType;

    /**
     * Optional human-readable message or metadata.
     */
    private String message;

    /**
     * Timestamp when the event occurred.
     */
    private Instant occurredAt;
}
