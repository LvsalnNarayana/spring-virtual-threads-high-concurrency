package com.example.invoice_service.models;

import com.example.invoice_service.entity.InvoiceStatus;
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
public class InvoiceResponseModel {

    /**
     * Unique identifier of the invoice.
     */
    private UUID invoiceId;

    /**
     * Current lifecycle status of the invoice.
     */
    private InvoiceStatus status;

    /**
     * URL to download the generated invoice PDF.
     * Available only when status = COMPLETED.
     */
    private String pdfUrl;

    /**
     * Timestamp when invoice was generated.
     * Null until COMPLETED.
     */
    private Instant generatedAt;

    /**
     * Reason for failure, populated only when status = FAILED.
     */
    private String failureReason;
}
