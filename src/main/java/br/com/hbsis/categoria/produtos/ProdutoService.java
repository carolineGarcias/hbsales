package br.com.hbsis.categoria.produtos;

import br.com.hbsis.categoria.linhas.ILinhaRepository;
import br.com.hbsis.fornecedor.IFornecedorRepository;
import com.google.common.net.HttpHeaders;
import com.opencsv.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;

@Service
public class ProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);

    private final IProdutoRepository iProdutoRepository;
    private final IFornecedorRepository iFornecedorRepository;
    private final ILinhaRepository iLinhaRepository;

    public ProdutoService(IProdutoRepository iProdutoRepository,
                          IFornecedorRepository iFornecedorRepository,
                          ILinhaRepository iLinhaRepository) {

        this.iProdutoRepository    = iProdutoRepository;
        this.iFornecedorRepository = iFornecedorRepository;
        this.iLinhaRepository      = iLinhaRepository;
    }

    public List<Produto> findAll() { //
        return iProdutoRepository.findAll();
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
                produto.setCodProduto(Long.parseLong(bean[2]));
                produto.setPesoProd(Double.parseDouble(bean[3]));
                produto.setLinha(iLinhaRepository.findById(Long.parseLong(bean[4])).get());
                produto.setPrecoProd(Double.parseDouble(bean[5]));
                produto.setUnidadeCaixaProd(Double.parseDouble(bean[6]));
                produto.setValidadeProd(bean[7]);

                reading.add(produto);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return iProdutoRepository.saveAll(reading);
    }

    public List<Produto> saveAll(List<Produto> produto) throws Exception { //

        return iProdutoRepository.saveAll(produto);
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {

        this.validate(produtoDTO);

        LOGGER.info("Salvando Produto!");
        LOGGER.debug("Produto: {}", produtoDTO);

        Produto produto = new Produto();

        produto.setId(produtoDTO.getId());
        produto.setCodProduto(produtoDTO.getCodProduto());
        produto.setNomeProduto(produtoDTO.getNomeProduto());
        produto.setLinha(iLinhaRepository.findById(produtoDTO.getLinhaId()).get());
        produto.setPrecoProd(produtoDTO.getPrecoProd());
        produto.setUnidadeCaixaProd(produtoDTO.getUnidadeCaixaProd());
        produto.setPesoProd(produtoDTO.getPesoProd());
        produto.setValidadeProd(produtoDTO.getValidadeProd());


        produto = this.iProdutoRepository.save(produto);

        return ProdutoDTO.of(produto);

    }

    private void validate(ProdutoDTO produtoDTO) {

        LOGGER.info("Validando Produto");

        if (produtoDTO == null) {
            throw new IllegalArgumentException("Produto não deve ser nulo");
        }
    }

    public ProdutoDTO findById(Long id) { //
        Optional<Produto> produtoOptional = this.iProdutoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            return ProdutoDTO.of(produtoOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) { //
        LOGGER.info("Executando delete para produto de ID> [{}]", id);

        this.iProdutoRepository.deleteById(id);
    }

    public ProdutoDTO update(ProdutoDTO produtoDTO, Long id) {

        Optional<Produto> produtoExistenteOptional = this.iProdutoRepository.findById(id);

        if (produtoExistenteOptional.isPresent()) {
            Produto produtoExistente = produtoExistenteOptional.get();

            LOGGER.info("Atualizando produto... id: [{}]", produtoExistente.getId());
            LOGGER.debug("Payload: {}", produtoDTO);
            LOGGER.debug("Fornecedor Existente: {}", produtoExistente);

            produtoExistente.setId(produtoDTO.getId());
            produtoExistente.setCodProduto(produtoDTO.getCodProduto());
            produtoExistente.setNomeProduto(produtoDTO.getNomeProduto());
            produtoExistente.setLinha(iLinhaRepository.findById(produtoDTO.getLinhaId()).get());
            produtoExistente.setPrecoProd(produtoDTO.getPrecoProd());
            produtoExistente.setUnidadeCaixaProd(produtoDTO.getUnidadeCaixaProd());
            produtoExistente.setPesoProd(produtoDTO.getPesoProd());
            produtoExistente.setValidadeProd(produtoDTO.getValidadeProd());

            produtoExistente = this.iProdutoRepository.save(produtoExistente);

            return produtoDTO.of(produtoExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));

    }

    public void importProduto(Long id, MultipartFile file) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream());

        CSVReader csvReader = new CSVReaderBuilder(inputStreamReader)
                .withSkipLines(1)
                .build();

        List<String[]> linhaString = csvReader.readAll();
        List<Produto> resultadoLeitura = new ArrayList<>();

        for (String[] linha : linhaString) {
            try {
                String[] bean = linha[0].replaceAll("\"", "").split(";");

                Produto produto = new Produto();
                if (iFornecedorRepository.existsById(id)) {
                    produto.setNomeProduto(bean[1]);
                    produto.setCodProduto(Long.parseLong(bean[2]));
                    produto.setPesoProd(Double.parseDouble(bean[3]));
                    produto.setLinha(iLinhaRepository.findById(Long.parseLong(bean[4])).get());
                    produto.setPrecoProd(Double.parseDouble(bean[5]));
                    produto.setUnidadeCaixaProd(Double.parseDouble(bean[6]));
                    produto.setValidadeProd(bean[7]);

                    if (iProdutoRepository.existsByCodigoProduto(produto.getCodProduto()) &&
                            id == produto.getLinha().getProduto().getFornecedor().getId()) {
                        produto.setId(iProdutoRepository.findByCodigoProduto(produto.getCodProduto()).get().getId());
                        update(ProdutoDTO.of(produto), produto.getId());
                    } else if (id == produto.getLinha().getProduto().getFornecedor().getId()) {
                        iProdutoRepository.save(produto);
                    } else {
                        LOGGER.info("Produto {} ... pertence a outro fornecedor.", produto.getCodProduto());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
        public void exportCSV(HttpServletResponse response) {
            try {
                String filename = "produtos.csv";

                response.setContentType("text/csv");
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"");

                PrintWriter writer1 = response.getWriter();
                ICSVWriter icsvWriter = new CSVWriterBuilder(writer1).
                        withSeparator(';').
                        withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).
                        withLineEnd   (CSVWriter.DEFAULT_LINE_END).
                        build();

                String readerCSV[] = {
                        "id_produto",
                        "cod_produto",
                        "nome_produto",
                        "preço_prod",
                        "unidade_caixa",
                        "peso_unidade",
                        "validade_prod",
                        "id_linha_produto"
                };

                icsvWriter.writeNext(readerCSV);
                for (Produto produto : iProdutoRepository.findAll()) {
                    icsvWriter.writeNext (new String[]{
                            produto.getId().toString(),
                            produto.getLinha().getIdLinha().toString(),
                            produto.getCodProduto().toString(),
                            produto.getPrecoProd().toString(),
                            produto.getUnidadeCaixaProd().toString(),
                            produto.getPesoProd().toString(),
                            produto.getValidadeProd(),
                            produto.getNomeProduto()
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

