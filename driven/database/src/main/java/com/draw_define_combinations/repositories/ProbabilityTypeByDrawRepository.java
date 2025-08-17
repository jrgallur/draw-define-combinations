package com.draw_define_combinations.repositories;


import com.draw_define_combinations.domain.ProbabilityTypeByDrawMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProbabilityTypeByDrawRepository extends JpaRepository<ProbabilityTypeByDrawMO, Integer> {
    List<ProbabilityTypeByDrawMO> findByProbabilityTypeId(Integer probabilityTypeId);
}