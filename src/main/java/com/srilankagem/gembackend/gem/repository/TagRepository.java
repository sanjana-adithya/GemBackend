package com.srilankagem.gembackend.gem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srilankagem.gembackend.gem.models.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
