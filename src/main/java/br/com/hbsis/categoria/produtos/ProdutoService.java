package br.com.hbsis.categoria.produtos;

import br.com.hbsis.fornecedor.FornecedorService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);

    private final IProdutoRepository iProdutoRepository;
    private final FornecedorService fornecedorService;

    public ProdutoService(IProdutoRepository iProdutoRepository, FornecedorService fornecedorService) {
        this.iProdutoRepository = iProdutoRepository;
        this.fornecedorService = fornecedorService;
    }

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
    public ProdutoDTO findById(Long id) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findById(id);

        if(produtoOptional.isPresent()){
            return ProdutoDTO.of(produtoOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public List<Produto> findAll () {

        List<Produto> produtos = iProdutoRepository.findAll();

        return produtos;
    }

    public ProdutoDTO update(ProdutoDTO produtoDTO, Long id) {

        Optional<Produto> produtoExistenteOptional = this.iProdutoRepository.findById(id);

        if (produtoExistenteOptional.isPresent()){
            Produto produtoExistente = produtoExistenteOptional.get();

            LOGGER.info("Atualizando produto... id: [{}]", produtoExistente.getId());
            LOGGER.debug("Payload: {}", produtoDTO);
            LOGGER.debug("Fornecedor Existente: {}", produtoExistente);

            produtoExistente.setCodProduto(produtoDTO.getCodProduto());
            produtoExistente.setNomeProduto(produtoDTO.getNomeProduto());
            produtoExistente.setFornecedor(produtoDTO.getFornecedor());

            produtoExistente = this.iProdutoRepository.save(produtoExistente);

            return produtoDTO.of(produtoExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));

    }

    public void delete(Long id){
        LOGGER.info("Executando delete para categoria produto de ID> [{}]", id);

        this.iProdutoRepository.deleteById(id);
    }

}
