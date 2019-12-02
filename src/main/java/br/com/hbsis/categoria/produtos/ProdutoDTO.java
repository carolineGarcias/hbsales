package br.com.hbsis.categoria.produtos;


public class ProdutoDTO {

    private Long   id, codProduto, linhaId;
    private String nomeProduto, validadeProd;
    private double pesoProd, unidadeCaixaProd, precoProd;

    public ProdutoDTO(Long id, Long codProduto, Long linhaId,
                      String nomeProduto, String validadeProd,
                      double pesoProd, double unidadeCaixaProd, double precoProd){

        this.id            = id;
        this.codProduto    = codProduto;
        this.linhaId       = linhaId;
        this.nomeProduto   = nomeProduto;
        this.validadeProd  = validadeProd;
        this.pesoProd      = pesoProd;
        this.unidadeCaixaProd = unidadeCaixaProd;
        this.precoProd     = precoProd;

    }

    public static ProdutoDTO of (Produto produto){
        return new ProdutoDTO(
                produto.getId(),
                produto.getCodProduto(),
                produto.getLinha().getIdLinha(),
                produto.getNomeProduto(),
                produto.getValidadeProd(),
                produto.getPesoProd(),
                produto.getUnidadeCaixaProd(),
                produto.getPrecoProd()
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

    public Long getLinhaId() {
        return linhaId;
    }

    public void setLinhaId(Long linhaId) {
        this.linhaId = linhaId;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getValidadeProd() {
        return validadeProd;
    }

    public void setValidadeProd(String validadeProd) {
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

    @Override
    public String toString() {
        return "Produto { " +
                "id= " + id +
                ", Código produto= '"  + codProduto       + '\'' +
                ", Nome Produto= '"    + nomeProduto      + '\'' +
                ", Linha= "            + linhaId            + '\'' +
                ", Preço= "            + precoProd        + '\'' +
                ", Unidade p/ Caixa= " + unidadeCaixaProd + '\'' +
                ", Peso p/ unidade= "  + pesoProd         + '\'' +
                ", Validade Produto= " + validadeProd     + '\'' +
                '}';
    }
}