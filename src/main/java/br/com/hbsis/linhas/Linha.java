package br.com.hbsis.linhas;

import br.com.hbsis.categoria.produto.Categoria;

import javax.persistence.*;

@Entity
@Table(name = "seg_linhas")
public class Linha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_linha")
    private Long idLinha;

    @Column(name = "nome_linha", unique = true, nullable = false)
    private String nomeLinha;

    @Column(name = "codigo_linha", nullable = false, length = 10)
    private String codLinha;

    @ManyToOne
    @JoinColumn(name= "id_linha_categoria", referencedColumnName = "id_categoria")
    private Categoria categoria;

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

    public String getCodLinha() {
        return codLinha;
    }

    public void setCodLinha(String codLinha) {
        this.codLinha = codLinha;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String toString(){
        return "Linha { " +
                "id= "              + idLinha   +
                ", Nome linha= '"   + nomeLinha + '\'' +
                ", ID Categoria= "  + categoria.toString() + '\'' +
                ", Codigo Linha= "  +
                '}';
    }

}


