CREATE TABLE seg_categorias(
    id_categoria BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    nome_categoria           VARCHAR(50) NOT NULL,
    codigo_categoria         VARCHAR(10) NOT NULL UNIQUE,
    id_categoria_fornecedor BIGINT FOREIGN KEY REFERENCES seg_fornecedores (id_fornecedor)
);
