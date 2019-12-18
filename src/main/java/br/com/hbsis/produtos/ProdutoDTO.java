package br.com.hbsis.produtos;

import java.time.LocalDate;

public class ProdutoDTO {
    private Long   idProduto, linhaId;;
    private String nomeProduto, codProduto;
    private LocalDate validadeProd;
    private double pesoProd, unidadeCaixaProd, precoProd;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Long idProduto, Long linhaId,
                      String nomeProduto, String codProduto,
                      LocalDate validadeProd, double pesoProd,
                      double unidadeCaixaProd, double precoProd) {

        this.idProduto = idProduto;
        this.linhaId = linhaId;
        this.nomeProduto = nomeProduto;
        this.codProduto = codProduto;
        this.validadeProd = validadeProd;
        this.pesoProd = pesoProd;
        this.unidadeCaixaProd = unidadeCaixaProd;
        this.precoProd = precoProd;
    }

    public static ProdutoDTO of(Produto produto){
        return new ProdutoDTO(
                produto.getIdProduto(),
                produto.getLinha().getIdLinha(),
                produto.getNomeProduto(),
                produto.getCodProduto(),
                produto.getValidadeProd(),
                produto.getPesoProd(),
                produto.getUnidadeCaixaProd(),
                produto.getPrecoProd()
        );
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

    public LocalDate getValidadeProd() {
        return validadeProd;
    }

    public void setValidadeProd(LocalDate validadeProd) {
        this.validadeProd = validadeProd;
    }

    public double getPesoProd() {
        return pesoProd;
    }

    public void setPesoProd(double pesoProd) {
        this.pesoProd = pesoProd;
    }

    public double getUnidadeCaixaProd() {
        return unidadeCaixaProd;
    }

    public void setUnidadeCaixaProd(double unidadeCaixaProd) {
        this.unidadeCaixaProd = unidadeCaixaProd;
    }

    public double getPrecoProd() {
        return precoProd;
    }

    public void setPrecoProd(double precoProd) {
        this.precoProd = precoProd;
    }

    public Long getLinhaId() {
        return linhaId;
    }

    public void setLinhaId(Long linhaId) {
        this.linhaId = linhaId;
    }

    @Override
    public String toString() {
        return "ProdutoDTO{" +
                "idProduto="          + idProduto    + '\'' +
                ", codProduto="       + codProduto   + '\'' +
                ", nomeProduto='"     + nomeProduto  + '\'' +
                ", validadeProd='"    + validadeProd + '\'' +
                ", pesoProd="         + pesoProd     + '\'' +
                ", unidadeCaixaProd=" + unidadeCaixaProd +'\'' +
                ", precoProd="        + precoProd    + '\'' +
                ", linhaId="          + linhaId      + '\'' +
                '}';
    }
}