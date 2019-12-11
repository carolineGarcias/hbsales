package br.com.hbsis.pessoa.fornecedor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FornecedorServiceTest {

	@Mock
	private IFornecedorRepository iFornecedorRepository;

	@InjectMocks
	private FornecedorService fornecedorService;

	@Test
	public void saveFornecedor() {
		FornecedorDTO fornecedorDTO = new FornecedorDTO(
				null,
				"Minha querida Razão social",
				"81875973000176",
				"Este é meu nome fantasia",
				"Rua alameda avenida numero 1",
				"99999999999",
				"mail@mail.com.br"
		);


		Fornecedor fornecedorEntityMock = mock(Fornecedor.class);

		when(fornecedorEntityMock.getRazaoSocial()).thenReturn(fornecedorDTO.getRazaoSocial());
		when(fornecedorEntityMock.getCnpj()).thenReturn(fornecedorDTO.getCnpj());
		when(fornecedorEntityMock.getNomeFantasia()).thenReturn(fornecedorDTO.getNomeFantasia());
		when(fornecedorEntityMock.getEndereco()).thenReturn(fornecedorDTO.getEndereco());
		when(fornecedorEntityMock.getEmailContato()).thenReturn(fornecedorDTO.getEmailContato());
		when(fornecedorEntityMock.getTelefoneContato()).thenReturn(fornecedorDTO.getTelefoneContato());

		when(this.iFornecedorRepository.save(any())).thenReturn(fornecedorEntityMock);

		Fornecedor fornecedor = fornecedorService.saveEntity(fornecedorDTO);

		verify(this.iFornecedorRepository, times(1)).save(any());

		assertEquals(fornecedorDTO.getRazaoSocial(), fornecedor.getRazaoSocial());
		assertEquals(fornecedorDTO.getCnpj(), fornecedor.getCnpj());
		assertEquals(fornecedorDTO.getNomeFantasia(), fornecedor.getNomeFantasia());
		assertEquals(fornecedorDTO.getEndereco(), fornecedor.getEndereco());
		assertEquals(fornecedorDTO.getEmailContato(), fornecedor.getEmailContato());
		assertEquals(fornecedorDTO.getTelefoneContato(), fornecedor.getTelefoneContato());

		assertEquals(fornecedorEntityMock, fornecedor);
	}

}
