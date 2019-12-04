create table fornecedor
(
    id               BIGINT IDENTITY (1, 1) PRIMARY KEY NOT NULL,
    razao_social     varchar(100)                       not null,
    cnpj             varchar(11)                        not null unique,
    nome_fantasia    varchar(100)                       not null,
    endereco         varchar(100)                       not null,
    telefone_contato varchar(12)                        not null,
    email_contato    varchar(50)                        not null
);

create unique index ix_fornecedor_01 on fornecedor (cnpj asc);
