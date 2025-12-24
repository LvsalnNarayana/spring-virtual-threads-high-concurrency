package com.example.invoice_service.mappers;

import com.example.invoice_service.entity.InvoiceEntity;
import com.example.invoice_service.models.InvoiceResponseModel;

public final class InvoiceMapper {

    private InvoiceMapper() {
        // utility class
    }

    public static InvoiceResponseModel toResponse(InvoiceEntity invoice) {
        return InvoiceResponseModel.builder()
                .invoiceId(invoice.getId())
                .status(invoice.getStatus())
                .pdfUrl(invoice.getPdfUrl())
                .generatedAt(invoice.getGeneratedAt())
                .failureReason(invoice.getFailureReason())
                .build();
    }
}
