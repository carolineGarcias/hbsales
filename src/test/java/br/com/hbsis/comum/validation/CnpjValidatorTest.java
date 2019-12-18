package br.com.hbsis.comum.validation;

import br.com.hbsis.validation.ValidatorCNPJ;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorCNPJTest {

	@Test
	public void cnpjIsValido() {
		ValidatorCNPJ cnpjValidator = new ValidatorCNPJ();

		final String cnpjHbsis = "81875973000176";

		boolean valid = cnpjValidator.isValid(cnpjHbsis, null);

		assertTrue(valid);
	}

	@Test
	public void cnpjComDigitoVerificadorErrado() {
		ValidatorCNPJ cnpjValidator = new ValidatorCNPJ();

		final String cnpjHbsis = "81875973000172";

		boolean valid = cnpjValidator.isValid(cnpjHbsis, null);

		assertFalse(valid);
	}

	@Test
	public void cnpjContainsLetras() {
		ValidatorCNPJ cnpjValidator = new ValidatorCNPJ();

		final String cnpjHbsis = "8187597300017A";

		boolean valid = cnpjValidator.isValid(cnpjHbsis, null);

		assertFalse(valid);
	}

	@Test
	public void cnpjContainsMuitosCaracteres() {
		ValidatorCNPJ cnpjValidator = new ValidatorCNPJ();

		final String cnpjHbsis = "818759730001762";

		boolean valid = cnpjValidator.isValid(cnpjHbsis, null);

		assertFalse(valid);
	}


}
