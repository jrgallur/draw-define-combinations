package com.draw_define_combinations.repositories;

import com.draw_define_combinations.domain.ProbabilityTypeMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProbabilityTypeRepository extends JpaRepository<ProbabilityTypeMO, Integer> {
    Optional<ProbabilityTypeMO> findByCode(String code);
}