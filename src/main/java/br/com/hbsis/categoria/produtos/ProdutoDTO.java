package br.com.hbsis.categoria.produtos;

import br.com.hbsis.fornecedor.Fornecedor;
import org.apache.logging.log4j.message.StringFormattedMessage;

public class ProdutoDTO {

    private Long id;
    private String nomeProduto;
    private String codProduto;
    private Fornecedor fornecedor;

    public ProdutoDTO() {
    }

    public ProdutoDTO(String codProduto, String nomeProduto, Fornecedor fornecedor) {
        this.codProduto  = codProduto;
        this.nomeProduto = nomeProduto;
        this.fornecedor  = fornecedor;
    }

    public ProdutoDTO(Long id, String codProduto, String nomeProduto, Fornecedor fornecedor) {
        this.id = id;
        this.codProduto = codProduto;
        this.nomeProduto = nomeProduto;
        this.fornecedor = fornecedor;
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

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(String codProduto) {
        this.codProduto = codProduto;
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
                "id= " + id +
                ", Nome Produto= '" + nomeProduto + '\'' +
                ", Fornecedor= "    + fornecedor.toString() +
                '}';
    }
}