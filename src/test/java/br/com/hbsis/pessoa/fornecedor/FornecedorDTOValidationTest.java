package br.com.hbsis.pessoa.fornecedor;

import br.com.hbsis.fornecedor.FornecedorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FornecedorDTOValidationTest {

	private Validator validator;

	@BeforeEach
	public void init() {
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		this.validator = vf.getValidator();
	}

	@Test
	public void everythingIsValid() {
		FornecedorDTO fornecedorDTO = new FornecedorDTO(
				0L,
				"Minha querida razão social",
				"81875973000176",
				"Meu querido nome fantasia",
				"meu querido endereço em alguma rua",
				"99999999999",
				"meu_querido@mail.com.br"
		);

		Set<ConstraintViolation<FornecedorDTO>> violations = this.validator.validate(fornecedorDTO);
		assertTrue(violations.isEmpty());
	}

	@Test
	public void fornecedorComCnpjErrado(){
		FornecedorDTO fornecedorDTO = new FornecedorDTO(
				0L,
				"Minha querida razão social",
				"99999999999",
				"Meu querido nome fantasia",
				"meu querido endereço em alguma rua",
				"99999999999",
				"meu_querido@mail.com.br"
		);


		Set<ConstraintViolation<FornecedorDTO>> violationSet = this.validator.validate(fornecedorDTO);

		assertEquals(1, violationSet.size());
	}
}

