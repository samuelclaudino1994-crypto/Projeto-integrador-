package br.com.senac.view;

import br.com.senac.dao.ClienteDAO;
import br.com.senac.dao.EquipamentoDAO;
import br.com.senac.dao.OrdemServicoDAO;
import br.com.senac.model.Cliente;
import br.com.senac.model.Equipamento;
import br.com.senac.model.OrdemServico;
import br.com.senac.model.StatusOS; // Importa o novo Enum

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClienteDAO clienteDao = new ClienteDAO();
    private static final EquipamentoDAO equipamentoDao = new EquipamentoDAO();
    private static final OrdemServicoDAO osDao = new OrdemServicoDAO();

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n=================================");
            System.out.println("         SENAC SOLUTIONS         ");
            System.out.println("=================================");
            System.out.println("1 - Clientes");
            System.out.println("2 - Equipamentos");
            System.out.println("3 - Ordens de Serviço");
            System.out.println("4 - Relatórios Gerenciais");
            System.out.println("0 - Sair");
            System.out.println("=================================");
            System.out.print("Escolha uma opção: ");
            
            opcao = lerInteiro();

            switch (opcao) {
                case 1: menuClientes(); break;
                case 2: menuEquipamentos(); break;
                case 3: menuOS(); break;
                case 4: menuRelatorios(); break;
                case 0: System.out.println("👋 Saindo do sistema... Até logo!"); break;
                default: System.out.println("⚠️ Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
    }

    // ==========================================
    // MENU CLIENTES (Limpo, sem opções vazias)
    // ==========================================
    private static void menuClientes() {
        int op;
        do {
            System.out.println("\n--- MENU CLIENTES ---");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Listar Clientes");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            op = lerInteiro();

            switch (op) {
                case 1:
                    Cliente c = new Cliente();
                    System.out.print("Nome: "); c.setNome(scanner.nextLine());
                    System.out.print("CPF: "); c.setCpf(scanner.nextLine());
                    System.out.print("Telefone: "); c.setTelefone(scanner.nextLine());
                    System.out.print("E-mail: "); c.setEmail(scanner.nextLine());
                    clienteDao.salvar(c);
                    break;
                case 2:
                    exibirTabelaClientes(clienteDao.listarTodos());
                    break;
                case 0: break;
                default: System.out.println("⚠️ Opção inválida!");
            }
        } while (op != 0);
    }

    // ==========================================
    // MENU EQUIPAMENTOS (Limpo, sem opções vazias)
    // ==========================================
    private static void menuEquipamentos() {
        int op;
        do {
            System.out.println("\n--- MENU EQUIPAMENTOS ---");
            System.out.println("1 - Cadastrar Equipamento");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            op = lerInteiro();

            switch (op) {
                case 1:
                    Equipamento e = new Equipamento();
                    System.out.print("Tipo (Celular/Notebook...): "); e.setTipo(scanner.nextLine());
                    System.out.print("Marca: "); e.setMarca(scanner.nextLine());
                    System.out.print("Modelo: "); e.setModelo(scanner.nextLine());
                    System.out.print("Nº de Série: "); e.setNumSerie(scanner.nextLine());
                    System.out.print("Descrição do Defeito: "); e.setDescricaoDefeito(scanner.nextLine());
                    System.out.print("ID do Cliente Dono: "); int idClie = lerInteiro();
                    
                    Cliente dono = new Cliente(); dono.setIdCliente(idClie); e.setCliente(dono);
                    equipamentoDao.salvar(e);
                    break;
                case 0: break;
                default: System.out.println("⚠️ Opção inválida!");
            }
        } while (op != 0);
    }

    // ==========================================
    // MENU ORDENS DE SERVIÇO
    // ==========================================
    private static void menuOS() {
        int op;
        do {
            System.out.println("\n--- MENU ORDENS DE SERVIÇO ---");
            System.out.println("1 - Abrir Ordem de Serviço");
            System.out.println("2 - Listar Todas as OS");
            System.out.println("3 - Atualizar Status");
            System.out.println("4 - Encerrar OS");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            op = lerInteiro();

            switch (op) {
                case 1: tratarAberturaOS(); break;
                case 2: exibirTabelaOS(osDao.listarOS()); break;
                case 3: tratarAtualizacaoStatus(); break;
                case 4: tratarEncerramentoOS(); break;
                case 0: break;
                default: System.out.println("⚠️ Opção inválida!");
            }
        } while (op != 0);
    }

    private static void tratarAberturaOS() {
        OrdemServico os = new OrdemServico();
        os.setDataAbertura(new Date());
        os.setStatus(StatusOS.ABERTA.name()); // Usa o Enum de forma segura
        
        System.out.print("Técnico Responsável: "); os.setResponsavel(scanner.nextLine());
        System.out.print("Observação Inicial: "); os.setObservacao(scanner.nextLine());
        System.out.print("Custo Inicial (R$): "); os.setCusto(lerDouble());
        System.out.print("ID do Cliente: "); int idC = lerInteiro();
        System.out.print("ID do Equipamento: "); int idE = lerInteiro();
        
        Cliente c = new Cliente(); c.setIdCliente(idC); os.setCliente(c);
        Equipamento e = new Equipamento(); e.setIdEquipamento(idE); os.setEquipamento(e);
        osDao.abrirOS(os);
    }

    // Blindagem total contra inserção de texto inválido no Status
    private static void tratarAtualizacaoStatus() {
        System.out.print("Número da OS: "); 
        int idA = lerInteiro();
        
        System.out.println("\nSelecione o novo Status:");
        System.out.println("1 - EM ANDAMENTO");
        System.out.println("2 - EM MANUTENÇÃO");
        System.out.print("Opção: ");
        int escolhaStatus = lerInteiro();
        
        String statusSelecionado;
        switch (escolhaStatus) {
            case 1: statusSelecionado = StatusOS.EM_ANDAMENTO.name(); break;
            case 2: statusSelecionado = StatusOS.EM_MANUTENCAO.name(); break;
            default:
                System.out.println("⚠️ Opção de status inválida. Operação cancelada.");
                return;
        }
        
        System.out.print("Nova Observação Técnica: "); 
        String ob = scanner.nextLine();
        
        osDao.atualizarStatus(idA, statusSelecionado, ob);
    }

    private static void tratarEncerramentoOS() {
        System.out.print("Número da OS para ENCERRAR: "); int idM = lerInteiro();
        System.out.print("Custo Final Total (R$): "); double valor = lerDouble();
        System.out.print("Observação de Encerramento: "); String obsF = scanner.nextLine();
        osDao.encerrarOS(idM, valor, obsF);
    }

    // ==========================================
    // MENU RELATÓRIOS
    // ==========================================
    private static void menuRelatorios() {
        int op;
        do {
            System.out.println("\n--- MENU RELATÓRIOS ---");
            System.out.println("1 - Clientes cadastrados");
            System.out.println("2 - OS Abertas");
            System.out.println("3 - OS Finalizadas");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            op = lerInteiro();

            switch (op) {
                case 1: exibirTabelaClientes(clienteDao.listarTodos()); break;
                case 2: exibirTabelaOS(osDao.listarOSAbertas()); break;
                case 3: exibirTabelaOS(osDao.listarOSFinalizadas()); break;
                case 0: break;
                default: System.out.println("⚠️ Opção inválida!");
            }
        } while (op != 0);
    }

    // ==========================================
    // EXIBIÇÃO DE TABELAS COM PROTEÇÃO NULL-POINTER
    // ==========================================
    private static void exibirTabelaOS(List<OrdemServico> lista) {
        System.out.println("\n=======================================================================================");
        if (lista == null || lista.isEmpty()) {
            System.out.println("                     ⚠️ Nenhum registro de OS encontrado.                     ");
        } else {
            System.out.printf("%-6s | %-20s | %-20s | %-15s | %-12s%n", "Nº OS", "CLIENTE", "EQUIPAMENTO", "STATUS", "CUSTO (R$)");
            System.out.println("---------------------------------------------------------------------------------------");
            for (OrdemServico os : lista) {
                String nomeCliente = (os.getCliente() != null) ? os.getCliente().getNome() : "Não Informado";
                String modeloEquip = (os.getEquipamento() != null) ? os.getEquipamento().getModelo() : "Não Informado";

                System.out.printf("%-6d | %-20s | %-20s | %-15s | R$ %-9.2f%n", 
                        os.getIdOs(),
                        limitarTexto(nomeCliente, 20),
                        limitarTexto(modeloEquip, 20),
                        os.getStatus(), os.getCusto());
            }
        }
        System.out.println("=======================================================================================");
    }

    private static void exibirTabelaClientes(List<Cliente> lista) {
        System.out.println("\n=======================================================================================");
        if (lista == null || lista.isEmpty()) {
            System.out.println("                     ⚠️ Nenhum cliente cadastrado.                     ");
        } else {
            System.out.printf("%-5s | %-25s | %-15s | %-25s%n", "ID", "NOME", "TELEFONE", "EMAIL");
            System.out.println("---------------------------------------------------------------------------------------");
            for (Cliente c : lista) {
                System.out.printf("%-5d | %-25s | %-15s | %-25s%n", c.getIdCliente(), limitarTexto(c.getNome(), 25), c.getTelefone(), c.getEmail());
            }
        }
        System.out.println("=======================================================================================");
    }

    private static String limitarTexto(String texto, int max) {
        if (texto == null) return "";
        return (texto.length() <= max) ? texto : texto.substring(0, max - 3) + "...";
    }

    // ==========================================
    // CAPTURA DE ENTRADAS BLINDADAS
    // ==========================================
    private static int lerInteiro() {
        while (true) {
            try {
                int i = scanner.nextInt();
                scanner.nextLine(); 
                return i;
            } catch (InputMismatchException e) {
                System.out.println("❌ Entrada inválida! Digite apenas números inteiros.");
                scanner.nextLine(); 
                System.out.print("Tente novamente: ");
            }
        }
    }

    private static double lerDouble() {
        while (true) {
            try {
                double d = scanner.nextDouble();
                scanner.nextLine(); 
                return d;
            } catch (InputMismatchException e) {
                System.out.println("❌ Preço inválido! Use apenas números e vírgula/ponto.");
                scanner.nextLine(); 
                System.out.print("Tente novamente (Ex: 150,50): ");
            }
        }
    }
}