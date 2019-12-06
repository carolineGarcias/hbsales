package br.com.hbsis.fornecedor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FornecedorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorService.class);

    private final IFornecedorRepository fornecedorRepository;

    @Autowired
    public FornecedorService(IFornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {
        return FornecedorDTO.of(this.saveEntity(fornecedorDTO));
    }

    private Fornecedor saveEntity(FornecedorDTO fornecedorDTO) {
        LOGGER.info("Sanvando fornecedor");
        LOGGER.debug("Payload: {}", fornecedorDTO);

        Fornecedor fornecedor = this.fromDto(fornecedorDTO, new Fornecedor());


        fornecedor = this.fornecedorRepository.save(fornecedor);

        LOGGER.trace("Fornecedor Salvo {}", fornecedor);

        return fornecedor;
    }

    private Fornecedor fromDto(FornecedorDTO fornecedorDTO, Fornecedor fornecedor) {

        fornecedor.setRazaoSocial(fornecedorDTO.getRazaoSocial());
        fornecedor.setCnpj(fornecedorDTO.getCnpj());
        fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasia());
        fornecedor.setEndereco(fornecedorDTO.getEndereco());
        fornecedor.setTelefone(fornecedorDTO.getTelefone());
        fornecedor.setEmail(fornecedorDTO.getEmail());

        return fornecedor;
    }

    public FornecedorDTO findById(Long id) {
        return FornecedorDTO.of(findFornecedorById(id));
    }

    public Fornecedor findFornecedorById(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.fornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            return fornecedorOptional.get();
        }


        throw new NoSuchElementException(String.format("Id [%s] não existe...", id));
    }
    public List<FornecedorDTO> findAll() {

        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        List<FornecedorDTO> fornecedorDTO = new ArrayList<>();
        fornecedores.forEach(fornecedor -> fornecedorDTO.add(FornecedorDTO.of(fornecedor)));

        return fornecedorDTO;
    }

    public FornecedorDTO update(Long id, FornecedorDTO fornecedorDTO) {
        Fornecedor fornecedor = this.findFornecedorById(id);

        LOGGER.debug("Atualizando fornecedor....");
        LOGGER.debug("Fornecedor atual: {} / Fornecedor novo: {}", fornecedor, fornecedorDTO);

        fornecedor = this.fornecedorRepository.save(this.fromDto(fornecedorDTO, fornecedor));

        return FornecedorDTO.of(fornecedor);
    }
    public void delete(Long id) {
        LOGGER.info("Executando delete para fornecedor de ID: [{}]", id);

        this.fornecedorRepository.deleteById(id);
    }
}

  /*private void validate(FornecedorDTO fornecedorDTO) {

        LOGGER.info("Validando Fornecedor");

        if (fornecedorDTO == null) {
            throw new IllegalArgumentException("FornecedorDTO não deve ser nulo");
        }
        if (StringUtils.isEmpty(fornecedorDTO.getRazaoSocial())) {
            throw new IllegalArgumentException("Razão social não deve ser nula/vazia");
        }
        if (StringUtils.isEmpty(fornecedorDTO.getCnpj())) {
            throw new IllegalArgumentException("Cnpj não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(fornecedorDTO.getNomeFantasia())) {
            throw new IllegalArgumentException("Nome não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(fornecedorDTO.getEmail())) {
            throw new IllegalArgumentException("E-mail não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(fornecedorDTO.getEndereco())) {
            throw new IllegalArgumentException("Endereço não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(fornecedorDTO.getTelefone())) {
            throw new IllegalArgumentException("Telefone não deve ser nulo/vazio");
        }
    }
*/