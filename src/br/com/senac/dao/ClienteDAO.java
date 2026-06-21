package br.com.senac.dao; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.senac.jdbc.ConexaoJDBC; 
import br.com.senac.model.Cliente;

public class ClienteDAO {

    // CREATE - Salvar cliente (idCliente não entra por ser AUTO_INCREMENT)
    public void salvar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, Cpf, Telefone, email) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexaoJDBC.conectar(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            
            stmt.executeUpdate();
            System.out.println("🎉 Cliente salvo com sucesso no banco de dados!");
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao salvar cliente: " + e.getMessage());
        }
    }

    // READ - Listar todos os clientes
    public List<Cliente> listarTodos() {
        String sql = "SELECT * FROM cliente";
        List<Cliente> lista = new ArrayList<>();

        try (Connection conn = ConexaoJDBC.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                
                // BATENDO EXATAMENTE COM AS COLUNAS DA SUA TABELA:
                cliente.setIdCliente(rs.getInt("idCliente")); 
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("Cpf"));
                cliente.setTelefone(rs.getString("Telefone"));
                cliente.setEmail(rs.getString("email"));

                lista.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar clientes: " + e.getMessage());
        }
        return lista;
    }

    // UPDATE - Alterar dados de um cliente
    public void atualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, Cpf = ?, Telefone = ?, email = ? WHERE idCliente = ?";

        try (Connection conn = ConexaoJDBC.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setInt(5, cliente.getIdCliente()); 

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("🔄 Cliente atualizado com sucesso!");
            } else {
                System.out.println("⚠️ Nenhum cliente encontrado com o idCliente informado.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    // DELETE - Excluir um cliente
    public void excluir(int idCliente) {
        String sql = "DELETE FROM cliente WHERE idCliente = ?";

        try (Connection conn = ConexaoJDBC.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("🗑️ Cliente excluído com sucesso!");
            } else {
                System.out.println("⚠️ Nenhum cliente encontrado com o idCliente informado.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao excluir cliente: " + e.getMessage());
        }
    }
}