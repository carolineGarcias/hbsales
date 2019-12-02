package br.com.hbsis.categoria.produtos;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.categoria.linhas.Linha;
import javax.persistence.*;

@Entity
@Table(name= "seg_produtos")
public class Produto {

    public Produto() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_linha")
    private Long id;

    @Column(name = "cod_produto")
    private Long codProduto;

    @Column(name = "nome_produto")
    private String nomeProduto;

    @Column(name = "preco_produto")
    double precoProd;

    @Column(name = "unidade_caixa_produto")
    double unidadeCaixaProd;

    @Column(name = "peso_produto")
    double pesoProd;

    @Column(name = "validade_produto")
    String validadeProd;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor_produto", referencedColumnName = "id")
    private Fornecedor fornecedor;

    private Linha linha;

    public Linha getLinha() {
        return linha;
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

    public double getPrecoProd() {
        return precoProd;
    }

    public void setPrecoProd(double precoProd) {
        this.precoProd = precoProd;
    }

    public double getUnidadeCaixaProd() {
        return unidadeCaixaProd;
    }

    public void setUnidadeCaixaProd(double unidadeCaixaProd) {
        this.unidadeCaixaProd = unidadeCaixaProd;
    }

    public double getPesoProd() {
        return pesoProd;
    }

    public void setPesoProd(double pesoProd) {
        this.pesoProd = pesoProd;
    }

    public String getValidadeProd() {
        return validadeProd;
    }

    public void setValidadeProd(String validadeProd) {
        this.validadeProd = validadeProd;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public void setLinha(Linha linha) {
        this.linha = linha;
    }

    @Override
    public String toString() {
        return "Produto { " +
                "id= " + id +
                ", Código produto= '"  + codProduto       + '\'' +
                ", Nome Produto= '"    + nomeProduto      + '\'' +
                ", Preço= "            + precoProd        + '\'' +
                ", Unidade p/ Caixa= " + unidadeCaixaProd + '\'' +
                ", Peso p/ unidade= "  + pesoProd         + '\'' +
                ", Validade Produto= " + validadeProd     + '\'' +
                ", Fornecedor= "       + fornecedor       + '\'' +
                '}';
    }

}