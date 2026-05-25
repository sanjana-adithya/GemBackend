package com.srilankagem.gembackend.gem.controller;

import com.srilankagem.gembackend.common.exception.ResourceNotFoundException;
import com.srilankagem.gembackend.gem.dto.CertificateRequest;
import com.srilankagem.gembackend.gem.dto.CertificateResponse;
import com.srilankagem.gembackend.gem.service.CertificateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/certificates")

public class CertificateController {
    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping
    public ResponseEntity<CertificateResponse> createCertificate(@RequestBody CertificateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Custom-Header", "Sending-Customer-Header")
                .body(certificateService.createCertificate(request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CertificateResponse> getCertificateById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(certificateService.getCertificateById(id));
    }

}
