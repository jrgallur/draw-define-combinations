package com.draw_define_combinations.adapters;

import com.draw_define_combinations.application.ports.driven.ProbabilityTypePort;
import com.draw_define_combinations.domain.ProbabilityType;
import com.draw_define_combinations.mappers.ProbabilityTypeMapper;
import com.draw_define_combinations.repositories.ProbabilityTypeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ProbabilityTypeAdapter implements ProbabilityTypePort {
    private final ProbabilityTypeRepository repository;
    private final ProbabilityTypeMapper mapper;


    @Override
    public ProbabilityType getByCode(String code) {
        return mapper.toDomain(repository.findByCode(code).orElse(null));
    }

    @Override
    public List<ProbabilityType> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }
}
