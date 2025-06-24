package com.draw_define_combinations.repositories;

import com.draw_define_combinations.models.ProbabilityTypeMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProbabilityTypeRepository extends JpaRepository<ProbabilityTypeMO, Short> {
    Optional<ProbabilityTypeMO> findByCode(String code);
}