package com.example.invoice_service.services;

import com.example.invoice_service.Repository.InvoiceRepository;
import com.example.invoice_service.entity.InvoiceEntity;
import com.example.invoice_service.entity.InvoiceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class InvoiceGenerationServiceImpl implements InvoiceGenerationService {

    private final InvoiceRepository invoiceRepository;

    @Override
    @Transactional
    public void generateInvoice(InvoiceEntity invoice) {
        try {
            // 1. Fetch order details (blocking HTTP call)
            // 2. Render invoice template
            // 3. Generate PDF (CPU + I/O)
            // 4. Upload to object storage
            // 5. Publish RabbitMQ event

            // Simulated success path
            invoice.setPdfUrl("https://object-storage/invoices/" + invoice.getInvoiceNumber() + ".pdf");
            invoice.setGeneratedAt(Instant.now());
            invoice.setStatus(InvoiceStatus.COMPLETED);

        } catch (Exception ex) {
            invoice.setStatus(InvoiceStatus.FAILED);
            invoice.setFailureReason(ex.getMessage());
        }

        invoiceRepository.save(invoice);
    }
}
