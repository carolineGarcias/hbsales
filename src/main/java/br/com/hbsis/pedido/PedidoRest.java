package br.com.hbsis.pedido;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(name = "/pedidos")
public class PedidoRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidoRest.class);
    private final PedidoService pedidoService;

    @Autowired
    public PedidoRest(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public PedidoDTO save(@RequestBody PedidoDTO pedidoDTO) {
        return this.pedidoService.save(pedidoDTO);
    }

    @GetMapping("/exportar/{id}")
    public void findAll(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        pedidoService.PedidoVendas(response, id);
    }

    @GetMapping("/funcionarios/{id}")
    public void findAllFuncionario(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        pedidoService.PedidoFuncionario(id, response);
    }

    @GetMapping("/retirado/{id}")
    public List<PedidoDTO> findAll1(@PathVariable Long id) {
        return this.pedidoService.findAllByFornecedorId(id);
    }

    @PutMapping("/{id}")
    public PedidoDTO update(@PathVariable("id") Long id, @RequestBody PedidoDTO pedidoDTO) {
        return this.pedidoService.update(pedidoDTO, id);
    }

    @GetMapping("/listar")
    public List<Pedido> listar() {
        LOGGER.info("Gerando lista de Pedidos.");

        return this.pedidoService.listar();
    }

    @GetMapping("/{id}")
    public PedidoDTO findById(@PathVariable("id") Long id) {
        LOGGER.info("Procurando pedido de ID: [{}]", id);

        return this.pedidoService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.pedidoService.delete(id);
    }
}