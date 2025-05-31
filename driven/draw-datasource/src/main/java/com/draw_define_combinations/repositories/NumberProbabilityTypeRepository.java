package com.draw_define_combinations.repositories;

import com.draw_define_combinations.models.NumberProbabilityTypeMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NumberProbabilityTypeRepository extends JpaRepository<NumberProbabilityTypeMO, Short> {
    /**
     * Get the type list
     *
     * @return Whole type list
     */
    List<NumberProbabilityTypeMO> findAll();

    Optional<NumberProbabilityTypeMO> findByCode(String code);

}