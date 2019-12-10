CREATE TABLE seg_linhas(
    id_linha BIGINT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    nome_linha       VARCHAR (50) NOT NULL,
    codigo_linha     VARCHAR (10) NOT NULL UNIQUE,
    id_linha_categoria BIGINT FOREIGN KEY REFERENCES seg_categorias(id_categoria)
);