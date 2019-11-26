create table seg_fornecedores
(
    id BIGINT IDENTITY   (1,1)  PRIMARY KEY NOT NULL,
    razao_social  VARCHAR (36)  NOT NULL,
    cnpj          VARCHAR (36)  NOT NULL,
    nome_fantasia VARCHAR (255) NOT NULL,
    endereco      VARCHAR (100) NOT NULL,
    telefone      VARCHAR (36)  NOT NULL,
    email         VARCHAR (100) NOT NULL
);