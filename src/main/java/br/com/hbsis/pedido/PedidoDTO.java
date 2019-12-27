package br.com.hbsis.pedido;

import java.time.LocalDate;

public class PedidoDTO {

    private Long idPedido, funcionarioId, vendasId, produtoId;
    private String status, codPedido, uuid;
    private LocalDate dataPedido;
    private int quantidadePedido;


    public PedidoDTO(Long idPedido, Long funcionarioId, Long vendasId,
                     Long produtoId, String status, String codPedido, String uuid,
                     LocalDate dataPedido, int quantidadePedido) {

        this.idPedido = idPedido;
        this.funcionarioId = funcionarioId;
        this.vendasId = vendasId;
        this.produtoId = produtoId;
        this.status = status;
        this.codPedido = codPedido;
        this.uuid = uuid;
        this.dataPedido = dataPedido;
        this.quantidadePedido = quantidadePedido;
    }

    public static PedidoDTO of(Pedido pedido) {
        return new PedidoDTO(
                pedido.getIdPedido(),
                pedido.getFuncionario().getId(),
                pedido.getVendas().getId(),
                pedido.getProduto().getIdProduto(),
                pedido.getStatus(),
                pedido.getCodPedido(),
                pedido.getUuid(),
                pedido.getDataPedido(),
                pedido.getQuantidadePedido()
        );
    }

    public PedidoDTO() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(String codPedido) {
        this.codPedido = codPedido;
    }

    public Long getVendasId() {
        return vendasId;
    }

    public void setVendasId(Long vendasId) {
        this.vendasId = vendasId;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public int getQuantidadePedido() {
        return quantidadePedido;
    }

    public void setQuantidadePedido(int quantidadePedido) {
        this.quantidadePedido = quantidadePedido;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "idPedido=" + idPedido +
                ", funcionarioId=" + funcionarioId +
                ", vendasId=" + vendasId +
                ", produtoId=" + produtoId +
                ", status='" + status + '\'' +
                ", codPedido='" + codPedido + '\'' +
                ", uuid='" + uuid + '\'' +
                ", dataPedido=" + dataPedido +
                ", quantidadePedido=" + quantidadePedido +
                '}';
    }
}