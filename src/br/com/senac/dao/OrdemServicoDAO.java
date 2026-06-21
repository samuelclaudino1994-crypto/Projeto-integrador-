package br.com.senac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.senac.jdbc.ConexaoJDBC;
import br.com.senac.model.OrdemServico;
import br.com.senac.model.Cliente;
import br.com.senac.model.Equipamento;

public class OrdemServicoDAO {

    // 1. ABRIR NOVA OS (Create)
    public void abrirOS(OrdemServico os) {
        String sql = "INSERT INTO ordem_servico (dataAbertura, status, responsavel, observacao, custo, Cliente_idCliente, equipamentos_idequipamentos) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoJDBC.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(os.getDataAbertura().getTime()));
            stmt.setString(2, os.getStatus());
            stmt.setString(3, os.getResponsavel());
            stmt.setString(4, os.getObservacao());
            stmt.setDouble(5, os.getCusto());
            stmt.setInt(6, os.getCliente().getIdCliente());
            stmt.setInt(7, os.getEquipamento().getIdEquipamento());

            stmt.executeUpdate();
            System.out.println("🎉 Ordem de Serviço aberta com sucesso!");

        } catch (SQLException e) {
            System.out.println("❌ Erro ao abrir Ordem de Serviço: " + e.getMessage());
        }
    }

    // 2. MÉTODO GENÉRICO COM INNER JOIN (Com a melhoria do .trim())
    public List<OrdemServico> listarOSPorStatus(String status) {
        String sql = "SELECT os.*, c.nome, e.Modelo FROM ordem_servico os " +
                     "INNER JOIN cliente c ON os.Cliente_idCliente = c.idCliente " +
                     "INNER JOIN equipamentos e ON os.equipamentos_idequipamentos = e.idequipamentos";
        
        // Evita que strings contendo apenas espaços passem como um status válido
        if (status != null && !status.trim().isEmpty()) {
            sql += " WHERE os.status = ?";
        }
                     
        List<OrdemServico> lista = new ArrayList<>();

        try (Connection conn = ConexaoJDBC.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (status != null && !status.trim().isEmpty()) {
                stmt.setString(1, status.toUpperCase().trim());
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrdemServico os = new OrdemServico();
                    os.setIdOs(rs.getInt("idOS"));
                    os.setDataAbertura(rs.getDate("dataAbertura"));
                    os.setDataEncerramento(rs.getDate("dataEncerramento"));
                    os.setStatus(rs.getString("status"));
                    os.setResponsavel(rs.getString("responsavel"));
                    os.setObservacao(rs.getString("observacao"));
                    os.setCusto(rs.getDouble("custo"));

                    Cliente c = new Cliente();
                    c.setIdCliente(rs.getInt("Cliente_idCliente"));
                    c.setNome(rs.getString("nome")); 
                    os.setCliente(c);

                    Equipamento e = new Equipamento();
                    e.setIdEquipamento(rs.getInt("equipamentos_idequipamentos"));
                    e.setModelo(rs.getString("Modelo"));
                    os.setEquipamento(e);

                    lista.add(os);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar Ordens de Serviço: " + e.getMessage());
        }
        return lista;
    }

    // 3. LISTAR TODAS AS OS
    public List<OrdemServico> listarOS() {
        return listarOSPorStatus(null);
    }

    // 4. LISTAR APENAS OS ABERTAS
    public List<OrdemServico> listarOSAbertas() {
        return listarOSPorStatus("ABERTA");
    }

    // 5. LISTAR APENAS OS FINALIZADAS
    public List<OrdemServico> listarOSFinalizadas() {
        return listarOSPorStatus("FINALIZADA");
    }

    // 6. ATUALIZAR STATUS (Mantendo 100% em português brasileiro)
    public void atualizarStatus(int idOS, String novoStatus, String novaObservacao) {
        String sql = "UPDATE ordem_servico SET status = ?, observacao = ? WHERE idOS = ?";

        try (Connection conn = ConexaoJDBC.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus.toUpperCase().trim());
            stmt.setString(2, novaObservacao);
            stmt.setInt(3, idOS);

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("🔄 Status da OS nº " + idOS + " atualizado para " + novoStatus + "!");
            } else {
                System.out.println("⚠️ Nenhuma OS encontrada com o ID " + idOS);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar status da OS: " + e.getMessage());
        }
    }

    // 7. ENCERRAR OS
    public void encerrarOS(int idOS, double custoFinal, String observacaoFinal) {
        String sql = "UPDATE ordem_servico SET status = 'FINALIZADA', dataEncerramento = ?, custo = ?, observacao = ? WHERE idOS = ? AND status <> 'FINALIZADA'";

        try (Connection conn = ConexaoJDBC.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
            stmt.setDouble(2, custoFinal);
            stmt.setString(3, observacaoFinal);
            stmt.setInt(4, idOS);

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("🔒 OS nº " + idOS + " encerrada e finalizada com sucesso!");
            } else {
                System.out.println("⚠️ OS não encontrada ou já se encontra FINALIZADA.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao encerrar a OS: " + e.getMessage());
        }
    }
}