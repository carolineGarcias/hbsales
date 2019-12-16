package br.com.hbsis.vendas;

import java.time.LocalDate;

public class VendasDTO {

    private Long id, fornecedorId;
    private LocalDate inicioVendas, fimVendas, retiradaPedido;
    private String descricao;

    public VendasDTO(Long id, Long fornecedorId, LocalDate inicioVendas, LocalDate fimVendas, LocalDate retiradaPedido, String descricao) {
        this.id = id;
        this.inicioVendas = inicioVendas;
        this.fimVendas = fimVendas;
        this.fornecedorId = fornecedorId;
        this.retiradaPedido = retiradaPedido;
        this.descricao = descricao;
    }

     public static VendasDTO of(Vendas vendas){
            return new VendasDTO(
                    vendas.getId(),
                    vendas.getFornecedor().getIdFornecedor(),
                    vendas.getInicioVendas(),
                    vendas.getFimVendas(),
                    vendas.getRetiradaPedido(),
                    vendas.getDescricao()
            );
     }

    public VendasDTO() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Long fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public LocalDate getRetiradaPedido() {
        return retiradaPedido;
    }

    public void setRetiradaPedido(LocalDate retiradaPedido) {
        this.retiradaPedido = retiradaPedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInicioVendas() {
        return inicioVendas;
    }

    public void setInicioVendas(LocalDate inicioVendas) {
        this.inicioVendas = inicioVendas;
    }

    public LocalDate getFimVendas() {
        return fimVendas;
    }

    public void setFimVendas(LocalDate fimVendas) {
        this.fimVendas = fimVendas;
    }

    @Override
    public String toString() {
        return "VendasDTO{" +
                "id=" + id +
                ", Id Fornecedor= " + fornecedorId +
                ", inicio Vendas= "  + inicioVendas +
                ", fim Vendas= "     + fimVendas    +
                ", Descrção= "      + descricao    +
                '}';
    }
}