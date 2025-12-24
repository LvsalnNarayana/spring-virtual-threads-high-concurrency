package com.example.invoice_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRequestModel {

    /**
     * Order identifier for which the invoice must be generated.
     */

    private UUID orderId;
}
