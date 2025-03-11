CREATE TYPE column_kind AS ENUM ('INICIAL', 'CANCELADO', 'FINAL', 'PENDENTE');


CREATE TABLE board (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE board_column (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    kind column_kind NOT NULL,
    column_order INT NOT NULL,
    board_id BIGINT NOT NULL,
    CONSTRAINT fk_board FOREIGN KEY (board_id) REFERENCES board(id) ON DELETE CASCADE
);


CREATE TABLE card (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    board_column_id BIGINT NOT NULL,
    CONSTRAINT fk_board_column FOREIGN KEY (board_column_id) REFERENCES board_column(id) ON DELETE CASCADE
);

-- Tabela Block
CREATE TABLE block (
    id BIGSERIAL PRIMARY KEY,
    block_cause TEXT NOT NULL,
    block_in TIMESTAMP WITH TIME ZONE NOT NULL,
    unblock_cause TEXT,
    unblock_in TIMESTAMP WITH TIME ZONE,
    card_id BIGINT NOT NULL,
    CONSTRAINT fk_card FOREIGN KEY (card_id) REFERENCES card(id) ON DELETE CASCADE
);
