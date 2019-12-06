package br.com.hbsis.categoria.produto;

import br.com.hbsis.fornecedor.Fornecedor;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;

@Entity
@Table (name = "seg_categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long id;

    @Column(name = "codigo_categoria" , unique = true, nullable = false)
    private String codCategoria;

    @Column(name = "nome_categoria", unique = false, nullable = false, length = 100)
    private String nomeCategoria;

    @ManyToOne
    @JoinColumn(name = "id_categoria_fornecedor", referencedColumnName = "id_fornecedor")
    @JsonDeserialize
    private Fornecedor fornecedor;

    public Categoria() {
    }

    public Categoria(String codCategoria, String nomeCategoria, Fornecedor fornecedor) {
        this.codCategoria = codCategoria;
        this.nomeCategoria = nomeCategoria;
        this.fornecedor= fornecedor;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(String codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", codCategoria="   + this.getCodCategoria()  +
                ", nomeCategoria='" + this.getNomeCategoria() + '\'' +
                ", fornecedor="     + this.getFornecedor().getId()    +
                '}';
    }
}
