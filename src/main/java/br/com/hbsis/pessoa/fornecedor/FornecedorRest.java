package br.com.hbsis.pessoa.fornecedor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorRest {

	private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorRest.class);

	private final FornecedorService fornecedorService;

	@Autowired
	public FornecedorRest(FornecedorService fornecedorService) {
		this.fornecedorService = fornecedorService;
	}

	@PostMapping
	public FornecedorDTO save(@Valid @RequestBody FornecedorDTO fornecedorDTO) {
		LOGGER.info("Recebendo requisição para persistência de fornecedor...");
		LOGGER.debug("Payload {}", fornecedorDTO);

		return this.fornecedorService.save(fornecedorDTO);
	}

	@GetMapping("{id}")
	public FornecedorDTO findById(@PathVariable Long id) {

		LOGGER.info("Recebendo consulta para o id [{}]", id);

		return this.fornecedorService.findById(id);
	}

	@PutMapping("{id}")
	public FornecedorDTO update(@PathVariable Long id, @RequestBody FornecedorDTO fornecedorDTO) {

		LOGGER.info("Recebendo requisição para alteração do fornecedor...");
		LOGGER.debug("Payload: {}", fornecedorDTO);

		return this.fornecedorService.update(id, fornecedorDTO);
	}
}
