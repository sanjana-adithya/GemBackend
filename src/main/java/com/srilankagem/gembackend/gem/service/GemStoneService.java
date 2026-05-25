package com.srilankagem.gembackend.gem.service;

import com.srilankagem.gembackend.common.exception.ResourceNotFoundException;
import com.srilankagem.gembackend.gem.dto.GemStoneRequest;
import com.srilankagem.gembackend.gem.dto.GemStoneResponse;
import com.srilankagem.gembackend.gem.models.GemStone;
import com.srilankagem.gembackend.gem.repository.GemStoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GemStoneService {

    private final GemStoneRepository gemStoneRepository;

    public Page<GemStoneResponse> getAllGemstones(Pageable pageable) {
//        return gemStoneRepository.findByActiveTrue(pageable).map(item -> toResponse(item));
        return gemStoneRepository.findByActiveTrue(pageable).map(this::toResponse);
    }

    public GemStoneResponse createGemStone(GemStoneRequest request) {
        GemStone gemStone = GemStone.builder()
                .gemCode(request.getGemCode())
                .type(request.getType())
                .origin(request.getOrigin())
                .caratWeight(request.getCaratWeight())
                .description(request.getDescription())
                .stockQuantity(request.getStockQuantity())
                .color(request.getColor())
                .treatment(request.getTreatment())
                .pricePerCarat(request.getPricePerCarat())
                .active(true)
                .build();

        return toResponse(gemStoneRepository.save(gemStone));
    }


    public GemStoneResponse getGemStoneById(Long id) throws ResourceNotFoundException {
        return toResponse(gemStoneRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Gem", id.toString())));
    }


    private GemStoneResponse toResponse(GemStone gemStone) {
        return GemStoneResponse.builder().
                id(gemStone.getId())
                .gemCode(gemStone.getGemCode())
                .type(gemStone.getType())
                .color(gemStone.getColor())
                .caratWeight(gemStone.getCaratWeight())
                .origin(gemStone.getOrigin())
                .treatment(gemStone.getTreatment())
                .pricePerCarat(gemStone.getPricePerCarat())
                .stockQuantity(gemStone.getStockQuantity())
                .certified(gemStone.isCertified())
                .description(gemStone.getDescription())
                .createdAt(gemStone.getCreatedAt())
                .updatedAt(gemStone.getUpdatedAt())
                .build();
    }


}
