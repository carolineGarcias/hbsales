package br.com.hbsis.vendas;

import br.com.hbsis.categoria.produto.Categoria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendasRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(VendasRest.class);
    private final VendasService vendasService;

    @Autowired
    public VendasRest(VendasService vendasService) {
        this.vendasService = vendasService;
    }

    @PostMapping
    public VendasDTO save(@RequestBody VendasDTO vendasDTO) {

        LOGGER.info("Recebendo solicitação para salvar periodo de vendas...");
        return vendasService.save(vendasDTO);
    }

    @PutMapping("/{id}")
    public VendasDTO update(@PathVariable("id") Long id, @RequestBody VendasDTO vendasDTO) {
        return this.vendasService.update(vendasDTO, id);
    }

    @GetMapping("/listar")
    public List<Vendas> listar() {
        LOGGER.info("Gerando lista de Vendas.");

        return this.vendasService.listar();
    }

    @GetMapping("/{id}")
    public VendasDTO findById(@PathVariable("id") Long id) {
        LOGGER.info("Procurando periodo de vendas de ID: [{}]", id);

        return this.vendasService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.vendasService.delete(id);
    }
}