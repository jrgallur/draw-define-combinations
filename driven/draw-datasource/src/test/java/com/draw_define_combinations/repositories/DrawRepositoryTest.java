package com.draw_define_combinations.repositories;

import com.draw_define_combinations.models.DrawMO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DrawRepositoryTest {
    @Autowired
    DrawRepository repository;

    @Autowired
    private TestEntityManager testEM;

    // @Test
    void should_find_no_tutorials_if_repository_is_empty() {
        List<DrawMO> drawMOList = repository.findAll();

        assertNotNull(drawMOList);
        assertTrue(drawMOList.isEmpty());
    }
}