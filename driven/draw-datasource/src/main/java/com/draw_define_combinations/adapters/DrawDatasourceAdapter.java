package com.draw_define_combinations.adapters;

import com.draw_define_combinations.mappers.DrawMapper;
import com.draw_define_combinations.models.Draw;
import com.draw_define_combinations.models.DrawList;
import com.draw_define_combinations.ports.driven.DrawDatasourcePort;
import com.draw_define_combinations.repositories.DrawRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class DrawDatasourceAdapter implements DrawDatasourcePort {
    private final DrawMapper drawMapper;
    private final DrawRepository drawRepository;

    public DrawList getDrawList() {
        // TODO:
        return new DrawList(drawRepository.findAll().stream().map(drawMapper::toDomain).collect(Collectors.toList()));
    }

    public void insertDraw(Draw draw) {
        log.info("Saving draw: {}", draw);
        drawRepository.save(drawMapper.toModel(draw));
    }

    public Optional<Draw> findByDate(Integer date) {
        return drawRepository.findByDate(date).map(drawMapper::toDomain);
    }

}