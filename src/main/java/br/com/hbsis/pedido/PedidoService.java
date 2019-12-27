package br.com.hbsis.pedido;

import br.com.hbsis.funcionarios.FuncionarioService;
import br.com.hbsis.produtos.Produto;
import br.com.hbsis.produtos.ProdutoService;
import br.com.hbsis.periodoVendas.Vendas;
import br.com.hbsis.periodoVendas.VendasService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class PedidoService {

    private final Logger LOGGER = LoggerFactory.getLogger(br.com.hbsis.pedido.PedidoService.class);

    private final IPedidoRepository iPedidoRepository;
    private final FuncionarioService funcionarioService;
    private final ProdutoService produtoService;
    private final VendasService vendasService;

    public PedidoService(IPedidoRepository iPedidoRepository, FuncionarioService funcionarioService,
                         ProdutoService produtoService, VendasService vendasService) {

        this.iPedidoRepository = iPedidoRepository;
        this.funcionarioService = funcionarioService;
        this.produtoService = produtoService;
        this.vendasService = vendasService;
    }

    public PedidoDTO save(PedidoDTO pedidoDTO) {
        this.validate(pedidoDTO);

        LOGGER.info("Salvando Pedidos...");

        Pedido pedido = new Pedido();

        pedido.setIdPedido(pedidoDTO.getIdPedido());
        pedido.setCodPedido(pedidoDTO.getCodPedido());
        pedido.setUuid(UUID.randomUUID().toString());
        pedido.setStatus(pedidoDTO.getStatus());
        pedido.setDataPedido(pedidoDTO.getDataPedido());
        pedido.setQuantidadePedido(pedidoDTO.getQuantidadePedido());
        pedido.setFuncionario(funcionarioService.findByFuncionarioId(pedidoDTO.getFuncionarioId()));
        pedido.setVendas(vendasService.findByVendasId(pedidoDTO.getVendasId()));
        pedido.setProduto(produtoService.findByProdutoId(pedidoDTO.getProdutoId()));

        pedido = this.iPedidoRepository.save(pedido);

        return pedidoDTO.of(pedido);
    }

    public String runCode() {
        List codigo = new ArrayList();
        for (int i = 1; i < 100; i++) {
            codigo.add(i);
        }
        Collections.shuffle(codigo);
        String codigoCompleto = StringUtils.leftPad(String.valueOf(codigo.get(0)), 10, "0");
        return codigoCompleto;
    }

    public void validate(PedidoDTO pedidoDTO) {
        LOGGER.info("Validando Pedido");

        pedidoDTO.setCodPedido(runCode());
        pedidoDTO.setDataPedido(LocalDate.now());
        Produto produto;
        Vendas vendas;

        while (iPedidoRepository.existsByCodPedido(pedidoDTO.getCodPedido())) {
            LOGGER.info("O Código informado já existe, vamos gerar um novo código!");
            pedidoDTO.setCodPedido(runCode());
            if (pedidoDTO == null) {
                throw new IllegalArgumentException("Pedido não pode ser nulo.");
            }
            if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getStatus()))) {
                throw new IllegalArgumentException("Status não pode ser nulo");
            }
            if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getFuncionarioId()))) {
                throw new IllegalArgumentException("Id Funcionario não pode ser nulo");
            }

            produto = produtoService.findByProdutoId(pedidoDTO.getProdutoId());
            vendas = vendasService.findByVendasId(pedidoDTO.getVendasId());

            if (produto.getLinha().getCategoria().getFornecedor().getIdFornecedor()
                    != vendas.getFornecedor().getIdFornecedor()) {
                throw new IllegalArgumentException("O produto não é o mesmo da venda do fornecedor!");
            }
            if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getVendasId()))) {
                throw new IllegalArgumentException("Vendas não deve ser nulo");
            }
            if (iPedidoRepository.existsByCodPedido(pedidoDTO.getCodPedido())) {
                throw new IllegalArgumentException("O código já existe");
            }

            if (StringUtils.isEmpty(String.valueOf(pedidoDTO.getQuantidadePedido()))) {
                throw new IllegalArgumentException("A quantidade de pedido não deve ser nula");
            }

            switch (pedidoDTO.getStatus().toUpperCase()) {
                case "ATIVO":      // Pedido foi criado, mas ainda não foi retirado
                case "CANCELADO":  // Quando o pedido não será mais retirado
                case "RETIRADO":   // Quando o pedido já foi criado
                    break;
                default:
                    throw new IllegalArgumentException("Status válidos: Ativo/Cancelado/Retirado");
            }
        }
    }

    public PedidoDTO existsByCodPedido(String codPedido) {
        return existsByCodPedido(codPedido);

    }

    public PedidoDTO update(PedidoDTO pedidoDTO, Long id) {
        Optional<Pedido> pedidoOptional = this.iPedidoRepository.findById(id);

        if (pedidoOptional.isPresent()) {
            Pedido pedidoExistente = pedidoOptional.get();

            LOGGER.info("Atualizando Pedido do Fornecedor...[{}]", pedidoOptional.get().getFuncionario().getId());
            LOGGER.debug("Payload... [{}]", pedidoDTO);
            LOGGER.debug("Pedido existente... [{}]", pedidoExistente);

            pedidoExistente.setIdPedido(pedidoDTO.getIdPedido());
            pedidoExistente.setFuncionario(funcionarioService.findByFuncionarioId(pedidoOptional.get().getFuncionario().getId()));
            pedidoExistente.setStatus(pedidoDTO.getStatus());

            this.iPedidoRepository.save(pedidoExistente);
            return pedidoDTO.of(pedidoExistente);
        }

        throw new IllegalArgumentException(String.format("Não foi possível atualizar o pedido do fornecedor com o id...", id));
    }

    public List<Pedido> listar() {

        LOGGER.info("Listando Pedidos.");
        List<Pedido> pedidos;
        pedidos = this.iPedidoRepository.findAll();
        return pedidos;
    }

    public PedidoDTO findById(Long id) {
        Optional<Pedido> pedidoOptional = this.iPedidoRepository.findById(id);

        if (pedidoOptional.isPresent()) {
            return PedidoDTO.of(pedidoOptional.get());
        }
        throw new IllegalArgumentException(String.format("Pedido de ID {} não encontrado.", id));
    }

    public void delete(Long id) {

        LOGGER.info("Excluindo Pedido de ID: [{}]", id);
        this.iPedidoRepository.deleteById(id);
    }
}