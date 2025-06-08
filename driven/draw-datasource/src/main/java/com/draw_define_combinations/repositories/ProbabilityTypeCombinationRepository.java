package com.draw_define_combinations.repositories;

import com.draw_define_combinations.models.ProbabilityTypeCombinationMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProbabilityTypeCombinationRepository extends JpaRepository<ProbabilityTypeCombinationMO, Integer> {
    Optional<ProbabilityTypeCombinationMO> findByCode(String code);

}