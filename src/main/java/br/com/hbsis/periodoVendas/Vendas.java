package br.com.hbsis.periodoVendas;

import br.com.hbsis.fornecedor.Fornecedor;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seg_vendas")
public class Vendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "inicio")
    private LocalDate inicioVendas;

    @Column(name = "fim")
    private LocalDate fimVendas;

    @Column(name= "retirada_pedido")
    private LocalDate retiradaPedido;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_vendas_fornecedor", referencedColumnName = "id")
    private Fornecedor fornecedor;

    public Vendas() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getRetiradaPedido() {
        return retiradaPedido;
    }

    public void setRetiradaPedido(LocalDate retiradaPedido) {
        this.retiradaPedido = retiradaPedido;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInicioVendas() {
        return inicioVendas;
    }

    public void setInicioVendas(LocalDate inicioVendas) {
        this.inicioVendas = inicioVendas;
    }

    public LocalDate getFimVendas() {
        return fimVendas;
    }

    public void setFimVendas(LocalDate fimVendas) {
        this.fimVendas = fimVendas;
    }

    @Override
    public String toString() {
        return "Vendas{" +
                "id=" + id +
                ", Id Fornecedor= " + fornecedor.toString() +
                ", inicio Vendas="  + inicioVendas +
                ", fim Vendas="     + fimVendas    +
                ", retirada Pedido="+ retiradaPedido +
                ", Descrição= "     + descricao    +
                '}';
    }
}