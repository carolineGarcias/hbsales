package br.com.hbsis.categoria.linhas;

import com.google.common.net.HttpHeaders;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.PrintWriter;

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
    public LinhaDTO save(@RequestBody LinhaDTO linhaDTO){

        LOGGER.info("Recebendo solicitação de persistência de linha categoria...");
        LOGGER.debug("Payload: {}", linhaDTO);

        return this.linhaService.save(linhaDTO);
    }

    @GetMapping("/exportLinhas")
    public void exportCSV(HttpServletResponse response) throws Exception {

        String filename = "linhas.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; arquivo= \"" + filename + "\"");

        PrintWriter writer = response.getWriter();

        ICSVWriter csvWriter = new CSVWriterBuilder(writer)
                .withSeparator (';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd   (CSVWriter.DEFAULT_LINE_END)
                .build();

        String headerCSV[] = {
                "id_linha",
                "id_linha_produto",
                "nome_linha"};

        csvWriter.writeNext(headerCSV);

        for (Linha file : linhaService.findAll()) {
                csvWriter.writeNext(new
                    String[] {
                            file.getId().toString(),
                            file.getProduto().getId().toString(),
                            file.getNomeLinha()
                }
            );
        }

    }

    @PostMapping("/importLinhas")
    public void importCSV(@RequestParam("file") MultipartFile file) throws Exception {
               linhaService.readAll(file);

    }

    @GetMapping("/{id}")
    public LinhaDTO find(@PathParam("id") Long id){

        LOGGER.info("Recebendo find by ID...[{}]", id);

        return this.linhaService.findById(id);
    }

    @PutMapping("/{id}")
    public LinhaDTO update(@PathVariable("id") Long id, @RequestBody LinhaDTO linhaDTO){
        LOGGER.info("Recebendo update para Linha Categoria de ID: {}", id);
        LOGGER.debug("Payload: {}", linhaDTO);

        return this.linhaService.update(linhaDTO, id);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        LOGGER.info("Recebendo Delete para Linha Categoria de ID: {}", id);

        this.linhaService.delete(id);
    }

}