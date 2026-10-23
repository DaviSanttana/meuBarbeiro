package com.meubarbeiro.api.servico;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false)
    private int duracaoMinutos;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    protected Servico() {
    }

    public Servico(String nome, int duracaoMinutos, BigDecimal preco) {
        this.nome = nome;
        this.duracaoMinutos = duracaoMinutos;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public BigDecimal getPreco() {
        return preco;
    }
}
