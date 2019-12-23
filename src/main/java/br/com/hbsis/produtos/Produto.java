package br.com.hbsis.produtos;

import br.com.hbsis.linhas.Linha;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name= "seg_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Long idProduto;

    @Column(name = "cod_produto")
    private String codProduto;

    @Column(name = "nome_produto")
    private String nomeProduto;

    @Column(name = "preco_produto")
    private double precoProd;

    @Column(name = "unidade_produto")
    private double unidadeCaixaProd;

    @Column(name = "peso_produto")
    private double pesoProd;

    @Column(name = "validade_produto")
    private LocalDate validadeProd;

    @ManyToOne
    @JoinColumn(name = "id_produto_linha", referencedColumnName = "id_linha")
    private Linha linha;

    public Produto() {
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(String codProduto) {
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

    public LocalDate getValidadeProd() {
        return validadeProd;
    }

    public void setValidadeProd(LocalDate validadeProd) {
        this.validadeProd = validadeProd;
    }

    public Linha getLinha() {
        return linha;
    }

    public void setLinha(Linha linha) {
        this.linha = linha;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "idProduto="          + idProduto        + '\'' +
                ", codProduto="       + codProduto       + '\'' +
                ", nomeProduto='"     + nomeProduto      + '\'' +
                ", precoProd="        + precoProd        + '\'' +
                ", unidadeCaixaProd=" + unidadeCaixaProd + '\'' +
                ", pesoProd="         + pesoProd         + '\'' +
                ", validadeProd='"    + validadeProd     + '\'' +
                ", linha="            + linha.toString() +
                '}';
    }
}