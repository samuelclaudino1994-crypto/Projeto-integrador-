USE senac_solutions;

-- 1. Criação correta da Tabela Cliente
CREATE TABLE cliente (
    idCliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    Cpf VARCHAR(14) NOT NULL UNIQUE,
    Telefone VARCHAR(10) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- 2. Criação correta da Tabela equipamentos (Plural, igual ao diagrama)
CREATE TABLE equipamentos (
    idequipamentos INT AUTO_INCREMENT PRIMARY KEY,
    Tipo VARCHAR(50) NOT NULL,
    Marca VARCHAR(50) NOT NULL,
    Modelo VARCHAR(50) NOT NULL,
    numSerie VARCHAR(50) NOT NULL,
    descricaoDefeito TEXT NOT NULL,
    Cliente_idCliente INT NOT NULL,
    FOREIGN KEY (Cliente_idCliente) REFERENCES Cliente(idCliente) ON DELETE CASCADE
);

-- 3. Criação correta da Tabela ordem_servico (Com as suas datas e custos)
CREATE TABLE ordem_servico (
    idOS INT AUTO_INCREMENT PRIMARY KEY,
    dataAbertura DATE NOT NULL,
    dataEncerramento DATE,
    status VARCHAR(30) NOT NULL DEFAULT 'ABERTA',
    responsavel VARCHAR(100) NOT NULL,
    observacao TEXT,
    custo DECIMAL(10,2) DEFAULT 0.00,
    Cliente_idCliente INT NOT NULL,
    equipamentos_idequipamentos INT NOT NULL,
    FOREIGN KEY (Cliente_idCliente) REFERENCES Cliente(idCliente) ON DELETE CASCADE,
    FOREIGN KEY (equipamentos_idequipamentos) REFERENCES equipamentos(idequipamentos) ON DELETE CASCADE
);

ALTER TABLE cliente MODIFY Telefone VARCHAR(15) NOT NULL;


select*from cliente;