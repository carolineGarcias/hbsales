package br.com.hbsis.categoria.produto;

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
    public CategoriaRest(CategoriaService categoriaService){
        this.categoriaService= categoriaService;
    }

    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaDTO categoriaDTO){
        LOGGER.info("Recebendo solicitação de persistência de categoria...");
        LOGGER.debug("Payload: {}", categoriaDTO);

       return this.categoriaService.save(categoriaDTO);
    }

       @GetMapping("/{id}")
    public CategoriaDTO find(@PathVariable("id") Long id) {

        LOGGER.info("Recebendo find by ID... id: [{}]", id);

        return this.categoriaService.findById(id);
    }

    @PutMapping("/{id}")
    public CategoriaDTO update(@PathVariable("id") Long id, @RequestBody CategoriaDTO categoriaDTO) {
        LOGGER.info("Recebendo Update para Categoria de ID: {}", id);
        LOGGER.debug("Payload: {}", categoriaDTO);

        return this.categoriaService.update(categoriaDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para Categoria Produtos de ID: {}", id);

        this.categoriaService.delete(id);
    }

    @GetMapping("/exportar")
    public void exportarCSV(HttpServletResponse httpResponse){
        LOGGER.info("Exportando arquivo categorias.csv'");

        this.categoriaService.exportCSV(httpResponse);
    }

    @PostMapping("/importar")
    public void importarCSV(@RequestParam("file") MultipartFile file) throws Exception {
        LOGGER.info("Importando arquivo categorias.csv'");

        categoriaService.readAll(file);
    }

    @GetMapping("/listar")
    public List<Categoria> listar(){
        LOGGER.info("Gerando lista de Categorias.");

        return this.categoriaService.listar();
    }
}