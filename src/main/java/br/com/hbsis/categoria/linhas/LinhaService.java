package br.com.hbsis.categoria.linhas;


import br.com.hbsis.categoria.produtos.Produto;
import br.com.hbsis.categoria.produtos.ProdutoDTO;
import br.com.hbsis.categoria.produtos.ProdutoService;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import com.microsoft.sqlserver.jdbc.StringUtils;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class LinhaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaService.class);


    private final ILinhaRepository iLinhaRepository;
    private final ProdutoService produtoService;
    private final FornecedorService fornecedorService;

    public LinhaService(ILinhaRepository iLinhaRepository, ProdutoService produtoService, FornecedorService fornecedorService) {
        this.iLinhaRepository  = iLinhaRepository;
        this.produtoService    = produtoService;
        this.fornecedorService = fornecedorService;
    }

    public List<Linha> findAll() {
        return iLinhaRepository.findAll();
    }

    public List<Linha> saveAll(List<Linha> linha) {
        return iLinhaRepository.saveAll(linha);
    }

    public LinhaDTO save(LinhaDTO linhaDTO) {

        LOGGER.debug("Linha: {} ", linhaDTO);

        this.validate(linhaDTO);

        LOGGER.info("Salvando Linha!!!");
        LOGGER.debug("Linha: {}", linhaDTO);

        Linha linha = new Linha();

        linha.setNomeLinha(linhaDTO.getNomeLinha());
        linha.setProduto(linhaDTO.getProduto());

        linha = this.iLinhaRepository.save(linha);

        return LinhaDTO.of(linha);

    }

    public List<Linha> readAll(MultipartFile file) throws Exception {
        InputStreamReader reader   = new InputStreamReader(file.getInputStream());

        CSVReader csvReader        = new CSVReaderBuilder(reader).withSkipLines(1).build();

        List<String[]> lineString  = csvReader.readAll();

        List<Linha> reading = new ArrayList<>();

        for (String[] linhas : lineString) {
            try {

                String[] bean = linhas[0].replaceAll("\"","").split(";");

                Linha linha = new Linha();
                Produto produto = new Produto();
                Fornecedor fornecedor = new Fornecedor();

                ProdutoDTO produtoDTO       = produtoService.findById(Long.parseLong(bean[1]));
                FornecedorDTO fornecedorDTO = fornecedorService.findById(produtoDTO.getFornecedor().getId());

                linha.setNomeLinha(bean[2]);

                fornecedor.setId(fornecedorDTO.getId());
                fornecedor.setRazaoSocial(fornecedorDTO.getRazaoSocial());
                fornecedor.setCnpj(fornecedorDTO.getCnpj());
                fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasia());
                fornecedor.setEmail(fornecedorDTO.getEmail());
                fornecedor.setTelefone(fornecedorDTO.getTelefone());
                fornecedor.setEndereco(fornecedorDTO.getEndereco());


                produto.setFornecedor(fornecedor);
                linha.setProduto(produto);

                reading.add(linha);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return iLinhaRepository.saveAll(reading);
    }


    public void validate(LinhaDTO linhaDTO) {
        LOGGER.info("VALIDANDO LINHA!!");

        if (linhaDTO == null) {
            throw new IllegalArgumentException("Linha não pode ser nulo.");
        }
        if (StringUtils.isEmpty(linhaDTO.getProduto().toString())){
            throw new IllegalArgumentException("ID Produto não pode ser nulo/vazio.");
        }
        if (StringUtils.isEmpty(linhaDTO.getNomeLinha())) {
            throw new IllegalArgumentException("Nome de Linha não poder ser nulo/vazio.");
        }
    }

    public LinhaDTO findById(Long id) {
        Optional<Linha> linhaOptional = this.iLinhaRepository.findById(id);

        if (linhaOptional.isPresent()) {
        return LinhaDTO.of(linhaOptional.get());
    }
        throw new IllegalArgumentException((String.format("ID %s não existe", id)));
        }

    public LinhaDTO update(LinhaDTO linhaDTO, Long id){
        Optional<Linha> linhaOptional = this.iLinhaRepository.findById(id);

        if (linhaOptional.isPresent()){
            Linha linhaExistente = linhaOptional.get();

            LOGGER.info("Atualizando a linha categoria... id:{}", linhaExistente.getId());
            LOGGER.debug("Payload: {}", linhaDTO);
            LOGGER.debug("Linha categoria existente: {}", linhaExistente);

            linhaExistente.setProduto(linhaDTO.getProduto());
            linhaExistente.setNomeLinha(linhaDTO.getNomeLinha());

            linhaExistente = this.iLinhaRepository.save(linhaExistente);

            return linhaDTO.of(linhaExistente);
        }

        throw new IllegalArgumentException((String.format("ID %S NAO EXISTE " ,  id)));
    }

    public  void delete(Long id){
        LOGGER.info("Executando delete para LINHA de ID [{}]", id);
        this.iLinhaRepository.deleteById(id);
   }

}