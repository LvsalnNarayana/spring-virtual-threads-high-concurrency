package com.example.invoice_service.services;

import com.example.invoice_service.entity.InvoiceEntity;

public interface InvoiceGenerationService {

    /**
     * Performs the full invoice generation workflow:
     * - Fetch order
     * - Render template
     * - Generate PDF
     * - Store PDF
     * - Publish event
     */
    void generateInvoice(InvoiceEntity invoice);
}
