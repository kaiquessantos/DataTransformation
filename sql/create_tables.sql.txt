-- Tabela para Dados Cadastrais das Operadoras Ativas

CREATE TABLE operadoras_ativas (
    registro_ans VARCHAR(20) PRIMARY KEY,
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    razao_social VARCHAR(255) NOT NULL,
    nome_fantasia VARCHAR(255),
    modalidade VARCHAR(100),
    logradouro VARCHAR(255),
    numero VARCHAR(30),
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    uf CHAR(2),
    cep VARCHAR(8),
    ddd VARCHAR(3),
    telefone VARCHAR(30),
    fax VARCHAR(15),
    endereco_eletronico VARCHAR(255),
    representante VARCHAR(255),
    cargo_representante VARCHAR(100),
    regiao_de_comercializacao VARCHAR(100),
    data_registro_ans DATE
);

-- Tabela para Demonstrações Contábeis

CREATE TABLE demonstracoes_contabeis (
    id SERIAL PRIMARY KEY,
    data DATE,
    reg_ans VARCHAR(20) NOT NULL,
    cd_conta_contabil VARCHAR(20),
    descricao VARCHAR(255),
    vl_saldo_inicial VARCHAR(20),
    vl_saldo_final VARCHAR(20)
);

