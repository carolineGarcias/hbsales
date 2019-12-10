package br.com.hbsis.fornecedor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorRest.class);
    private final FornecedorService fornecedorService;

    @Autowired
    public FornecedorRest(FornecedorService fornecedorService){
        this.fornecedorService = fornecedorService;
    }

    @PostMapping()
       public FornecedorDTO save(@Valid @RequestBody FornecedorDTO fornecedorDTO){

        LOGGER.info("Recebendo solicitação de persistência de Fornecedor...");
        LOGGER.debug("Payaload: {}", fornecedorDTO);

        return this.fornecedorService.save(fornecedorDTO);
    }

    @GetMapping("/{id}")
    public FornecedorDTO update(@PathVariable("id") Long idFornecedor){

        LOGGER.info("Recebendo find by ID... id: [{}]", idFornecedor);

        return this.fornecedorService.findById(idFornecedor);
    }

    @RequestMapping("/listar")
    public List<Fornecedor> findFornecedor(){
        List<Fornecedor> fornecedor = fornecedorService.findAll();

        return fornecedor;
    }

    @PutMapping("{id}")
    public FornecedorDTO update(@PathVariable Long idFornecedor, @RequestBody FornecedorDTO fornecedorDTO) {

        LOGGER.info("Recebendo requisição para alteração do fornecedor...");
        LOGGER.debug("Payload: {}", fornecedorDTO);

        return this.fornecedorService.update(idFornecedor, fornecedorDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long idFornecedor) {

        LOGGER.info("Recebendo Delete para fornecedor de ID: {}", idFornecedor);

        this.fornecedorService.delete(idFornecedor);
    }
}