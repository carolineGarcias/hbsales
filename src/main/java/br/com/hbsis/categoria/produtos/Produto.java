package br.com.hbsis.categoria.produtos;

import br.com.hbsis.fornecedor.Fornecedor;
import javax.persistence.*;

@Entity
@Table(name= "seg_produtos")

public class Produto {

    public Produto(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cod_produto", unique = true, nullable = false)
    private String codProduto;

    @Column(name = "nome_produto", unique = true, nullable = false, length = 100)
    private String nomeProduto;

    @ManyToOne
    @JoinColumn(name = "id_produto_fornecedor", referencedColumnName = "id")
    private Fornecedor fornecedor;

    public Produto(Long id, String codProduto, String nomeProduto, Fornecedor fornecedor) {
        this.id = id;
        this.codProduto = codProduto;
        this.nomeProduto = nomeProduto;
        this.fornecedor= fornecedor;
    }

  /*  public Produto(Long id, String codProduto, String nomeProduto, Fornecedor fornecedor) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.codProduto = codProduto;
        this.fornecedor = fornecedor;
    }
*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", Nome Produto= '" + nomeProduto + '\'' +
                ", Fornecedor= '" + fornecedor.toString() +
                '}';
    }
}