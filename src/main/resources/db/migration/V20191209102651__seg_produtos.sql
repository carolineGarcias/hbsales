create  table seg_produtos(

        id_produto BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        nome_produto        VARCHAR(100) NOT NULL,
        cod_produto         VARCHAR (10) NOT NULL UNIQUE,
        preco_produto       decimal(9,2) NOT NULL,
        unidade_produto     decimal(9,2) NOT NULL,
        peso_produto        decimal(9,3)  NOT NULL,
        validade_produto    DATE          NOT NULL,
        id_produto_linha BIGINT FOREIGN KEY
        REFERENCES seg_linhas(id_linha)
);
