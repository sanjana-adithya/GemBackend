package com.srilankagem.gembackend.trade.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TradeItemResponse {

    private Long id;
    private Long gemId;
    private String gemCode;
    private String gemType;
    private String caratWeight;
    private String quantity;
    private String unitPrice;

}
