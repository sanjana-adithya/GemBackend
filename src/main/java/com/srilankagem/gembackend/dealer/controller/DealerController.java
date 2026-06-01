package com.srilankagem.gembackend.dealer.controller;

import com.srilankagem.gembackend.common.exception.ResourceNotFoundException;
import com.srilankagem.gembackend.dealer.dto.DealerRequest;
import com.srilankagem.gembackend.dealer.dto.DealerResponse;
import com.srilankagem.gembackend.dealer.entity.DealerTier;
import com.srilankagem.gembackend.dealer.service.DealerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/dealers")
@RequiredArgsConstructor
public class DealerController {

    private final DealerService dealerService;

    @GetMapping
    public ResponseEntity<Page<DealerResponse>> getAllDealers(@PageableDefault(size = 20, sort = "companyName") Pageable pageable) {
        return ResponseEntity.ok(dealerService.getAllDealers(pageable));
    }

    @PostMapping
    public ResponseEntity<DealerResponse> createDealer(@RequestBody DealerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dealerService.createDealer(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealerResponse> getDealer(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(dealerService.getDealerById(id));
    }

    @GetMapping("/tier/{tier}")
    public ResponseEntity<Page<DealerResponse>> getDealerByTier(@PathVariable DealerTier tier,
                                                                @PageableDefault(size = 20, sort = "companyName") Pageable pageable) {
        return ResponseEntity.ok(dealerService.getDealersByTier(tier, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DealerResponse> updateDealer(@PathVariable Long id, @RequestBody DealerRequest request) throws ResourceNotFoundException {
        return ResponseEntity.ok(dealerService.updateDealer(id, request));
    }
}
