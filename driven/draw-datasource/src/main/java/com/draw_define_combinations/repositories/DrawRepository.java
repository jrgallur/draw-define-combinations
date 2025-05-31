package com.draw_define_combinations.repositories;

import com.draw_define_combinations.models.DrawMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DrawRepository extends JpaRepository<DrawMO, Integer> {
    /**
     * Get the draw list
     *
     * @return Whole draw list
     */
    List<DrawMO> findAll();

    /**
     * Find a draw by date
     * @param date Date in witch the draw must be done
     * @return The draw, if exists in the date
     */
    Optional<DrawMO> findByDate(Integer date);
}