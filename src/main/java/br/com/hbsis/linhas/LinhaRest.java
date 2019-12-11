package br.com.hbsis.linhas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/linhas")
public class LinhaRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaRest.class);
    private final LinhaService linhaService;

    @Autowired
    public LinhaRest(LinhaService linhaCategoriaService) {
        this.linhaService = linhaCategoriaService;
    }

    @PostMapping
    public LinhaDTO save(@RequestBody LinhaDTO linhaDTO) {

        LOGGER.info("Recebendo solicitação de persistência de linha categoria...");
        LOGGER.debug("Payload: {}", linhaDTO);

        return this.linhaService.save(linhaDTO);
    }

    @RequestMapping("/listar")
    public List<Linha> findAll() {

        List<Linha> linhas = linhaService.findAll();
        return linhas;
    }

    @GetMapping("/exportar")
    public void exportCSV(HttpServletResponse httpResponse) throws Exception {

        LOGGER.info("Exportando arquivo response.csv");

        this.linhaService.exportCSV(httpResponse);

    }

    @PostMapping("/importar")
    public void importCSV(@RequestParam("file") MultipartFile file) throws Exception {

        linhaService.readAll(file);
    }

    @GetMapping("/{id}")
    public LinhaDTO find(@PathParam("id") Long id) {

        LOGGER.info("Recebendo find by ID...[{}]", id);

        return this.linhaService.findById(id);
    }

    @PutMapping("/{id}")
    public LinhaDTO update(@PathVariable("id") Long id, @RequestBody LinhaDTO linhaDTO) {
        LOGGER.info("Recebendo update para Linha Categoria de ID: {}", id);
        LOGGER.debug("Payload: {}", linhaDTO);

        return this.linhaService.update(linhaDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para Linha Categoria de ID: {}", id);

        this.linhaService.delete(id);
    }
}