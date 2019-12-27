package br.com.hbsis.pedido;

import br.com.hbsis.funcionarios.Funcionario;
import br.com.hbsis.produtos.Produto;
import br.com.hbsis.periodoVendas.Vendas;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seg_pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idPedido;

    @Column(name = "codigo")
    private String codPedido;

    @Column(name = "status")
    private String status;

    @Column(name = "data")
    private LocalDate dataPedido;

    @Column(name = "quantidade_pedido")
    private int quantidadePedido;

    @Column(name = "uuid")
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "id_pedido_funcionario", referencedColumnName = "id")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "id_pedido_produto", referencedColumnName = "id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "id_pedido_vendas", referencedColumnName = "id")
    private Vendas vendas;

    public Pedido() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
                ", dataPedido=" + dataPedido +
                ", quantidadePedido=" + quantidadePedido +
                ", uuid='" + uuid + '\'' +
                ", funcionario=" + funcionario +
                ", produto=" + produto +
                ", vendas=" + vendas +
                '}';
    }
}