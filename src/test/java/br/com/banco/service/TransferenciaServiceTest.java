package br.com.banco.service;

import br.com.banco.AplicationConfigTest;
import br.com.banco.dto.TransferenciaDTO;
import br.com.banco.model.Conta;
import br.com.banco.model.Transferencia;
import br.com.banco.repository.ContaRepository;
import br.com.banco.repository.TransferenciaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@DisplayName("TransferenciaServiceTest")
public class TransferenciaServiceTest extends AplicationConfigTest {

	@Mock
	private TransferenciaRepository transferenciaRepository;

	@Mock
	private ContaRepository contaRepository;

	@InjectMocks
	private TransferenciaService transferenciaService;

	@Test
	public void testObterTodasTransferencias() {
		List<Transferencia> transferencias = new ArrayList<>();
		transferencias.add(new Transferencia(1L, LocalDateTime.now(), BigDecimal.valueOf(100.0), "entrada", "João", new Conta(1L, "Fulano")));
		transferencias.add(new Transferencia(2L, LocalDateTime.now(), BigDecimal.valueOf(50.0), "saida", "Maria", new Conta(2L, "Sicrano")));

		when(transferenciaRepository.findAll()).thenReturn(transferencias);

		List<TransferenciaDTO> result = transferenciaService.obterTodasTransferencias();

		assertEquals(2, result.size());
	}

	@Test
	public void testAdicionarTransferencia() {
		TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
		transferenciaDTO.setDataTransferencia(LocalDateTime.now());
		transferenciaDTO.setValor(BigDecimal.valueOf(200.0));
		transferenciaDTO.setTipo("entrada");
		transferenciaDTO.setNomeOperadorTransacao("Lucas");
		transferenciaDTO.setNumeroConta(1L);

		Conta conta = new Conta(1L, "Fulano");

		when(contaRepository.findById(anyLong())).thenReturn(Optional.of(conta));

		Transferencia transferenciaSalva = new Transferencia(1L, transferenciaDTO.getDataTransferencia(),
				transferenciaDTO.getValor(), transferenciaDTO.getTipo(),
				transferenciaDTO.getNomeOperadorTransacao(), conta);
		when(transferenciaRepository.save(ArgumentMatchers.any(Transferencia.class))).thenReturn(transferenciaSalva);

		TransferenciaDTO result = transferenciaService.adicionarTransferencia(transferenciaDTO);

		assertNotNull(result);
		assertEquals(1L, result.getNumeroConta().longValue());
		// ... Verificar outras propriedades se necessário ...
	}

	@Test
	public void testObterTransferencias() {
		Long numeroConta = 1L;
		LocalDateTime dataInicial = LocalDateTime.of(2022, Month.JANUARY, 1, 0, 0);
		LocalDateTime dataFinal = LocalDateTime.of(2022, Month.DECEMBER, 31, 23, 59);

		List<Transferencia> transferencias = new ArrayList<>();
		transferencias.add(new Transferencia(1L, LocalDateTime.now(), BigDecimal.valueOf(100.0), "entrada", "João", new Conta(1L, "Fulano")));

		when(transferenciaRepository.findBy(numeroConta, dataInicial, dataFinal, null)).thenReturn(transferencias);

		List<TransferenciaDTO> result = transferenciaService.obterTransferencias(numeroConta, dataInicial, dataFinal, null);

		assertEquals(1, result.size());
		// ... Verificar os dados da transferência retornada se necessário ...
	}

	@Test
	public void testRemoverTransferenciaExistente() {
		Long idTransferencia = 1L;

		Transferencia transferencia = new Transferencia();
		transferencia.setId(idTransferencia);

		Mockito.when(transferenciaRepository.findById(idTransferencia)).thenReturn(Optional.of(transferencia));

		transferenciaService.removerTransferencia(idTransferencia);

		Mockito.verify(transferenciaRepository).delete(transferencia);
	}
}
