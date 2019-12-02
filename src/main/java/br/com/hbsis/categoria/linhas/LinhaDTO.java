package br.com.hbsis.categoria.linhas;

import br.com.hbsis.categoria.produtos.Produto;

public class LinhaDTO {

    private Long    idLinha;
    private String  nomeLinha;
    private Produto produto;

    public LinhaDTO(){
    }

    public LinhaDTO(Long idLinha, String nomeLinha, Produto produto){
        this.idLinha   = idLinha;
        this.nomeLinha = nomeLinha;
        this.produto   = produto;
    }

    public LinhaDTO( String nomeLinha, Produto produto){
        this.nomeLinha = nomeLinha;
        this.produto   = produto;
    }

    public static  LinhaDTO of(Linha linha) {
        return new LinhaDTO(
                linha.getId(),
                linha.getNomeLinha(),
                linha.getProduto()

        );
    }

    public Long getId() {
        return idLinha;
    }

    public String getNomeLinha() {
        return nomeLinha;
    }

    public void setNomeLinha(String nomeLinha) {
        this.nomeLinha = nomeLinha;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "Linha { " +
                "  id= "            + idLinha   +
                ", Nome linha= '"   + nomeLinha + '\'' +
                ", Produto= "       + produto   +
                '}';
    }
}
