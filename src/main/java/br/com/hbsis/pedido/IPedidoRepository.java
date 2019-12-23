package br.com.hbsis.pedido;

import br.com.hbsis.funcionarios.Funcionario;
import br.com.hbsis.vendas.Vendas;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IPedidoRepository extends JpaRepository<Pedido, Long> {

    //List<Pedido> PedidoVendas(Vendas vendas);
    List<Pedido> findByFuncionario(Funcionario funcionario);
    boolean existsByCodPedido(String codPedido);
    List<Pedido> findByVendas(Vendas vendas);
}
