package br.com.hbsis.fornecedor;


import com.microsoft.sqlserver.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorService.class);
    private final IFornecedorRepository ifornecedorRepository;

    @Autowired
    public FornecedorService(IFornecedorRepository ifornecedorRepository) {
        this.ifornecedorRepository = ifornecedorRepository;
    }

    public Fornecedor saveEntity(FornecedorDTO fornecedorDTO) {
        LOGGER.info("Sanvando fornecedor");
        LOGGER.debug("Payload: {}", fornecedorDTO);

        Fornecedor fornecedor = this.fromDto(fornecedorDTO, new Fornecedor());

        fornecedor = this.ifornecedorRepository.save(fornecedor);

        LOGGER.trace("Fornecedor Salvo {}", fornecedor);

        return fornecedor;
    }


    public Fornecedor fromDto(FornecedorDTO fornecedorDTO, Fornecedor fornecedor) {

        fornecedor.setRazaoSocial(fornecedorDTO.getRazaoSocial());
        fornecedor.setCnpj(fornecedorDTO.getCnpj());
        fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasia());
        fornecedor.setEndereco(fornecedorDTO.getEndereco());
        fornecedor.setTelefone(fornecedorDTO.getTelefone());
        fornecedor.setEmail(fornecedorDTO.getEmail());

        return fornecedor;
    }

    public List<Fornecedor> findAll() {
        return ifornecedorRepository.findAll();
    }

    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {

        this.validate(fornecedorDTO);

        LOGGER.info("Salvando fornecedor");
        LOGGER.debug("Fornecedor: {}", fornecedorDTO);

        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setRazaoSocial(fornecedorDTO.getRazaoSocial());
        fornecedor.setCnpj(fornecedorDTO.getCnpj());
        fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasia());
        fornecedor.setEndereco(fornecedorDTO.getEndereco());
        fornecedor.setTelefone(fornecedorDTO.getTelefone());
        fornecedor.setEmail(fornecedorDTO.getEmail());

        fornecedor = this.ifornecedorRepository.save(fornecedor);

        return FornecedorDTO.of(fornecedor);
    }

    public FornecedorDTO findById(Long idFornecedor) {
        Optional<Fornecedor> fornecedorOptional = this.ifornecedorRepository.findById(idFornecedor);

        if (fornecedorOptional.isPresent()) {
            return FornecedorDTO.of(fornecedorOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", idFornecedor));
    }

    public Fornecedor findByIdFornecedor(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.ifornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            return fornecedorOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Fornecedor findByCnpj(String cnpj) {

        return this.ifornecedorRepository.findByCnpj(cnpj);
    }


    public FornecedorDTO update(Long idFornecedor, FornecedorDTO fornecedorDTO) {
        Optional<Fornecedor> fornecedorExistenteOptional = this.ifornecedorRepository.findById(idFornecedor);

        if (fornecedorExistenteOptional.isPresent()) {
            Fornecedor fornecedorExistente = fornecedorExistenteOptional.get();

            LOGGER.info("Atualizando o fornecedor... id:{}", fornecedorExistente.getIdFornecedor());
            LOGGER.debug("Payload: {}", fornecedorDTO);
            LOGGER.debug("Fornecedor Existente: {}", fornecedorExistente);

            fornecedorExistente.setRazaoSocial(fornecedorDTO.getRazaoSocial());
            fornecedorExistente.setCnpj(fornecedorDTO.getCnpj());
            fornecedorExistente.setNomeFantasia(fornecedorDTO.getNomeFantasia());
            fornecedorExistente.setEndereco(fornecedorDTO.getEndereco());
            fornecedorExistente.setTelefone(fornecedorDTO.getTelefone());
            fornecedorExistente.setEmail(fornecedorDTO.getEmail());

            fornecedorExistente = this.ifornecedorRepository.save(fornecedorExistente);

            return FornecedorDTO.of(fornecedorExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", idFornecedor));

    }

    public void delete(Long idFornecedor) {
        LOGGER.info("Executando delete para fornecedor de ID: [{}]", idFornecedor);

        this.ifornecedorRepository.deleteById(idFornecedor);
    }

    private void validate(FornecedorDTO fornecedorDTO) {

        LOGGER.info("Validando Fornecedor");

        if (fornecedorDTO == null) {
            throw new IllegalArgumentException("FornecedorDTO não deve ser nulo");
        }
        if (StringUtils.isEmpty(fornecedorDTO.getRazaoSocial())) {
            throw new IllegalArgumentException("Razão social não deve ser nula/vazia");
        }
        if (StringUtils.isEmpty((fornecedorDTO.getCnpj()))) {
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
}