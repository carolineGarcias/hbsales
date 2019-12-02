package br.com.hbsis.categoria.produtos;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.lang.StringUtils;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);

    private final IProdutoRepository iProdutoRepository;
    private final FornecedorService fornecedorService;

    public ProdutoService(IProdutoRepository iProdutoRepository, FornecedorService fornecedorService) {
        this.iProdutoRepository = iProdutoRepository;
        this.fornecedorService  = fornecedorService;
    }

    public List<Produto> findAll(){ //
        return iProdutoRepository.findAll();
    }

    /*public List<String> stringLista(){ //
        List<String> listar = new ArrayList<>();
        for(Produto csv : iProdutoRepository.findAll()){
            String listacsv =
                    csv.getId()           + ";"
                            + csv.getCodProduto()   + ";"
                            + csv.getNomeProduto()  + ";"
                            + csv.getFornecedor().getId() + ";" ;
            listar.add(listacsv);
        }
        return listar;
    }
*/
   public List<Produto> readAll(MultipartFile file) throws Exception { //

        InputStreamReader inputReader = new InputStreamReader(file.getInputStream());
        CSVReader readerCsv = new CSVReaderBuilder(inputReader).withSkipLines(1).build();

        List<String[]> findLine = readerCsv.readAll();
        List<Produto> Reading = new ArrayList<>();

        for (String[] linha : findLine) {
            try {

                String[] line = linha[0].replaceAll("\""," ").split(";");

                Fornecedor fornecedor = new Fornecedor();
                FornecedorDTO fornecedorDTO = new FornecedorDTO();
                Produto produto = new Produto();

                produto.setCodProduto(Long.parseLong(line[1]));  ///traduzindo p/ Java;
                produto.setNomeProduto(line[2]);

                fornecedorDTO = fornecedorService.findById(Long.parseLong(line[3]));

                fornecedor.setId(fornecedorDTO.getId());
                fornecedor.setRazaoSocial(fornecedorDTO.getRazaoSocial());
                fornecedor.setCnpj(fornecedorDTO.getCnpj());
                fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasia());
                fornecedor.setEndereco(fornecedorDTO.getEndereco());
                fornecedor.setTelefone(fornecedorDTO.getTelefone());
                fornecedor.setEmail(fornecedorDTO.getEmail());

                produto.setFornecedor(fornecedor);

                Reading.add(produto);
            }
            catch (Exception exception){
                exception.printStackTrace();
            }
        }

        return iProdutoRepository.saveAll(Reading);
    }

    public List<Produto> saveAll(List<Produto> produto) throws Exception { //

        return iProdutoRepository.saveAll(produto);
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {

        this.validate(produtoDTO);

        LOGGER.info("Salvando Produto!");
        LOGGER.debug("Produto: {}", produtoDTO);

        Produto produto = new Produto();

        produto.setNomeProduto(produtoDTO.getNomeProduto());
        produto.setCodProduto (produtoDTO.getCodProduto());
        produto.setFornecedor (produtoDTO.getFornecedor());

        produto = this.iProdutoRepository.save(produto);

        return ProdutoDTO.of(produto);
    }

    private void validate(ProdutoDTO produtoDTO) {

        LOGGER.info("Validando Produto");

        if (produtoDTO == null) {
            throw new IllegalArgumentException("Produto não deve ser nulo");
        }
        if (StringUtils.isEmpty(produtoDTO.getNomeProduto())) {
            throw new IllegalArgumentException("Nome não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(produtoDTO.getFornecedor().toString())) {
            throw new IllegalArgumentException("Fornecedor não deve ser nulo/vazio");
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

            produtoExistente.setCodProduto (produtoDTO.getCodProduto());
            produtoExistente.setNomeProduto(produtoDTO.getNomeProduto());
            produtoExistente.setFornecedor (produtoDTO.getFornecedor());

            produtoExistente = this.iProdutoRepository.save(produtoExistente);

            return produtoDTO.of(produtoExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));

    }

     /*
    public List<Produto> findAll () {

        List<Produto> produto = iProdutoRepository.findAll();

        return produto;
    }
   */
    /* public List<String> importExcel(List<Produto> produto ) {

        List<String> produtosExcel = new ArrayList<>();
        for (int i = 0; i < produto.size() ; i++) {
            produtosExcel.add(produto.get(i).toString());
        }

        return produtosExcel;
    }

    }

   /*
    public ProdutoDTO save(ProdutoDTO produtoDTO) {

        produtoDTO.setFornecedor(fornecedorService.findFornecedorById((produtoDTO.getFornecedor().getId())));
        this.validate(produtoDTO);

        LOGGER.info("Salvando Produto");
        LOGGER.debug("Produto: {}", produtoDTO);
        LOGGER.debug("Fornecedor: {}", produtoDTO.getFornecedor().getNomeFantasia());

        Produto produto = new Produto(
                produtoDTO.getId(),
                produtoDTO.getCodProduto(),
                produtoDTO.getNomeProduto(),
                produtoDTO.getFornecedor());

        produto = this.iProdutoRepository.save(produto);

        return produtoDTO.of(produto);
    }

    */

}
