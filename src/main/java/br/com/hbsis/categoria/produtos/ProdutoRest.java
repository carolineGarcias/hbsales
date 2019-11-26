package br.com.hbsis.categoria.produtos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoRest{

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoRest.class);

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoRest(ProdutoService produtoService){
        this.produtoService = produtoService;
    }

    @RequestMapping("/listar")
    public List<Produto> findProdutos() {

        List<Produto> produtos = produtoService.findAll();
        return produtos;
    }

    @PostMapping
    public ProdutoDTO save(@RequestBody ProdutoDTO produtoDTO){

        LOGGER.info("Recebendo solicitação de persistência do produto...");
        LOGGER.debug("Payload: {}", produtoDTO);

        return this.produtoService.save(produtoDTO);
    }

    @GetMapping("/{id}")
    public ProdutoDTO find(@PathVariable("id") Long id) {

        LOGGER.info("Recebendo find by ID... id: [{}]", id);

        return this.produtoService.findById(id);
    }

    @PutMapping("/{id}")
    public ProdutoDTO update(@PathVariable("id") Long id, @RequestBody ProdutoDTO produtoDTO){
        LOGGER.info("Recebendo Update para Produto de ID: {}", id);
        LOGGER.debug("Payload: {}", produtoDTO);

        return this.produtoService.update(produtoDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para Produto de ID: {}", id);

        this.produtoService.delete(id);
    }
}