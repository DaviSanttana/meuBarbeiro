CREATE EXTENSION IF NOT EXISTS btree_gist;

CREATE TABLE cliente (
                         id        BIGSERIAL PRIMARY KEY,
                         nome      VARCHAR(120) NOT NULL,
                         email     VARCHAR(255) NOT NULL UNIQUE,
                         telefone  VARCHAR(20)
);

CREATE TABLE profissional (
                              id     BIGSERIAL PRIMARY KEY,
                              nome   VARCHAR(120) NOT NULL,
                              ativo  BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE servico (
                         id               BIGSERIAL PRIMARY KEY,
                         nome             VARCHAR(120) NOT NULL,
                         duracao_minutos  INTEGER NOT NULL CHECK (duracao_minutos > 0),
                         preco            NUMERIC(10,2) NOT NULL CHECK (preco >= 0)
);

CREATE TABLE agendamento (
                             id               BIGSERIAL PRIMARY KEY,
                             cliente_id       BIGINT NOT NULL REFERENCES cliente(id),
                             profissional_id  BIGINT NOT NULL REFERENCES profissional(id),
                             servico_id       BIGINT NOT NULL REFERENCES servico(id),
                             inicio           TIMESTAMPTZ NOT NULL,
                             fim              TIMESTAMPTZ NOT NULL,
                             status           VARCHAR(20) NOT NULL DEFAULT 'AGENDADO'
                                 CHECK (status IN ('AGENDADO', 'CANCELADO', 'CONCLUIDO')),
                             criado_em        TIMESTAMPTZ NOT NULL DEFAULT now(),
                             CHECK (fim > inicio),
                             CONSTRAINT sem_sobreposicao EXCLUDE USING gist (
        profissional_id WITH =,
        tstzrange(inicio, fim) WITH &&
    ) WHERE (status <> 'CANCELADO')
);

CREATE INDEX idx_agendamento_cliente ON agendamento(cliente_id);