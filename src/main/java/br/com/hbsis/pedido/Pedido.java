package br.com.hbsis.pedido;

import br.com.hbsis.funcionarios.Funcionario;
import br.com.hbsis.produtos.Produto;
import br.com.hbsis.vendas.Vendas;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seg_pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "codigo_pedido")
    private String codPedido;

    @Column(name = "status_pedido")
    private String status;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    @Column(name = "quantidade_pedido")
    private int quantidadePedido;

    @ManyToOne
    @JoinColumn(name = "id_pedido_funcionario", referencedColumnName = "id_funcionarios")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "id_pedido_produto", referencedColumnName = "id_produto")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "id_pedido_vendas", referencedColumnName = "id_vendas")
    private Vendas vendas;

    public Pedido() {
    }

    public int getQuantidadePedido() {
        return quantidadePedido;
    }

    public void setQuantidadePedido(int quantidadePedido) {
        this.quantidadePedido = quantidadePedido;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(String codPedido) {
        this.codPedido = codPedido;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", codPedido='" + codPedido + '\'' +
                ", status='" + status + '\'' +
                ", funcionario=" + funcionario.toString() +
                ", produto=" + produto.toString() +
                ", vendas=" + vendas.toString() +
                ", dataPedido=" + dataPedido +
                ", quantidadePedido=" + quantidadePedido +
                '}';
    }
}