create TABLE seg_pedidos(

    id_pedido  BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    codigo_pedido VARCHAR(10) NOT NULL UNIQUE,
    status_pedido VARCHAR(10) NOT NULL,
    data_pedido DATE NOT NULL,
    quantidade_pedido BIGINT NOT NULL,
    id_pedido_funcionario BIGINT FOREIGN KEY REFERENCES seg_funcionarios(id_funcionarios),
    id_pedido_produto BIGINT     FOREIGN KEY REFERENCES seg_produtos(id_produto),
    id_pedido_vendas BIGINT      FOREIGN KEY REFERENCES seg_linhas(id_vendas),




)