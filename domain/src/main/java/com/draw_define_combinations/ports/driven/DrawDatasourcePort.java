package com.draw_define_combinations.ports.driven;

import com.draw_define_combinations.models.Draw;
import com.draw_define_combinations.models.DrawList;

import java.util.Optional;

public interface DrawDatasourcePort {
    /**
     * Get the draw list
     *
     * @return Draw list
     */
    DrawList getDrawList();

    /**
     * Insert a draw in the database
     *
     * @param draw Draw to insert
     */
    void insertDraw(Draw draw);

    /**
     * Get a Draw by date
     * @param date Date of the draw
     * @return the Draw, if exists in that date
     */
    Optional<Draw> findByDate(Integer date);
}