package br.com.hbsis.produtos;

import br.com.hbsis.categoria.produto.Categoria;
import br.com.hbsis.categoria.produto.CategoriaDTO;
import br.com.hbsis.categoria.produto.CategoriaService;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.fornecedor.IFornecedorRepository;
import br.com.hbsis.linhas.ILinhaRepository;
import br.com.hbsis.linhas.Linha;
import br.com.hbsis.linhas.LinhaDTO;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProdutoService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);

    private final IProdutoRepository iProdutoRepository;
    private final ILinhaRepository   iLinhaRepository;
    private final IFornecedorRepository iFornecedorRepository;
    private DateTimeFormatter LocalDateFormatt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final FornecedorService fornecedorService;
    private final CategoriaService  categoriaService;
    private final LinhaService      linhaService;

    public ProdutoService(IProdutoRepository iProdutoRepository,
                          IFornecedorRepository iFornecedorRepository,
                          ILinhaRepository iLinhaRepository, FornecedorService fornecedorService,
                          CategoriaService categoriaService, LinhaService linhaService) {

        this.iProdutoRepository    = iProdutoRepository;
        this.iLinhaRepository      = iLinhaRepository;
        this.iFornecedorRepository = iFornecedorRepository;
        this.fornecedorService     = fornecedorService;
        this.categoriaService      = categoriaService;
        this.linhaService          = linhaService;
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
                produto.setLinha(iLinhaRepository.findById(Long.parseLong(bean[4])).get());
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
    public ProdutoDTO save (ProdutoDTO produtoDTO){

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
        produto.setLinha(iLinhaRepository.findById(produtoDTO.getLinhaId()).get());
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
            produtoExistente.setLinha(iLinhaRepository.findById(produtoDTO.getIdProduto()).get());
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
           /*   String formatarCNPJ = produto.getLinha().getCategoria().getFornecedor().getCnpj().replaceAll
                        ("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");*/

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
                       /* formatarCNPJ,
                          produto.getLinha().getCategoria().getFornecedor().getRazaoSocial().toUpperCase()*/
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importFornecedor(Long id, MultipartFile file) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream());

        CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
        CSVReader csvReader = new CSVReaderBuilder(inputStreamReader)
                .withCSVParser(parser)
                .withSkipLines(1)
                .build();

        List<String[]> linhaString = csvReader.readAll();

        for (String[] bean : linhaString) {

            ProdutoDTO produtoDTO = new ProdutoDTO();
            Categoria categoria = new Categoria();
            Linha linha = new Linha();
            LinhaDTO linhaDTO;

            String codigo = String.format("%1$10s", bean[7].replaceAll("[^a-zA-Z0-9]+", ""));
            codigo = codigo.replaceAll(" ", "0").toUpperCase();

            produtoDTO.setNomeProduto(bean[1]);
            produtoDTO.setCodProduto(bean[2]);
            produtoDTO.setPesoProd(Double.parseDouble(bean[3]));
            produtoDTO.setPesoProd(Double.parseDouble(bean[3]));
            produtoDTO.setPrecoProd(Double.parseDouble(bean[5]));
            produtoDTO.setUnidadeCaixaProd(Double.parseDouble(bean[6]));
            produtoDTO.setValidadeProd(LocalDate.parse(bean[7]));

            if (fornecedorService.existsById(id)) {

                try {
                    if (!(categoriaService.existsCategoriaByCodCategoria(bean[9]))
                            && !(categoriaService.existsCategoriaByCodCategoria(categoriaService.codeContrutor(bean[9], id)))) {

                        Fornecedor fornecedor = FornecedorService.fromDto(fornecedorService.findById(id), new Fornecedor());

                        categoria.setFornecedor(fornecedor);
                        categoria.setNomeCategoria(bean[10]);
                        categoria.setCodCategoria(bean[9]);
                        categoria.setId(categoriaService.save(CategoriaDTO.of(categoria)).getId());

                        linha.setCategoria(categoria);

                        LOGGER.info(String.format("Categoria... ID %d", categoria.getId()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                 try {
                    if (!(linhaService.existsByCodLinha(bean[7])) && !(linhaService.existsByCodLinha(codigo))) {

                        categoria = categoriaService.findByCodCategoria(bean[9]);

                        if (categoria == null) {
                            categoria = categoriaService.findByCodCategoria(categoriaService.codeContrutor(bean[9], id));
                        }

                        linha.setCategoria(categoria);
                        linha.setNomeLinha(bean[8]);
                        linha.setCodLinha(codigo);
                        linhaDTO = linhaService.save(LinhaDTO.of(linha));
                        linha.setIdLinha(linhaDTO.getIdLinha());
                        linha.setCodLinha(linhaDTO.getCodLinha());
                        produtoDTO.setLinhaId(linha.getIdLinha());

                        LOGGER.info(String.format("Linha... ID %d", linha.getIdLinha()));
                    }
                 } catch (Exception e) {
                    e.printStackTrace();
                 }
                 try {
                    if (linhaService.existsByCodLinha(bean[7]) || linhaService.existsByCodLinha(codigo)) {

                        categoria = categoriaService.findByCodCategoria(bean[9]);

                        if (categoria == null) {

                             categoria = categoriaService.findByCodCategoria(categoriaService.codeContrutor(bean[9], id));
                        }
                        linha.setCategoria(categoria);
                        linha.setNomeLinha(bean[8]);
                        linha.setCodLinha(codigo);
                        linha.setIdLinha(linhaService.findByCodLinha(codigo).getIdLinha());

                        produtoDTO.setLinhaId(linha.getIdLinha());

                        linhaDTO = linhaService.update(LinhaDTO.of(linha), linha.getIdLinha());
                        linha.setIdLinha(linhaDTO.getIdLinha());
                        linha.setCodLinha(linhaDTO.getCodLinha());

                        LOGGER.info(String.format("Atualização Linha. ID %d", linha.getIdLinha()));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (existsByCodProduto(bean[0])) {

                        produtoDTO.setLinhaId(linha.getIdLinha());
                        produtoDTO.setIdProduto(findByCodProduto(bean[0]).getIdProduto());
                        update(produtoDTO, produtoDTO.getIdProduto());

                        LOGGER.info(String.format("Produto atualizado. ID %d", produtoDTO.getIdProduto()));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    if (!(existsByCodProduto(bean[0]))) {

                        produtoDTO.setLinhaId(linhaService.findByCodLinha(linha.getCodLinha()).getIdLinha());
                        produtoDTO = save(produtoDTO);
                        produtoDTO = save(produtoDTO);

                        LOGGER.info(String.format("Produto criado. ID %d", produtoDTO.getIdProduto()));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Produto findByCodProduto(String codProduto) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findByCodProduto(codProduto);

        if (produtoOptional.isPresent()) {
            return produtoOptional.get();
        }
        throw new IllegalArgumentException(String.format("Codigo %s não existe.", codProduto));
    }

    private boolean existsByCodProduto(String codProduto) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findByCodProduto(codProduto);

        return produtoOptional.isPresent();
    }
}