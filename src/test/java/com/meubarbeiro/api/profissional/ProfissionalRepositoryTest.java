package com.meubarbeiro.api.profissional;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class ProfissionalRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16");

    @Autowired
    ProfissionalRepository repository;

    @Autowired
    EntityManager entityManager;

    @Test
    void salvaERelePorId() {
        Profissional salvo = repository.save(new Profissional("Carlos"));
        entityManager.flush();
        entityManager.clear();

        Profissional encontrado = repository.findById(salvo.getId()).orElseThrow();

        assertEquals("Carlos", encontrado.getNome());
        assertTrue(encontrado.isAtivo());
    }
    @Test
    void desativarSemSaveGravaNoBanco() {
        Profissional salvo = repository.save(new Profissional("Carlos"));
        entityManager.flush();
        entityManager.clear();

        Profissional carregado = repository.findById(salvo.getId()).orElseThrow();
        carregado.desativar();
        entityManager.flush();
        entityManager.clear();

        Profissional recarregado = repository.findById(salvo.getId()).orElseThrow();
        assertFalse(recarregado.isAtivo());
    }
}