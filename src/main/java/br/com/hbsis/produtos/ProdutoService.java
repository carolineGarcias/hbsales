package br.com.hbsis.produtos;

import br.com.hbsis.linhas.LinhaService;
import com.google.common.net.HttpHeaders;
import com.opencsv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);

    private final IProdutoRepository iProdutoRepository;
    private final LinhaService linhaService;

    public ProdutoService(IProdutoRepository iProdutoRepository,
                          LinhaService linhaService) {

        this.iProdutoRepository = iProdutoRepository;
        this.linhaService = linhaService;
    }

    public List<ProdutoDTO> findAll() {
        List<Produto> produtos = iProdutoRepository.findAll();
        List<ProdutoDTO> produtoDTO = new ArrayList<>();
        produtos.forEach(produto -> produtoDTO.add(ProdutoDTO.of(produto)));
        return produtoDTO;
    }

    public List<Produto> readAll(MultipartFile file) throws Exception {
        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();

        List<String[]> lineString = csvReader.readAll();
        List<Produto> reading = new ArrayList<>();

        for (String[] linhas : lineString) {
            try {

                String[] bean = linhas[0].replaceAll("\"", "").split(";");

                Produto produto = new Produto();

                produto.setNomeProduto(bean[1]);
                produto.setCodProduto(bean[2]);
                produto.setPesoProd(Double.parseDouble(bean[3]));
                this.linhaService.findById(Long.parseLong(bean[4])).getIdLinha();
                produto.setPrecoProd(Double.parseDouble(bean[5]));
                produto.setUnidadeCaixaProd(Double.parseDouble(bean[6]));
                produto.setValidadeProd(LocalDate.parse(bean[7]));

                reading.add(produto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return iProdutoRepository.saveAll(reading);
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {

        LOGGER.debug("Produto: {}", produtoDTO);

        this.validate(produtoDTO);

        LOGGER.info("Salvando Produto");
        LOGGER.debug("Produto: {}", produtoDTO);

        Produto produto = new Produto();

        String codigo = String.format("%1$10s", produtoDTO.getCodProduto().toUpperCase());
        codigo = codigo.replaceAll(" ", "0").toUpperCase();

        produto.setIdProduto(produtoDTO.getIdProduto());
        produto.setCodProduto(codigo);
        produto.setNomeProduto(produtoDTO.getNomeProduto().toUpperCase());
        produto.setPrecoProd(produtoDTO.getPrecoProd());
        produto.setLinha(linhaService.findByIdLinha(produtoDTO.getLinhaId()));
        produto.setUnidadeCaixaProd(produtoDTO.getUnidadeCaixaProd());
        produto.setPesoProd(produtoDTO.getPesoProd());
        produto.setValidadeProd(produtoDTO.getValidadeProd());

        produto = this.iProdutoRepository.save(produto);

        return ProdutoDTO.of(produto);
    }

    public ProdutoDTO findById(Long id) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            return ProdutoDTO.of(produtoOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    private void validate(ProdutoDTO produtoDTO) {

        LOGGER.info("Validando Produto");

        if (produtoDTO == null) {
            throw new IllegalArgumentException(String.format("Produto não deve ser nulo"));
        }
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para produto de ID> [{}]", id);

        this.iProdutoRepository.deleteById(id);
    }

    public ProdutoDTO update(ProdutoDTO produtoDTO, Long id) {

        Optional<Produto> produtoExistenteOptional = this.iProdutoRepository.findById(id);
        if (produtoExistenteOptional.isPresent()) {
            Produto produtoExistente = produtoExistenteOptional.get();

            LOGGER.info("Atualizando produto... id: [{}]", produtoExistente.getIdProduto());
            LOGGER.debug("Payload: {}", produtoDTO);
            LOGGER.debug("Fornecedor Existente: {}", produtoExistente);

            produtoExistente.setIdProduto(produtoDTO.getIdProduto());
            produtoExistente.setCodProduto(produtoDTO.getCodProduto().toUpperCase());
            produtoExistente.setNomeProduto(produtoDTO.getNomeProduto().toUpperCase());
            this.linhaService.findById(produtoDTO.getLinhaId()).getIdLinha();
            produtoExistente.setPrecoProd(produtoDTO.getPrecoProd());
            produtoExistente.setUnidadeCaixaProd(produtoDTO.getUnidadeCaixaProd());
            produtoExistente.setPesoProd(produtoDTO.getPesoProd());
            produtoExistente.setValidadeProd(produtoDTO.getValidadeProd());

            produtoExistente = this.iProdutoRepository.save(produtoExistente);

            return produtoDTO.of(produtoExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void exportCSV(HttpServletResponse httpResponse) {
        try {
            String filename = "produtos.csv";

            httpResponse.setContentType("text/csv");
            httpResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + filename + "\"");

            PrintWriter writer = httpResponse.getWriter();
            ICSVWriter csvWriter = new CSVWriterBuilder(writer).
                    withSeparator(';').
                    withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).
                    withLineEnd(CSVWriter.DEFAULT_LINE_END).
                    build();

            String readerCSV[] = {
                    "ID PRODUTO",
                    "CÓDIGO PRODUTO",
                    "NOME PRODUTO",
                    "PREÇO PRODUTO",
                    "UNIDADE CAIXA",
                    "PESO UNIDADE",
                    "VALIDADE PRODUTO",
                    "ID LINHA",
                    "NOME LINHA",
                    "CÓDIGO CATEGORIA",
                    "NOME CATEGORIA",
                    "CNPJ FORNECEDOR",
                    "RAZAO SOCIAL FORNECEDOR"
            };

            csvWriter.writeNext(readerCSV);
            for (Produto produto : iProdutoRepository.findAll()) {

                csvWriter.writeNext(new String[]{
                        produto.getIdProduto().toString(),
                        produto.getCodProduto().toUpperCase(),
                        produto.getNomeProduto().toUpperCase(),
                        "R$ " + produto.getPrecoProd(),
                        produto.getUnidadeCaixaProd() + " UN.",
                        String.valueOf(produto.getPesoProd()),
                        produto.getValidadeProd().toString(),
                        produto.getLinha().getIdLinha().toString(),
                        produto.getLinha().getNomeLinha().toUpperCase(),
                        produto.getLinha().getCategoria().getCodCategoria().toUpperCase(),
                        produto.getLinha().getCategoria().getNomeCategoria().toUpperCase(),
                        produto.getLinha().getCategoria().getFornecedor().getCnpj(),
                        produto.getLinha().getCategoria().getFornecedor().getRazaoSocial().toUpperCase()

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Produto findByProdutoId(Long id) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            return produtoOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }
}