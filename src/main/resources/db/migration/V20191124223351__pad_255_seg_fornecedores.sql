create table seg_fornecedores(
    id_fornecedor BIGINT IDENTITY   (1,1)  PRIMARY KEY NOT NULL,
    razao_social  VARCHAR (100)  NOT NULL,
    cnpj          VARCHAR  (14)  NOT NULL UNIQUE,
    nome_fantasia VARCHAR (100)  NOT NULL,
    endereco      VARCHAR (100)  NOT NULL,
    telefone      VARCHAR  (14)  NOT NULL,
    email         VARCHAR  (50)  NOT NULL
);

