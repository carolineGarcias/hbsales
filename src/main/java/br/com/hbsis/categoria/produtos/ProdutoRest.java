package br.com.hbsis.categoria.produtos;

import com.opencsv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
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
    @GetMapping("/exportar")
    public void exportarCSV(HttpServletResponse response) throws Exception {

        String filename = "produtos.csv";

                    response.setContentType("text/csv");
                    response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                                      "attachment; filename=\""
                                       + filename + "\"");

                    PrintWriter writer1 = response.getWriter();

                    ICSVWriter icsvWriter = new CSVWriterBuilder(writer1).
                            withSeparator(';').
                            withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).
                            withLineEnd(CSVWriter.DEFAULT_LINE_END).
                            build();

                    String readerCSV[] = {
                            "id_produto",
                            "cod_produto",
                            "nome_produto",
                            "id_fornecedor"
        };

        icsvWriter.writeNext(readerCSV);
        for (Produto linha : produtoService.findAll()) {
            icsvWriter.writeNext(
                            new String[]{
                            linha.getId().toString(),
                            linha.getCodProduto().toString(),
                            linha.getNomeProduto(),
                            linha.getFornecedor().getId().toString()});
        }

    }

    @RequestMapping("/listar")
    public List<Produto> findProduto(){
        List<Produto> produto = produtoService.findAll();

        return produto;
    }

    @PostMapping ("/importar")
    public void importCSV(@RequestParam("file") MultipartFile file) throws Exception {

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