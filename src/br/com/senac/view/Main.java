package br.com.senac.view;

import br.com.senac.dao.OrdemServicoDAO;
import br.com.senac.model.Cliente;
import br.com.senac.model.Equipamento;
import br.com.senac.model.OrdemServico;
import java.util.Date;

public class Main {
   public static void main(String[] args) {
        System.out.println("🧪 Iniciando teste de abertura de OS...");

        // 1. Instancia o cliente e equipamento existentes no banco (IDs que já existem)
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1); 

        Equipamento equipamento = new Equipamento();
        equipamento.setIdEquipamento(1); // Mude para o ID real gerado para o equipamento

        // 2. Cria a nova OS (ID 0 porque é AUTO_INCREMENT)
        OrdemServico novaOs = new OrdemServico(
            0, 
            new Date(), // Data de abertura atual
            null,       // Data de encerramento começa nula
            "ABERTA", 
            "Técnico Samuel", 
            "Verificar curto na placa-mãe", 
            150.00, 
            cliente, 
            equipamento
        );

        // 3. Salva no banco de dados
        OrdemServicoDAO osDao = new OrdemServicoDAO();
        osDao.abrirOS(novaOs);
    }
}