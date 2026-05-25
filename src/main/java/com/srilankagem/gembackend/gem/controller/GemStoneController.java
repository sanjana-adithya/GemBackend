package com.srilankagem.gembackend.gem.controller;

import com.srilankagem.gembackend.common.exception.ResourceNotFoundException;
import com.srilankagem.gembackend.gem.dto.GemStoneRequest;
import com.srilankagem.gembackend.gem.dto.GemStoneResponse;
import com.srilankagem.gembackend.gem.service.GemStoneService;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/gems")
@RequiredArgsConstructor
public class GemStoneController {

    private final GemStoneService gemStoneService;

    @GetMapping
    public ResponseEntity<Page<GemStoneResponse>> getAllGemStones(@PageableDefault(size = 20, sort = "color") Pageable pageable) {

        return ResponseEntity.ok(gemStoneService.getAllGemstones(pageable));
    }

    @PostMapping
    public ResponseEntity<GemStoneResponse> createGemStone(@RequestBody GemStoneRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Custom-Header", "Sending-Customer-Header")
                .body(gemStoneService.createGemStone(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GemStoneResponse> getGemStoneById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(gemStoneService.getGemStoneById(id));
    }
}
