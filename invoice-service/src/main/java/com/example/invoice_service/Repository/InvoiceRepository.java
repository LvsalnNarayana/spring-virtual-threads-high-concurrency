package com.example.invoice_service.Repository;

import com.example.invoice_service.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, UUID> {

    Optional<InvoiceEntity> findByOrderId(UUID orderId);

    Optional<InvoiceEntity> findByInvoiceNumber(String invoiceNumber);
}
