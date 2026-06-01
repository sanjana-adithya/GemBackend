package com.srilankagem.gembackend.dealer.service;

import com.srilankagem.gembackend.common.exception.DuplicateResourceException;
import com.srilankagem.gembackend.common.exception.ResourceNotFoundException;
import com.srilankagem.gembackend.dealer.dto.DealerRequest;
import com.srilankagem.gembackend.dealer.dto.DealerResponse;
import com.srilankagem.gembackend.dealer.entity.Dealer;
import com.srilankagem.gembackend.dealer.entity.DealerTier;
import com.srilankagem.gembackend.dealer.repository.DealerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DealerService {

    private final DealerRepository dealerRepository;

    public Page<DealerResponse> getAllDealers(Pageable pageable) {
        return dealerRepository.findAll(pageable).map(this::toResponse);
    }

    public Page<DealerResponse> getDealersByTier(DealerTier tier, Pageable pageable) {
        return dealerRepository.findByTier(tier, pageable).map(this::toResponse);
    }

    public DealerResponse createDealer(DealerRequest request) {
        if (dealerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Dealer already exists with email - " + request.getEmail());
        }

        Dealer dealer = Dealer.builder()
                .companyName(request.getCompanyName())
                .contactPerson(request.getContactPerson())
                .email(request.getEmail())
                .phone(request.getPhone())
                .country(request.getCountry())
                .shippingAddress(request.getShippingAddress())
                .tier(request.getTier() != null ? request.getTier() : DealerTier.BRONZE)
                .build();

        return toResponse(dealerRepository.save(dealer));
    }

    public DealerResponse getDealerById(Long id) throws ResourceNotFoundException {
        return toResponse(dealerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dealer", id.toString())));
    }

    public DealerResponse updateDealer(Long id, DealerRequest request) throws ResourceNotFoundException {
        Dealer dealer = dealerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dealer", id.toString()));

        if (!dealer.getEmail().equals(request.getEmail()) && dealerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Dealer already exists with email - " + request.getEmail());
        }

        dealer.setCompanyName(request.getCompanyName());
        dealer.setContactPerson(request.getContactPerson());
        dealer.setEmail(request.getEmail());
        dealer.setPhone(request.getPhone());
        dealer.setCountry(request.getCountry());
        dealer.setShippingAddress(request.getShippingAddress());
        if (request.getTier() != null) {
            dealer.setTier(request.getTier());
        }

        return toResponse(dealerRepository.save(dealer));
    }

    private DealerResponse toResponse(Dealer dealer) {
        return DealerResponse.builder()
                .id(dealer.getId())
                .companyName(dealer.getCompanyName())
                .contactPerson(dealer.getContactPerson())
                .email(dealer.getEmail())
                .phone(dealer.getPhone())
                .country(dealer.getCountry())
                .shippingAddress(dealer.getShippingAddress())
                .tier(dealer.getTier())
                .tradeCount(dealer.getTrades() != null ? dealer.getTrades().size() : 0)
                .createdAt(dealer.getCreatedAt())
                .updatedAt(dealer.getUpdatedAt())
                .build();
    }
}
