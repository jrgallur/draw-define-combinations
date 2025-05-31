package com.draw_define_combinations;

import com.draw_define_combinations.usecases.CalculateCombinationsUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MainTask implements CommandLineRunner {

    private final CalculateCombinationsUseCase service;

    public MainTask(CalculateCombinationsUseCase calculateCombinations) {
        this.service = calculateCombinations;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Iniciando tarea principal...");
        service.executeAll();
        log.info("Tarea completada.");
    }
}

