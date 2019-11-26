package br.com.hbsis.categoria.produtos;

import br.com.hbsis.fornecedor.Fornecedor;
import com.opencsv.bean.CsvBindByPosition;
import javax.persistence.*;

@Entity
@Table(name= "seg_produtos")

public class Produto {

    public Produto(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByPosition(position = 0)
    private Long id;

    @Column(name = "cod_produto", unique = true, nullable = false)
    @CsvBindByPosition(position = 1)
    private String codProduto;

    @Column(name = "nome_produto", unique = true, nullable = false, length = 100)
    @CsvBindByPosition(position = 2)
    private String nomeProduto;

    @ManyToOne
    @JoinColumn(name = "id_produto_fornecedor", referencedColumnName = "id")
    @CsvBindByPosition(position = 3)
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
        return    this.getId()          + ";"
                + this.getNomeProduto() + ";"
                + this.getCodProduto()  + ";"
                + this.getId()          + ";"
                + this.getFornecedor().getNomeFantasia();
    }
}