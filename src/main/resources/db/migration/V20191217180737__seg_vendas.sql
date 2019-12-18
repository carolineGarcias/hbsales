CREATE TABLE seg_vendas(
    id_vendas BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    inicio_vendas    DATE NOT NULL,
    fim_vendas       DATE NOT NULL,
    retirada_pedido  DATE NOT NULL,
    descricao        VARCHAR (50) NOT NULL,
    id_vendas_fornecedor BIGINT UNIQUE FOREIGN KEY REFERENCES seg_fornecedores(id_fornecedor),

);
