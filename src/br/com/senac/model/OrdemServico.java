package br.com.senac.model;
import java.util.Date;

public class OrdemServico {
    private int idOs;
    private Date dataAbertura;
    private Date dataEncerramento;
    private String status;
    private String responsavel;
    private String observacao;
    private double custo;
    private Cliente cliente;
    private Equipamento equipamento;

    public OrdemServico() {}
    public OrdemServico(int idOs, Date dataAbertura, Date dataEncerramento, String status, String responsavel, String observacao, double custo, Cliente cliente, Equipamento equipamento) {
        this.idOs = idOs;
        this.dataAbertura = dataAbertura;
        this.dataEncerramento = dataEncerramento;
        this.status = status;
        this.responsavel = responsavel;
        this.observacao = observacao;
        this.custo = custo;
        this.cliente = cliente;
        this.equipamento = equipamento;
    }

    public int getIdOs() { return idOs; }
    public void setIdOs(int idOs) { this.idOs = idOs; }
    public Date getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(Date dataAbertura) { this.dataAbertura = dataAbertura; }
    public Date getDataEncerramento() { return dataEncerramento; }
    public void setDataEncerramento(Date dataEncerramento) { this.dataEncerramento = dataEncerramento; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public double getCusto() { return custo; }
    public void setCusto(double custo) { this.custo = custo; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Equipamento getEquipamento() { return equipamento; }
    public void setEquipamento(Equipamento equipamento) { this.equipamento = equipamento; }
}