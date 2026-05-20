package com.example.employeedirectory.service;

import com.example.employeedirectory.model.Position;
import com.example.employeedirectory.repository.PositionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Cacheable("positions")
    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    public Position findById(@NonNull Long id) {
        return positionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Position not found with id: " + id));
    }

    @Transactional
    @CacheEvict(value = "positions", allEntries = true)
    public Position save(@NonNull Position position) {
        return positionRepository.save(position);
    }

    @Transactional
    @CacheEvict(value = "positions", allEntries = true)
    public void deleteById(@NonNull Long id) {
        positionRepository.deleteById(id);
    }
}
