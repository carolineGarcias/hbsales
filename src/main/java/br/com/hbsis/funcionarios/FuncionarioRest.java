package br.com.hbsis.funcionarios;

import com.sun.org.apache.xpath.internal.functions.FuncConcat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/funcionarios")
public class FuncionarioRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioRest.class);
    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioRest(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public FuncionarioDTO save(@Valid @RequestBody FuncionarioDTO funcionarioDTO) {

        LOGGER.info("Recebendo solicitação de persistência de Funcionarios...");
        LOGGER.debug("Payaload: {}", funcionarioDTO);

        return this.funcionarioService.save(funcionarioDTO);
    }

    @GetMapping("/{id}")
    public FuncionarioDTO update(@PathVariable("id") Long id) {

        LOGGER.info("Recebendo find by ID... id: [{}]", id);

        return this.funcionarioService.findById(id);
    }

    @PutMapping("{id}")
    public FuncionarioDTO update(@PathVariable Long idFornecedor, @RequestBody FuncionarioDTO funcionarioDTO) {

        LOGGER.info("Recebendo requisição para alteração do funcionario...");
        LOGGER.debug("Payload: {}", funcionarioDTO);

        return this.funcionarioService.update(idFornecedor, funcionarioDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {

        LOGGER.info("Recebendo Delete para fornecedor de ID: {}", id);

        this.funcionarioService.delete(id);
    }
}
