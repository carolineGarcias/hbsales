package br.com.hbsis.produtos;

import br.com.hbsis.fornecedor.IFornecedorRepository;
import br.com.hbsis.linhas.ILinhaRepository;
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

    private final IProdutoRepository    iProdutoRepository;
    private final ILinhaRepository      iLinhaRepository;
    private final IFornecedorRepository iFornecedorRepository;

    public ProdutoService(IProdutoRepository iProdutoRepository,
                          IFornecedorRepository iFornecedorRepository,
                          ILinhaRepository iLinhaRepository) {

        this.iProdutoRepository    = iProdutoRepository;
        this.iLinhaRepository      = iLinhaRepository;
        this.iFornecedorRepository = iFornecedorRepository;
    }

    public List<ProdutoDTO> findAll() {
        List<Produto> produtos = iProdutoRepository.findAll();
        List<ProdutoDTO> produtoDTO = new ArrayList<>();
        produtos.forEach(produto -> produtoDTO.add(ProdutoDTO.of(produto)));
        return produtoDTO;
    }

    public List<Produto> readAll(MultipartFile file) throws Exception {
        InputStreamReader reader  = new InputStreamReader(file.getInputStream());

        CSVReader csvReader       = new CSVReaderBuilder(reader).withSkipLines(1).build();

        List<String[]> lineString = csvReader.readAll();
        List<Produto> reading     = new ArrayList<>();

        for (String[] linhas : lineString) {
            try {

                String[] bean = linhas[0].replaceAll("\"", "").split(";");

                ProdutoDTO produtoDTO = new ProdutoDTO();
                Produto produto = new Produto();

                produto.setNomeProduto(bean[1]);
                produto.setCodProduto(bean[2]);
                produto.setPesoProd(Double.parseDouble(bean[3]));
                produto.setLinha(iLinhaRepository.findById(Long.parseLong(bean[4])).get());
                produto.setPrecoProd(Double.parseDouble(bean[5]));
                produto.setUnidadeCaixaProd(Double.parseDouble(bean[6]));
                produto.setValidadeProd(LocalDate.parse(bean[7]));

                reading.add(produto);
            }  catch (Exception e){
                e.printStackTrace();
            }
        }
        return iProdutoRepository.saveAll(reading);
    }


    public ProdutoDTO save(ProdutoDTO produtoDTO) {

        this.validate(produtoDTO);

        LOGGER.info("Salvando Produto!");
        LOGGER.debug("Produto: {}", produtoDTO);

        Produto produto = new Produto();

        produto.setIdProduto(produtoDTO.getIdProduto());
        produto.setCodProduto(produtoDTO.getCodProduto());
        produto.setNomeProduto(produtoDTO.getNomeProduto());
        produto.setLinha(iLinhaRepository.findById(produtoDTO.getIdProduto()).get());
        produto.setPrecoProd(produtoDTO.getPrecoProd());
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
            throw new IllegalArgumentException("Produto não deve ser nulo");
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
            produtoExistente.setCodProduto(produtoDTO.getCodProduto());
            produtoExistente.setNomeProduto(produtoDTO.getNomeProduto());
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

   /* public void exportCSV(HttpServletResponse httpResponse) {
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
                    "id_produto",
                    "cod_produto",
                    "nome_produto",
                    "preco_produto",
                    "unidade_caixa",
                    "peso_unidade",
                    "validade_produto",
                    "id_linha_produto"
            };

            csvWriter.writeNext(readerCSV);
            for (Produto produto : iProdutoRepository.findAll()) {
                csvWriter.writeNext(new String[]{
                        produto.getIdProduto().toString(),
                        produto.getCodProduto(),
                        produto.getLinha().getIdLinha().toString(),
                        produto.getNomeProduto(),
                        produto.getValidadeProd().toString(),
                        String.valueOf(produto.getPesoProd()),
                        String.valueOf(produto.getUnidadeCaixaProd()),
                        String.valueOf(produto.getPrecoProd()),

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 /*   public void importFornecedor(Long id, MultipartFile file) throws Exception{
        InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream());

        CSVReader csvReader = new CSVReaderBuilder(inputStreamReader)
                .withSkipLines(1)
                .build();

        List<String[]> linhaString = csvReader.readAll();

        List<Produto> reading = new ArrayList<>();

        for (String[] linha : linhaString) {
            try{
                String[] bean = linha[0].replaceAll("\"", "").split(";");

                Produto produto = new Produto();
                if(iFornecedorRepository.existsById(id)){

                    produto.setNomeProduto(bean[1]);
                    produto.setCodProduto(bean[2]);
                    produto.setPesoProd(Double.parseDouble(bean[3]));
                    produto.setLinha(iLinhaRepository.findById(Long.parseLong(bean[4])).get());
                    produto.setPrecoProd(Double.parseDouble(bean[5]));
                    produto.setUnidadeCaixaProd(Double.parseDouble(bean[6]));
                    produto.setValidadeProd(LocalDate.parse(bean[7]));

                    if (iProdutoRepository.existByCodigoProduto(produto.getCodProduto()) &&
                         id == produto.getLinha().getCategoria().getFornecedor().getId()){

                        produto.setIdProduto(iProdutoRepository.findByCodigoProduto
                        (produto.getCodProduto()).get().getIdProduto());
                        update(ProdutoDTO.of(produto), produto.getIdProduto());

                    }
                    else if(id == produto.getLinha().getCategoria().getFornecedor().getId()) {
                        iProdutoRepository.save(produto);
                    }
                    else {

                        LOGGER.info("Produto {} ... pertence a outro fornecedor.", produto.getCodProduto());

                    }
                }
            }  catch (Exception e){
                e.printStackTrace();
            }
        }
    }
*/
}