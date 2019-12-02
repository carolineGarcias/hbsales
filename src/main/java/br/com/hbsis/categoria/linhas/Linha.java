package br.com.hbsis.categoria.linhas;

import br.com.hbsis.categoria.produtos.Produto;
import javax.persistence.*;

@Entity
@Table(name = "seg_linhas")
public class Linha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_linha")
    private Long idLinha;

    @Column(name = "nome_linha", unique = true, nullable = false)
    private String nomeLinha;

    @ManyToOne
    @JoinColumn(name= "id_linha_produto", referencedColumnName = "id")
    Produto produto;

    public Long getId() {
        return idLinha;
    }

    public Long getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(Long idLinha) {
        this.idLinha = idLinha;
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

    public String toString(){
        return "Linha { " +
                "id= "              + idLinha   +
                ", Nome linha= '"   + nomeLinha + '\'' +
                ", Produto= "       + produto   +
                '}';

    }

}
