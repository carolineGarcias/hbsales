package br.com.hbsis.funcionarios;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FuncionarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioService.class);
    private final IFuncionarioRepository ifuncionarioRepository;

    public FuncionarioService(IFuncionarioRepository ifuncionarioRepository) {
        this.ifuncionarioRepository = ifuncionarioRepository;
    }

    public FuncionarioDTO findById(Long id) {
        Optional<Funcionario> funcionarioOptional = this.ifuncionarioRepository.findById(id);

        if (funcionarioOptional.isPresent()) {
            return FuncionarioDTO.of(funcionarioOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public FuncionarioDTO save(FuncionarioDTO funcionarioDTO) {

        this.validate(funcionarioDTO);

        LOGGER.info("Salvando funcionário");
        LOGGER.info("Funcionario: {}", funcionarioDTO);

        Funcionario funcionario = new Funcionario();

        funcionario.setNomeFuncionario(funcionarioDTO.getNomeFuncionario().toUpperCase());
        funcionario.setEmailFuncionario(funcionarioDTO.getEmailFuncionario());
        funcionario.setUuidFuncionario(UUID.randomUUID().toString());

        funcionario = this.ifuncionarioRepository.save(funcionario);

        return funcionarioDTO.of(funcionario);
    }

    public FuncionarioDTO update(Long id, FuncionarioDTO funcionarioDTO) {
        Optional<Funcionario> funcionarioExistenteOptional = this.ifuncionarioRepository.findById(id);

        if (funcionarioExistenteOptional.isPresent()) {
            Funcionario funcionarioExistente = funcionarioExistenteOptional.get();

            LOGGER.info("Atualizando usuário... id: [{}]", funcionarioExistente.getId());
            LOGGER.debug("Payload: {}", funcionarioDTO);
            LOGGER.debug("Usuario Existente: {}", funcionarioExistente);

            funcionarioExistente.setNomeFuncionario(funcionarioDTO.getNomeFuncionario());
            funcionarioExistente.setEmailFuncionario(funcionarioDTO.getEmailFuncionario());
            funcionarioExistente.setUuidFuncionario(funcionarioDTO.getUuidFuncionario());

            funcionarioExistente = this.ifuncionarioRepository.save(funcionarioExistente);
            return FuncionarioDTO.of(funcionarioExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    private void validate(FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Validando Funcionario");

        if (funcionarioDTO == null) {
            throw new IllegalArgumentException("Funcionario não deve ser nulo");
        }

        if (StringUtils.isEmpty(funcionarioDTO.getNomeFuncionario())) {
            throw new IllegalArgumentException("Nome não deve ser nulo/vazio");
        }

        if (StringUtils.isEmpty(funcionarioDTO.getEmailFuncionario())) {
            throw new IllegalArgumentException("Email não deve ser nulo/vazio");
        }

        if (StringUtils.isEmpty(funcionarioDTO.getUuidFuncionario())) {
            throw new IllegalArgumentException("UUid não deve ser nulo/vazio");
        }
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para funcionario de ID: [{}]", id);

        this.ifuncionarioRepository.deleteById(id);
    }
}

