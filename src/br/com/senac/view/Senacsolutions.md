# Senac Solutions - Sistema de Gestão de Ordens de Serviço

## Sobre o Projeto

O Senac Solutions é um sistema desenvolvido em Java para gerenciamento de clientes, equipamentos e ordens de serviço de uma assistência técnica.

O objetivo do projeto é automatizar o controle de atendimentos técnicos, cadastro de clientes, equipamentos recebidos para manutenção e acompanhamento de ordens de serviço.

Este projeto foi desenvolvido como parte do Projeto Integrador do programa Jovem Programador da Faculdade Senac Blumenau.

---

## Objetivos do Sistema

O sistema permite:

* Gerenciar clientes da assistência técnica
* Controlar equipamentos recebidos para manutenção
* Abrir ordens de serviço
* Atualizar o andamento dos atendimentos
* Encerrar serviços concluídos
* Emitir relatórios gerenciais

---

## Tecnologias Utilizadas

* Java
* Programação Orientada a Objetos (POO)
* JDBC
* MySQL
* Git
* GitHub
* VS Code

---

## Estrutura do Projeto

src/

├── br.com.senac.dao

│   ├── ClienteDAO.java

│   ├── EquipamentoDAO.java

│   └── OrdemServicoDAO.java

│

├── br.com.senac.jdbc

│   └── ConexaoJDBC.java

│

├── br.com.senac.model

│   ├── Cliente.java

│   ├── Equipamento.java

│   ├── OrdemServico.java

│   └── StatusOS.java

│

└── br.com.senac.view

```
└── Main.java
```

---

## Funcionalidades Implementadas

### Gestão de Clientes

* Cadastro de clientes
* Consulta de clientes
* Atualização de clientes
* Exclusão de clientes

### Gestão de Equipamentos

* Cadastro de equipamentos
* Consulta de equipamentos
* Atualização de equipamentos
* Exclusão de equipamentos
* Associação de equipamento ao cliente

### Gestão de Ordens de Serviço

* Abertura de OS
* Consulta de OS
* Consulta de OS abertas
* Consulta de OS finalizadas
* Atualização de status
* Encerramento de OS

### Relatórios

* Clientes cadastrados
* Ordens de serviço abertas
* Ordens de serviço finalizadas

---

## Banco de Dados

O sistema utiliza MySQL como banco de dados.

### Principais Tabelas

### Cliente

* idCliente
* nome
* cpf
* telefone
* email

### Equipamentos

* idequipamentos
* tipo
* marca
* modelo
* numSerie
* descricaoDefeito
* Cliente_idCliente

### Ordem_Servico

* idOS
* dataAbertura
* dataEncerramento
* status
* responsavel
* observacao
* custo
* Cliente_idCliente
* equipamentos_idequipamentos

---

## Relacionamentos

Cliente (1) ------ (N) Equipamentos

Cliente (1) ------ (N) Ordem de Serviço

Equipamento (1) ------ (N) Ordem de Serviço

---

## Como Executar o Projeto

### 1 - Clonar o Repositório

git clone https://github.com/samuelclaudino1994-crypto/Projeto-integrador-

### 2 - Criar Banco de Dados

Executar o script SQL disponibilizado no projeto.

### 3 - Configurar a Conexão

Arquivo:

ConexaoJDBC.java

Configurar:

* URL
* Usuário
* Senha

### 4 - Executar o Projeto

Executar a classe:

Main.java

---

## Regras de Negócio

* Toda Ordem de Serviço deve estar vinculada a um cliente.
* Todo equipamento deve pertencer a um cliente.
* Uma OS pode ser aberta apenas para equipamentos cadastrados.
* Uma OS finalizada não pode ser encerrada novamente.
* O sistema registra data de abertura e data de encerramento.

---

## Melhorias Futuras

* Interface gráfica utilizando JavaFX
* Service Layer
* Exportação de relatórios PDF
* Dashboard gerencial
* Validação de CPF
* Validação de e-mail
* Controle de usuários e autenticação

---

## Autor

Samuel de Moura Claudino

Projeto Integrador – Jovem Programador

Faculdade Senac Blumenau
