package br.com.hbsis.produtos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

    @PostMapping
    public ProdutoDTO save(@Valid @RequestBody ProdutoDTO produtoDTO){

        LOGGER.info("Recebendo solicitação de persistência do produto...");
        LOGGER.debug("Payload: {}", produtoDTO);

        return this.produtoService.save(produtoDTO);
    }
    @GetMapping("/{id}")
    public ProdutoDTO find(@PathVariable("id") Long id) {

        LOGGER.info("Recebendo find by ID... id: [{}]", id);
        return this.produtoService.findById(id);
    }
    @GetMapping("/exportar")
    public void exportCSV(HttpServletResponse response) throws Exception {
        LOGGER.info("Exportando arquivo produtos.csv");
        this.produtoService.exportCSV(response);
    }

    @RequestMapping("/listar")
    public List<ProdutoDTO> findProduto(){
        List<ProdutoDTO> produto = produtoService.findAll();

        return produto;
    }


    /*   @PutMapping("/importar-por-fornecedor/{id}")
       public void importFornecedor(@PathVariable("id") Long id, @RequestParam MultipartFile file) throws Exception{
           LOGGER.info("Adicionando Produtos do Fornecedor de ID... [{}]", id);
           produtoService.importFornecedor(id, file);
       }
       @PostMapping ("/importar")
       public void importProduto(@PathVariable("id") Long id,
                                 @RequestParam MultipartFile file) throws Exception {
           produtoService.readAll(file);
       }
   */

   @PutMapping("/fornecedor/{id}")
    public void importFornecedor(@PathVariable("id") Long id, @RequestParam MultipartFile file) throws Exception{

        LOGGER.info("Adicionando Produtos do Fornecedor de ID... [{}]", id);
        produtoService.importFornecedor(id, file);
    }

    @PostMapping ("/importar")
    public void importProduto(@PathVariable("file") @RequestParam MultipartFile file) throws Exception {

        produtoService.readAll(file);
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