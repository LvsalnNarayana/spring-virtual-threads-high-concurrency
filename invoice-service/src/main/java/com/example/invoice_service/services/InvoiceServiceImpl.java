package com.example.invoice_service.services;

import com.example.invoice_service.Repository.InvoiceRepository;
import com.example.invoice_service.entity.InvoiceEntity;
import com.example.invoice_service.entity.InvoiceStatus;
import com.example.invoice_service.mappers.InvoiceMapper;
import com.example.invoice_service.models.InvoiceRequestModel;
import com.example.invoice_service.models.InvoiceResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceGenerationService invoiceGenerationService;

    @Override
    @Transactional
    public InvoiceResponseModel generateInvoice(InvoiceRequestModel request) {

        InvoiceEntity invoice = invoiceRepository
                .findByOrderId(request.getOrderId())
                .orElseGet(() -> createNewInvoice(request));

        if (invoice.getStatus() == InvoiceStatus.REQUESTED) {
            invoice.setStatus(InvoiceStatus.GENERATING);
            invoiceRepository.save(invoice);

            // Blocking call – safe with Virtual Threads
            invoiceGenerationService.generateInvoice(invoice);
        }

        return InvoiceMapper.toResponse(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponseModel getInvoiceStatus(UUID invoiceId) {
        InvoiceEntity invoice = invoiceRepository
                .findById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));

        return InvoiceMapper.toResponse(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponseModel getInvoiceById(UUID invoiceId) {
        InvoiceEntity invoice = invoiceRepository
                .findById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));

        return InvoiceMapper.toResponse(invoice);
    }

    private InvoiceEntity createNewInvoice(InvoiceRequestModel request) {
        InvoiceEntity invoice = InvoiceEntity.builder()
                .orderId(request.getOrderId())
                .invoiceNumber(generateInvoiceNumber())
                .status(InvoiceStatus.REQUESTED)
                .requestedAt(Instant.now())
                .build();

        return invoiceRepository.save(invoice);
    }

    private String generateInvoiceNumber() {
        return "INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
