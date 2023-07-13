package br.com.banco.service;

import br.com.banco.dto.ContaDTO;
import br.com.banco.exception.ContaNotFoundException;
import br.com.banco.model.Conta;
import br.com.banco.repository.ContaRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaService {


	@Autowired
	private ContaRepository contaRepository;

	public List<ContaDTO> obterTodasContas() {
		List<Conta> contas = contaRepository.findAll();
		return contas.stream()
				.map(this::convertToContaDTO)
				.collect(Collectors.toList());
	}

	public ContaDTO obterContaPorId(Long id) {
		Conta conta = contaRepository.findById(id)
				.orElseThrow(() -> new ContaNotFoundException("Conta não encontrada com o ID: " + id));
		return convertToContaDTO(conta);
	}

	@Transactional
	public ContaDTO adicionarConta(ContaDTO contaDTO) {
		Conta conta = convertToConta(contaDTO);
		Conta contaSalva = contaRepository.save(conta);
		return convertToContaDTO(contaSalva);
	}

	@Transactional
	public ContaDTO atualizarConta(Long id, ContaDTO contaDTO) throws ServiceException {
		Conta conta = contaRepository.findById(id)
				.orElseThrow(() -> new ServiceException("Conta não encontrada com o ID: " + id));

		conta.setNomeResponsavel(contaDTO.getNomeResponsavel());
		Conta contaAtualizada = contaRepository.save(conta);
		return convertToContaDTO(contaAtualizada);
	}

	@Transactional
	public void removerConta(Long id) {
		Conta conta = contaRepository.findById(id)
				.orElseThrow(() -> new ServiceException("Conta não encontrada com o ID: " + id));

		contaRepository.delete(conta);
	}

	private ContaDTO convertToContaDTO(Conta conta) {
		ContaDTO contaDTO = new ContaDTO();
		contaDTO.setIdConta(conta.getIdConta());
		contaDTO.setNomeResponsavel(conta.getNomeResponsavel());
		return contaDTO;
	}

	private Conta convertToConta(ContaDTO contaDTO) {
		Conta conta = new Conta();
		conta.setIdConta(contaDTO.getIdConta());
		conta.setNomeResponsavel(contaDTO.getNomeResponsavel());
		return conta;
	}

}
