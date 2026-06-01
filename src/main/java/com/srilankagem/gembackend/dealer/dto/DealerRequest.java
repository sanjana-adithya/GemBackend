package com.srilankagem.gembackend.dealer.dto;

import com.srilankagem.gembackend.dealer.entity.DealerTier;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealerRequest {

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 100, message = "Company name must be between 2 to 100 characters")
    private String companyName;

    @NotBlank(message = "Contact person is required")
    private String contactPerson;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    private DealerTier tier;
}
