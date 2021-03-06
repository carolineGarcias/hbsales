package br.com.hbsis.categoria.produto;

import br.com.hbsis.fornecedor.Fornecedor;
import javax.persistence.*;

@Entity
@Table (name = "seg_categorias")
public class Categoria{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo", unique = true, nullable = false, length = 10)
    private String codCategoria;

    @Column(name = "nome", unique = false, nullable = false, length = 100)
    private String nomeCategoria;

    @ManyToOne
    @JoinColumn(name = "id_categoria_fornecedor", referencedColumnName = "id")
    private Fornecedor fornecedor;

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
                ", codCategoria='"  + codCategoria  + '\'' +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                ", fornecedor="     + fornecedor.toString() +
                '}';
    }
}