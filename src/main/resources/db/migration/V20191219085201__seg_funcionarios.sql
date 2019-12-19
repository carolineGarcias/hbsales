CREATE TABLE seg_funcionarios(

    id_funcionarios BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    nome_funcionario  VARCHAR (50) NOT NULL,
    email_funcionario VARCHAR (50) NOT NULL,
    uuid_funcionario  VARCHAR (36) NOT NULL


)