package br.com.hbsis.categoria.produtos;

import br.com.hbsis.fornecedor.Fornecedor;

public class ProdutoDTO {

    private Fornecedor fornecedor;
    private Long   id, codProduto;
    private String nomeProduto;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Long codProduto, String nomeProduto, Fornecedor fornecedor) {
        this.codProduto  = codProduto;
        this.nomeProduto = nomeProduto;
        this.fornecedor  = fornecedor;
    }

    public ProdutoDTO(Long id, Long codProduto, String nomeProduto, Fornecedor fornecedor) {
        this.id = id;
        this.codProduto  = codProduto;
        this.nomeProduto = nomeProduto;
        this.fornecedor  = fornecedor;
    }

    public static ProdutoDTO of(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getCodProduto(),
                produto.getNomeProduto(),
                produto.getFornecedor()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(Long codProduto) {
        this.codProduto = codProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "Produto { " +
                "  id= " + id +
                ", Código produto= '" + codProduto  + '\'' +
                ", Nome Produto= '"   + nomeProduto + '\'' +
                ", Fornecedor= "      + fornecedor.getId() +
                '}';
    }
}