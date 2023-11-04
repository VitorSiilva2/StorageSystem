package org.example.storagesystem.repository;

import org.example.storagesystem.model.LocationModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;
    @Test
    void findByPositionName() {


    }


    @Test
    public void testFindByPositionName() {
        // Chame o método que você deseja testar
        Optional<LocationModel> result = locationRepository.findByPositionName("PosicaoA");

        // Verifique se a consulta retornou um resultado
        assertTrue(result.isPresent());

        // Verifique se o resultado é o que você esperava
        assertEquals("PosicaoA", result.get().getPosition());
    }

    @Test
    public void testFindByPositionNameNonExisting() {
        // Chame o método que você deseja testar com uma posição que não existe
        Optional<LocationModel> result = locationRepository.findByPositionName("PosicaoC");

        // Verifique se a consulta não retornou um resultado
        assertTrue(result.isEmpty());
    }
}