package com.srilankagem.gembackend.dealer.repository;

import com.srilankagem.gembackend.dealer.entity.Dealer;
import com.srilankagem.gembackend.dealer.entity.DealerTier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> {

    Optional<Dealer> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<Dealer> findByTier(DealerTier tier, Pageable pageable);
}
