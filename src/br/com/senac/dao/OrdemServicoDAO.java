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

            // Convertendo java.util.Date para java.sql.Date para o MySQL aceitar corretamente
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

    // 2. CONSULTAR / LISTAR TODAS AS OSs (Read)
    public List<OrdemServico> listarTodas() {
        String sql = "SELECT * FROM ordem_servico";
        List<OrdemServico> lista = new ArrayList<>();

        try (Connection conn = ConexaoJDBC.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OrdemServico os = new OrdemServico();
                os.setIdOs(rs.getInt("idOS"));
                os.setDataAbertura(rs.getDate("dataAbertura"));
                os.setDataEncerramento(rs.getDate("dataEncerramento"));
                os.setStatus(rs.getString("status"));
                os.setResponsavel(rs.getString("responsavel"));
                os.setObservacao(rs.getString("observacao"));
                os.setCusto(rs.getDouble("custo"));

                // Vincula objetos básicos contendo apenas as chaves estrangeiras
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("Cliente_idCliente"));
                os.setCliente(c);

                Equipamento e = new Equipamento();
                e.setIdEquipamento(rs.getInt("equipamentos_idequipamentos"));
                os.setEquipamento(e);

                lista.add(os);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar Ordens de Serviço: " + e.getMessage());
        }
        return lista;
    }

    // 3. ATUALIZAR STATUS OU ENCERRAR (Update)
    public void atualizarStatus(int idOS, String novoStatus, String observacao, double custo, java.util.Date dataEncerramento) {
        String sql = "UPDATE ordem_servico SET status = ?, observacao = ?, custo = ?, dataEncerramento = ? WHERE idOS = ?";

        try (Connection conn = ConexaoJDBC.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setString(2, observacao);
            stmt.setDouble(3, custo);
            
            if (dataEncerramento != null) {
                stmt.setDate(4, new java.sql.Date(dataEncerramento.getTime()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }
            
            stmt.setInt(5, idOS);

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("🔄 Ordem de Serviço atualizada com sucesso!");
            } else {
                System.out.println("⚠️ Nenhuma OS encontrada com o ID informado.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar Ordem de Serviço: " + e.getMessage());
        }
    }
}