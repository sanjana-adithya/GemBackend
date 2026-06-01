package com.srilankagem.gembackend.gem.service;

import com.srilankagem.gembackend.common.exception.DuplicateResourceException;
import com.srilankagem.gembackend.common.exception.ResourceNotFoundException;
import com.srilankagem.gembackend.gem.dto.TagResponse;
import com.srilankagem.gembackend.gem.dto.TasRequest;
import com.srilankagem.gembackend.gem.models.Tag;
import com.srilankagem.gembackend.gem.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream().map(this::toResponse).toList();
    }

    public TagResponse createTag(TasRequest request) {
        if (tagRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateResourceException("Tag with name '" + request.getName() + "' already exists");
        }
        Tag tag = Tag.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return toResponse(tagRepository.save(tag));
    }

    public TagResponse getTagById(Long id) throws ResourceNotFoundException {
        return toResponse(tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", id.toString())));
    }

    public void deleteTag(Long id) throws ResourceNotFoundException {
        if (!tagRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tag", id.toString());
        }
        tagRepository.deleteById(id);
    }

    private TagResponse toResponse(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .description(tag.getDescription())
                .createdAt(tag.getCreatedAt())
                .build();
    }
}
