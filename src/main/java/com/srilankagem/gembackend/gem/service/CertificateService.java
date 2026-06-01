package com.srilankagem.gembackend.gem.service;


import com.srilankagem.gembackend.common.exception.DuplicateResourceException;
import com.srilankagem.gembackend.common.exception.ResourceNotFoundException;
import com.srilankagem.gembackend.gem.dto.CertificateRequest;
import com.srilankagem.gembackend.gem.dto.CertificateResponse;
import com.srilankagem.gembackend.gem.models.Certificate;
import com.srilankagem.gembackend.gem.models.GemStone;
import com.srilankagem.gembackend.gem.repository.CertificateRepository;
import com.srilankagem.gembackend.gem.repository.GemStoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service


public class CertificateService {

    private final CertificateRepository certificateRepository;

    private final GemStoneRepository gemStoneRepository;

//    @Autowired
//    public CertificateService(CertificateRepository certificateRepository) {
//        this.certificateRepository = certificateRepository;
//    }

    // @RequiredArgsConstructor eken me serama thiyenwa



    @Autowired
    public CertificateService(CertificateRepository certificateRepository, GemStoneRepository gemStoneRepository) {
        this.certificateRepository = certificateRepository;
        this.gemStoneRepository = gemStoneRepository;
    }

    public CertificateResponse createCertificate(CertificateRequest request) {
        if (certificateRepository.existsByCertificateNumber(request.getCertificateNumber())) {
            throw new DuplicateResourceException("Certificate with " + request.getCertificateNumber() + " this number already exists");
        }

        if (certificateRepository.existsByGemStoneId(request.getGemId())) {
            throw new DuplicateResourceException("Certificate with " + request.getGemId() + " already exists");
        }

        GemStone stone = gemStoneRepository.findById(request.getGemId()).orElseThrow(() -> new ResourceNotFoundException("Gem",request.getGemId().toString()));

        Certificate certificate = Certificate.builder()
                .certificateNumber(request.getCertificateNumber())
                .gemStone(stone)
                .issuedBy(request.getIssuedBy())
                .issuedDate(request.getIssueDate())
                .expireDate(request.getExpiryDate())
                .remark(request.getRemark())
                .build();

        return toResponse(certificateRepository.save(certificate));
    }

    private CertificateResponse toResponse(Certificate cert) {
        return CertificateResponse.builder()
                .certificateNumber(cert.getCertificateNumber())
                .gemId(cert.getId())
                .gemCode(cert.getGemStone().getGemCode())
                .issuedBy(cert.getIssuedBy())
                .issueDate(cert.getIssuedDate())
                .expiryDate(cert.getExpireDate())
                .remarks(cert.getRemark())
                .build();
    }


    public CertificateResponse getCertificateById(Long id) throws ResourceNotFoundException {
        return toResponse(certificateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Certificate", id.toString())));
    }
}
