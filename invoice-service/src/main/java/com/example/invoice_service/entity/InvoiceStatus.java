package com.example.invoice_service.entity;

public enum InvoiceStatus {

    /**
     * Invoice request accepted.
     * No processing has started yet.
     */
    REQUESTED,

    /**
     * Invoice generation is in progress.
     * PDF rendering + storage ongoing.
     */
    GENERATING,

    /**
     * Invoice successfully generated and stored.
     * PDF URL is available.
     */
    COMPLETED,

    /**
     * Invoice generation failed.
     * failureReason must be populated.
     */
    FAILED
}
