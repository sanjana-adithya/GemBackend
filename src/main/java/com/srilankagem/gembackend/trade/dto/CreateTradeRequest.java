package com.srilankagem.gembackend.trade.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CreateTradeRequest {

    @NotNull(message = "dealer id is required")
    private Long dealerId;

    @NotEmpty(message = "Trade must at least on gem")
    @Valid
    private List<TradeItemRequest> items;

    @Size(max = 100,message = "Shipping address cannot exceed 500 characters")
    private String shippingAddress;

    @Size(max = 500, message = "notes cannot exceed 500 characters")
    private String notes;
}
