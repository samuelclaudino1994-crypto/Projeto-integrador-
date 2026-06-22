package br.com.senac.view;

import br.com.senac.dao.ClienteDAO;
import br.com.senac.dao.EquipamentoDAO;
import br.com.senac.dao.OrdemServicoDAO;
import br.com.senac.model.Cliente;
import br.com.senac.model.Equipamento;
import br.com.senac.model.OrdemServico;
import br.com.senac.model.StatusOS;

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
            System.out.print("Escolha uma opção: ");
            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuEquipamentos();
                    break;
                case 3:
                    menuOrdensServico();
                    break;
                case 4:
                    menuRelatorios();
                    break;
                case 0:
                    System.out.println("👋 Sistema encerrado. Até logo!");
                    break;
                default:
                    System.out.println("⚠️ Opção inválida!");
            }
        } while (opcao != 0);
    }

    // ==========================================
    // MÓDULO CLIENTES (CRUD COMPLETO)
    // ==========================================
    private static void menuClientes() {
        int op;
        do {
            System.out.println("\n--- MENU CLIENTES ---");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Listar Clientes");
            System.out.println("3 - Alterar Cliente");
            System.out.println("4 - Excluir Cliente");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            op = lerInteiro();

            switch (op) {
                case 1:
                    Cliente c = new Cliente();
                    System.out.print("Nome: ");
                    c.setNome(scanner.nextLine());
                    System.out.print("CPF: ");
                    c.setCpf(scanner.nextLine());
                    System.out.print("Telefone: ");
                    c.setTelefone(scanner.nextLine());
                    System.out.print("E-mail: ");
                    c.setEmail(scanner.nextLine());
                    clienteDao.salvar(c);
                    break;
                case 2:
                    exibirTabelaClientes(clienteDao.listarTodos());
                    break;
                case 3:
                    System.out.print("Digite o ID do Cliente que deseja alterar: ");
                    int idAlt = lerInteiro();
                    Cliente cAlt = new Cliente();
                    cAlt.setIdCliente(idAlt);
                    System.out.print("Novo Nome: ");
                    cAlt.setNome(scanner.nextLine());
                    System.out.print("Novo CPF: ");
                    cAlt.setCpf(scanner.nextLine());
                    System.out.print("Novo Telefone: ");
                    cAlt.setTelefone(scanner.nextLine());
                    System.out.print("Novo E-mail: ");
                    cAlt.setEmail(scanner.nextLine());
                    clienteDao.atualizar(cAlt);
                    break;
                case 4:
                    System.out.print("Digite o ID do Cliente que deseja excluir: ");
                    int idExc = lerInteiro();
                    System.out.print("Tem certeza que deseja excluir o cliente ID " + idExc + "? (S/N): ");
                    String conf = scanner.nextLine().trim().toUpperCase();
                    if (conf.equals("S")) {
                        clienteDao.excluir(idExc);
                    } else {
                        System.out.println("❌ Operação cancelada.");
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("⚠️ Opção inválida!");
            }
        } while (op != 0);
    }

    // ==========================================
    // MÓDULO EQUIPAMENTOS (CRUD COMPLETO)
    // ==========================================
    private static void menuEquipamentos() {
        int op;
        do {
            System.out.println("\n--- MENU EQUIPAMENTOS ---");
            System.out.println("1 - Cadastrar Equipamento");
            System.out.println("2 - Listar Equipamentos");
            System.out.println("3 - Alterar Equipamento");
            System.out.println("4 - Excluir Equipamento");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            op = lerInteiro();

            switch (op) {
                case 1:
                    Equipamento e = new Equipamento();
                    e.setTipo(escolherTipoEquipamento());
                    System.out.print("Marca: ");
                    e.setMarca(scanner.nextLine());
                    System.out.print("Modelo: ");
                    e.setModelo(scanner.nextLine());
                    System.out.print("Nº de Série: ");
                    e.setNumSerie(scanner.nextLine());
                    System.out.print("Descrição do Defeito: ");
                    e.setDescricaoDefeito(scanner.nextLine());
                    System.out.print("ID do Cliente Dono: ");
                    int idClie = lerInteiro();

                    Cliente dono = new Cliente();
                    dono.setIdCliente(idClie);
                    e.setCliente(dono);
                    equipamentoDao.salvar(e);
                    break;
                case 2:
                    exibirTabelaEquipamentos(equipamentoDao.listarTodos());
                    break;
                case 3:
                    System.out.print("Digite o ID do Equipamento que deseja alterar: ");
                    int idEqAlt = lerInteiro();
                    Equipamento eAlt = new Equipamento();
                    eAlt.setIdEquipamento(idEqAlt);
                    eAlt.setTipo(escolherTipoEquipamento());
                    System.out.print("Nova Marca: ");
                    eAlt.setMarca(scanner.nextLine());
                    System.out.print("Novo Modelo: ");
                    eAlt.setModelo(scanner.nextLine());
                    System.out.print("Novo Nº de Série: ");
                    eAlt.setNumSerie(scanner.nextLine());
                    System.out.print("Nova Descrição do Defeito: ");
                    eAlt.setDescricaoDefeito(scanner.nextLine());
                    System.out.print("ID do Novo Cliente Dono: ");
                    int idClieAlt = lerInteiro();

                    Cliente donoAlt = new Cliente();
                    donoAlt.setIdCliente(idClieAlt);
                    eAlt.setCliente(donoAlt);
                    equipamentoDao.atualizar(eAlt);
                    break;
                case 4:
                    System.out.print("Digite o ID do Equipamento que deseja excluir: ");
                    int idEqExc = lerInteiro();
                    System.out.print("Tem certeza que deseja excluir o equipamento ID " + idEqExc + "? (S/N): ");
                    String confEq = scanner.nextLine().trim().toUpperCase();
                    if (confEq.equals("S")) {
                        equipamentoDao.excluir(idEqExc);
                    } else {
                        System.out.println("❌ Operação cancelada.");
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("⚠️ Opção inválida!");
            }
        } while (op != 0);
    }

    private static String escolherTipoEquipamento() {
        System.out.println("\nSelecione o Tipo de Equipamento:");
        System.out.println("1 - Notebook");
        System.out.println("2 - Desktop (Computador)");
        System.out.println("3 - Servidor");
        System.out.println("4 - Outro (Informática)");
        System.out.print("Opção: ");
        int opcaoTipo = lerInteiro();
        switch (opcaoTipo) {
            case 1:
                return "Notebook";
            case 2:
                return "Desktop";
            case 3:
                return "Servidor";
            default:
                return "Outro (Informática)";
        }
    }

    // ==========================================
    // MÓDULO ORDENS DE SERVIÇO
    // ==========================================
    private static void menuOrdensServico() {
        int op;
        do {
            System.out.println("\n--- MENU ORDENS DE SERVIÇO ---");
            System.out.println("1 - Abrir Nova OS");
            System.out.println("2 - Atualizar Status de OS");
            System.out.println("3 - Encerrar OS (Finalizar)");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            op = lerInteiro();

            switch (op) {
                case 1:
                    OrdemServico os = new OrdemServico();
                    os.setDataAbertura(new Date());
                    os.setStatus(StatusOS.ABERTA.name());

                    System.out.print("Nome do Técnico Responsável: ");
                    os.setResponsavel(scanner.nextLine());
                    System.out.print("Observações Iniciais: ");
                    os.setObservacao(scanner.nextLine());
                    os.setCusto(0.0);

                    System.out.print("ID do Cliente: ");
                    int idC = lerInteiro();
                    System.out.print("ID do Equipamento: ");
                    int idE = lerInteiro();

                    Cliente c = new Cliente();
                    c.setIdCliente(idC);
                    os.setCliente(c);
                    Equipamento e = new Equipamento();
                    e.setIdEquipamento(idE);
                    os.setEquipamento(e);

                    osDao.abrirOS(os);
                    break;

                case 2:
                    System.out.print("Digite o ID da OS que deseja atualizar: ");
                    int idOSStatus = lerInteiro();

                    System.out.println("\nSelecione o Novo Status:");
                    System.out.println("1 - ABERTA");
                    System.out.println("2 - EM MANUTENÇÃO");
                    System.out.println("3 - FINALIZADA");
                    System.out.print("Opção: ");
                    int opStatus = lerInteiro();

                    String novoStatus;
                    switch (opStatus) {
                        case 1:
                            novoStatus = StatusOS.ABERTA.name();
                            break;
                        case 2:
                            novoStatus = StatusOS.EM_MANUTENCAO.name();
                            break;
                        case 3:
                            novoStatus = StatusOS.FINALIZADA.name();
                            break;
                        default:
                            System.out.println("⚠️ Opção inválida! Operação abortada.");
                            return;
                    }

                    System.out.print("Atualizar Observação/Histórico do Serviço: ");
                    String novaObs = scanner.nextLine();

                    // Ajustado para casar com os 3 parâmetros do seu DAO: id, status, observacao
                    osDao.atualizarStatus(idOSStatus, novoStatus, novaObs);
                    break;

                case 3:
                    System.out.print("Digite o ID da OS que deseja Encerrar/Finalizar: ");
                    int idOSEncerrar = lerInteiro();
                    System.out.print("Valor/Custo Total do Serviço (R$): ");
                    double custo = lerDouble();
                    System.out.print("Laudo Técnico / Observações Finais: ");
                    String obsFinais = scanner.nextLine();

                    osDao.encerrarOS(idOSEncerrar, custo, obsFinais);
                    break;

                case 0:
                    break;
                default:
                    System.out.println("⚠️ Opção inválida!");
            }
        } while (op != 0);
    }

    // ==========================================
    // MÓDULO RELATÓRIOS GERENCIAIS (CORRIGIDO)
    // ==========================================
    private static void menuRelatorios() {
        int op;
        do {
            System.out.println("\n--- RELATÓRIOS GERENCIAIS ---");
            System.out.println("1 - Listar OS em Andamento (Abertas / Em Manutenção)");
            System.out.println("2 - Listar OS Finalizadas");
            System.out.println("3 - Listar Todas as OS");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            op = lerInteiro();

            // Puxamos a lista completa que já sabemos que funciona 100% no seu DAO
            List<OrdemServico> todasOS = osDao.listarOS();

            switch (op) {
                case 1:
                    System.out.println("\n📋 RELATÓRIO: ORDENS DE SERVIÇO EM ANDAMENTO");
                    // Criamos uma lista filtrando apenas o que NÃO está finalizado
                    List<OrdemServico> emAndamento = todasOS.stream()
                        .filter(os -> !os.getStatus().equalsIgnoreCase("FINALIZADA"))
                        .toList();
                    exibirTabelaOS(emAndamento); 
                    break;
                case 2:
                    System.out.println("\n📋 RELATÓRIO: ORDENS DE SERVIÇO FINALIZADAS");
                    // Filtramos apenas o que está finalizado
                    List<OrdemServico> finalizadas = todasOS.stream()
                        .filter(os -> os.getStatus().equalsIgnoreCase("FINALIZADA"))
                        .toList();
                    exibirTabelaOS(finalizadas);
                    break;
                case 3:
                    System.out.println("\n📋 RELATÓRIO: HISTÓRICO GERAL DE OS");
                    exibirTabelaOS(todasOS);
                    break;
                case 0: break;
                default: System.out.println("⚠️ Opção inválida!");
            }
        } while (op != 0);
    }

    // ==========================================
    // MÉTODOS DE RENDERIZAÇÃO DE TABELAS (CONSOLE)
    // ==========================================
    private static void exibirTabelaClientes(List<Cliente> clientes) {
        System.out.println("\n=======================================================================================");
        System.out.println(
                String.format("| %-4s | %-25s | %-15s | %-15s | %-20s |", "ID", "NOME", "CPF", "TELEFONE", "EMAIL"));
        System.out.println("=======================================================================================");
        if (clientes.isEmpty()) {
            System.out
                    .println("|                      Nenhum cliente cadastrado no momento.                          |");
        } else {
            for (Cliente c : clientes) {
                System.out.println(String.format("| %-4d | %-25s | %-15s | %-15s | %-20s |",
                        c.getIdCliente(),
                        limitarTexto(c.getNome(), 25),
                        limitarTexto(c.getCpf(), 15),
                        limitarTexto(c.getTelefone(), 15),
                        limitarTexto(c.getEmail(), 20)));
            }
        }
        System.out.println("=======================================================================================");
    }

    private static void exibirTabelaEquipamentos(List<Equipamento> equipamentos) {
        System.out.println("\n=======================================================================================");
        System.out.println(String.format("| %-4s | %-12s | %-12s | %-15s | %-12s | %-15s |", "ID", "TIPO", "MARCA",
                "MODELO", "Nº SÉRIE", "DONO (ID)"));
        System.out.println("=======================================================================================");
        if (equipamentos.isEmpty()) {
            System.out
                    .println("|                     Nenhum equipamento cadastrado no momento.                       |");
        } else {
            for (Equipamento e : equipamentos) {
                String donoInfo = (e.getCliente() != null) ? String.valueOf(e.getCliente().getIdCliente()) : "N/C";
                System.out.println(String.format("| %-4d | %-12s | %-12s | %-15s | %-12s | %-15s |",
                        e.getIdEquipamento(),
                        limitarTexto(e.getTipo(), 12),
                        limitarTexto(e.getMarca(), 12),
                        limitarTexto(e.getModelo(), 15),
                        limitarTexto(e.getNumSerie(), 12),
                        donoInfo));
            }
        }
        System.out.println("=======================================================================================");
    }

    private static void exibirTabelaOS(List<OrdemServico> listaOS) {
        System.out.println("\n=======================================================================================");
        System.out.println(String.format("| %-6s | %-12s | %-15s | %-12s | %-8s | %-20s |", "ID OS", "STATUS",
                "RESPONSÁVEL", "CUSTO (R$)", "EQP ID", "CLIENTE"));
        System.out.println("=======================================================================================");
        if (listaOS.isEmpty()) {
            System.out
                    .println("|                     Nenhuma Ordem de Serviço encontrada.                            |");
        } else {
            for (OrdemServico os : listaOS) {
                String nomeDono = (os.getCliente() != null) ? os.getCliente().getNome() : "Desconhecido";
                String idEq = (os.getEquipamento() != null) ? String.valueOf(os.getEquipamento().getIdEquipamento())
                        : "N/C";
                System.out.println(String.format("| %-6d | %-12s | %-15s | R$ %-9.2f | %-8s | %-20s |",
                        os.getIdOs(),
                        os.getStatus(),
                        limitarTexto(os.getResponsavel(), 15),
                        os.getCusto(),
                        idEq,
                        limitarTexto(nomeDono, 20)));
            }
        }
        System.out.println("=======================================================================================");
    }

    private static String limitarTexto(String texto, int max) {
        if (texto == null)
            return "";
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