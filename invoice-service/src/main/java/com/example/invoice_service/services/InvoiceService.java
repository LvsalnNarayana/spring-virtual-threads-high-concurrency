package com.example.invoice_service.services;

import com.example.invoice_service.models.InvoiceRequestModel;
import com.example.invoice_service.models.InvoiceResponseModel;

import java.util.UUID;

public interface InvoiceService {

    /**
     * Accepts a request to generate an invoice for an order.
     * This method is safe to be called under very high concurrency.
     */
    InvoiceResponseModel generateInvoice(InvoiceRequestModel request);

    /**
     * Fetches the current status of an invoice.
     */
    InvoiceResponseModel getInvoiceStatus(UUID invoiceId);

    /**
     * Fetches invoice details including download URL if available.
     */
    InvoiceResponseModel getInvoiceById(UUID invoiceId);
}
