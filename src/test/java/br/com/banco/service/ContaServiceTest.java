package br.com.banco.service;

import br.com.banco.AplicationConfigTest;
import br.com.banco.dto.ContaDTO;
import br.com.banco.model.Conta;
import br.com.banco.repository.ContaRepository;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@DisplayName("ContaServiceTest")
public class ContaServiceTest extends AplicationConfigTest {

	@MockBean
	private ContaRepository contaRepository;

	@Autowired
	private ContaService contaService;

	@Test
	@DisplayName("Deve remover uma Conta")
	public void deveRemoverUmaConta() {
		Long idConta = 1L;
		Conta conta = new Conta();
		conta.setIdConta(idConta);

		// Configura o comportamento do mock do repositório
		Mockito.when(contaRepository.findById(idConta)).thenReturn(Optional.of(conta));

		// Executa o método a ser testado
		contaService.removerConta(idConta);

		// Verifica se o método delete foi chamado com a conta correta
		Mockito.verify(contaRepository).delete(conta);
	}

	@Test
	@DisplayName("Deve Adicionar uma Conta")
	public void testAdicionarConta() {
		ContaDTO contaDTO = new ContaDTO();
		contaDTO.setNomeResponsavel("Fulano");

		Conta conta = new Conta();
		conta.setNomeResponsavel(contaDTO.getNomeResponsavel());

		Mockito.when(contaRepository.save(Mockito.any(Conta.class))).thenReturn(conta);

		ContaDTO contaAdicionada = contaService.adicionarConta(contaDTO);

		Assert.assertEquals(conta.getNomeResponsavel(), contaAdicionada.getNomeResponsavel());
	}

	@Test
	@DisplayName("Deve Atualizar uma Conta")
	public void deveAtualizarConta() {
		Long idConta = 1L;

		ContaDTO contaDTO = new ContaDTO();
		contaDTO.setNomeResponsavel("Novo Responsável");

		Conta contaExistente = new Conta();
		contaExistente.setIdConta(idConta);
		contaExistente.setNomeResponsavel("Antigo Responsável");

		Conta contaAtualizada = new Conta();
		contaAtualizada.setIdConta(idConta);
		contaAtualizada.setNomeResponsavel(contaDTO.getNomeResponsavel());

		Mockito.when(contaRepository.findById(idConta)).thenReturn(Optional.of(contaExistente));
		Mockito.when(contaRepository.save(Mockito.any(Conta.class))).thenReturn(contaAtualizada);

		ContaDTO contaAtualizadaDTO = contaService.atualizarConta(idConta, contaDTO);

		Assert.assertEquals(contaAtualizada.getIdConta(), contaAtualizadaDTO.getIdConta());
		Assert.assertEquals(contaAtualizada.getNomeResponsavel(), contaAtualizadaDTO.getNomeResponsavel());
	}

	@Test
	@DisplayName("Deve Pesquisar uma Conta")
	public void devePesquisarConta() {
		Long idConta = 1L;

		Conta contaExistente = new Conta();
		contaExistente.setIdConta(idConta);
		contaExistente.setNomeResponsavel("Fulano");

		Mockito.when(contaRepository.findById(idConta)).thenReturn(Optional.of(contaExistente));

		ContaDTO contaEncontradaDTO = contaService.obterContaPorId(idConta);

		Assert.assertEquals(contaExistente.getIdConta(), contaEncontradaDTO.getIdConta());
		Assert.assertEquals(contaExistente.getNomeResponsavel(), contaEncontradaDTO.getNomeResponsavel());
	}




}
