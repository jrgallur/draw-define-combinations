
package com.draw_define_combinations.repositories;

import com.draw_define_combinations.domain.ProbabilityTypeCombinationByDrawMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProbabilityTypeCombinationByDrawRepository extends JpaRepository<ProbabilityTypeCombinationByDrawMO, Integer> {
    List<ProbabilityTypeCombinationByDrawMO> findByDrawTypeId(Short drawTypeId);
}