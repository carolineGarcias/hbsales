package br.com.hbsis.categoria.produto;


import br.com.hbsis.categoria.produto.CategoriaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaRest.class);

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaRest(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaDTO categoriaDTO){

        LOGGER.info("Recebendo solicitação de persistência de categoria produto...");
        LOGGER.debug("Payload: {}", categoriaDTO);

        return this.categoriaService.save(categoriaDTO);
    }

    @RequestMapping("/listar")
    public List<Categoria> findFornecedor() {

        List<Categoria> categoria = categoriaService.findAll();
        return categoria;
    }

    @GetMapping("/export-csv-categorias")
    public void exportCSV(HttpServletResponse response) throws Exception {

        LOGGER.info("Exportando arquivo 'categorias.csv'");

        this.categoriaService.exportCSV(response);
    }

    @PostMapping("/import-csv-categorias")
    public void importCSV(@RequestParam("file") MultipartFile file) throws Exception {

        categoriaService.readAll(file);
    }

    @GetMapping("/{id}")
    public CategoriaDTO find(@PathVariable("id") Long id) {

        LOGGER.info("Recebendo find by ID... id: [{}]", id);

        return this.categoriaService.findById(id);
    }

    @PutMapping("/{id}")
    public CategoriaDTO update(@PathVariable("id") Long id, @RequestBody CategoriaDTO categoriaDTO) {
        LOGGER.info("Recebendo Update para Categoria Produto de ID: {}", id);
        LOGGER.debug("Payload: {}", categoriaDTO);

        return this.categoriaService.update(categoriaDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para Categoria Produtos de ID: {}", id);

        this.categoriaService.delete(id);
    }

}
