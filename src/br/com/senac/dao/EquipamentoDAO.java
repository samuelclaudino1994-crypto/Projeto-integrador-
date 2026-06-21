package br.com.senac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.senac.jdbc.ConexaoJDBC;
import br.com.senac.model.Equipamento;
import br.com.senac.model.Cliente;

public class EquipamentoDAO {

    // CREATE - Salvar equipamento vinculado a um cliente
    public void salvar(Equipamento equipamento) {
        // Usando os nomes exatos da sua tabela: idequipamentos não entra por ser AUTO_INCREMENT
        String sql = "INSERT INTO equipamentos (Tipo, Marca, Modelo, numSerie, descricaoDefeito, Cliente_idCliente) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoJDBC.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, equipamento.getTipo());
            stmt.setString(2, equipamento.getMarca());
            stmt.setString(3, equipamento.getModelo());
            stmt.setString(4, equipamento.getNumSerie());
            stmt.setString(5, equipamento.getDescricaoDefeito());
            
            // Pega o idCliente de dentro do objeto Cliente que está associado ao Equipamento
            stmt.setInt(6, equipamento.getCliente().getIdCliente());

            stmt.executeUpdate();
            System.out.println("🎉 Equipamento cadastrado e vinculado ao cliente com sucesso!");

        } catch (SQLException e) {
            System.out.println("❌ Erro ao salvar equipamento: " + e.getMessage());
        }
    }

    // READ - Listar todos os equipamentos (e traz junto o ID do dono)
    public List<Equipamento> listarTodos() {
        String sql = "SELECT * FROM equipamentos";
        List<Equipamento> lista = new ArrayList<>();

        try (Connection conn = ConexaoJDBC.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Equipamento eq = new Equipamento();
                
                // Mapeando os nomes exatos das colunas do seu banco:
                eq.setIdEquipamento(rs.getInt("idequipamentos"));
                eq.setTipo(rs.getString("Tipo"));
                eq.setMarca(rs.getString("Marca"));
                eq.setModelo(rs.getString("Modelo"));
                eq.setNumSerie(rs.getString("numSerie"));
                eq.setDescricaoDefeito(rs.getString("descricaoDefeito"));

                // Cria um objeto cliente básico só para guardar o ID do dono do equipamento
                Cliente dono = new Cliente();
                dono.setIdCliente(rs.getInt("Cliente_idCliente"));
                eq.setCliente(dono);

                lista.add(eq);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar equipamentos: " + e.getMessage());
        }
        return lista;
    }

    // UPDATE - Alterar dados de um equipamento
    public void atualizar(Equipamento equipamento) {
        String sql = "UPDATE equipamentos SET Tipo = ?, Marca = ?, Modelo = ?, numSerie = ?, descricaoDefeito = ?, Cliente_idCliente = ? WHERE idequipamentos = ?";

        try (Connection conn = ConexaoJDBC.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, equipamento.getTipo());
            stmt.setString(2, equipamento.getMarca());
            stmt.setString(3, equipamento.getModelo());
            stmt.setString(4, equipamento.getNumSerie());
            stmt.setString(5, equipamento.getDescricaoDefeito());
            stmt.setInt(6, equipamento.getCliente().getIdCliente());
            stmt.setInt(7, equipamento.getIdEquipamento()); // WHERE idequipamentos = ?

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("🔄 Equipamento atualizado com sucesso!");
            } else {
                System.out.println("⚠️ Nenhum equipamento encontrado com o ID informado.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar equipamento: " + e.getMessage());
        }
    }

    // DELETE - Excluir um equipamento pelo ID
    public void excluir(int idEquipamento) {
        String sql = "DELETE FROM equipamentos WHERE idequipamentos = ?";

        try (Connection conn = ConexaoJDBC.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEquipamento);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("🗑️ Equipamento excluído com sucesso!");
            } else {
                System.out.println("⚠️ Nenhum equipamento encontrado com o ID informado.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao excluir equipamento: " + e.getMessage());
        }
    }
}