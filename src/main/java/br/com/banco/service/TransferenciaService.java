package br.com.banco.service;

import br.com.banco.dto.TransferenciaDTO;
import br.com.banco.exception.ContaNotFoundException;
import br.com.banco.exception.TransferenciaNotFoundException;
import br.com.banco.model.Conta;
import br.com.banco.model.Transferencia;
import br.com.banco.repository.ContaRepository;
import br.com.banco.repository.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TransferenciaService {

	@Autowired
	private TransferenciaRepository transferenciaRepository;
	@Autowired
	private ContaRepository contaRepository;

	public List<TransferenciaDTO> obterTodasTransferencias() {
		List<Transferencia> transferencias = transferenciaRepository.findAll();
		return transferencias.stream()
				.map(this::convertToTransferenciaDTO)
				.collect(Collectors.toList());
	}

	public List<TransferenciaDTO> obterTransferencias(Long numeroConta, LocalDateTime dataInicial, LocalDateTime dataFinal, String nomeOperador) {
		List<Transferencia> transferencias = transferenciaRepository.findBy(numeroConta, dataInicial, dataFinal, nomeOperador);
		return transferencias.stream()
				.map(this::convertToTransferenciaDTO)
				.collect(Collectors.toList());
	}

	public TransferenciaDTO obterTransferenciaPorId(Long id) {
		Transferencia transferencia = transferenciaRepository.findById(id)
				.orElseThrow(() -> new TransferenciaNotFoundException("Transferência não encontrada com o ID: " + id));
		return convertToTransferenciaDTO(transferencia);
	}

	@Transactional
	public TransferenciaDTO adicionarTransferencia(TransferenciaDTO transferenciaDTO) {
		Conta conta = contaRepository.findById(transferenciaDTO.getNumeroConta())
				.orElseThrow(() -> new ContaNotFoundException("Conta não encontrada com o ID: " + transferenciaDTO.getNumeroConta()));

		Transferencia transferencia = convertToTransferencia(transferenciaDTO);
		transferencia.setConta(conta);
		Transferencia transferenciaSalva = transferenciaRepository.save(transferencia);
		return convertToTransferenciaDTO(transferenciaSalva);
	}

	@Transactional
	public TransferenciaDTO atualizarTransferencia(Long id, TransferenciaDTO transferenciaDTO) {
		Transferencia transferencia = transferenciaRepository.findById(id)
				.orElseThrow(() -> new TransferenciaNotFoundException("Transferência não encontrada com o ID: " + id));

		transferencia.setDataTransferencia(transferenciaDTO.getDataTransferencia());
		transferencia.setValor(transferenciaDTO.getValor());
		transferencia.setTipo(transferenciaDTO.getTipo());
		transferencia.setNomeOperadorTransacao(transferenciaDTO.getNomeOperadorTransacao());

		Transferencia transferenciaAtualizada = transferenciaRepository.save(transferencia);
		return convertToTransferenciaDTO(transferenciaAtualizada);
	}

	@Transactional
	public void removerTransferencia(Long id) {
		Transferencia transferencia = transferenciaRepository.findById(id)
				.orElseThrow(() -> new TransferenciaNotFoundException("Transferência não encontrada com o ID: " + id));

		transferenciaRepository.delete(transferencia);
	}

	public BigDecimal calcularSaldoTotal(Long numeroConta, LocalDateTime dataInicial, LocalDateTime dataFinal, String nomeOperador) {
		BigDecimal saldoTotal = BigDecimal.ZERO;
		List<Transferencia> transferencias = transferenciaRepository.findBy(numeroConta, dataInicial, dataFinal, nomeOperador);
		for (Transferencia transferencia : transferencias) {
			if (transferencia.getTipo().equals("entrada")) {
				saldoTotal = saldoTotal.add(transferencia.getValor());
			} else if (transferencia.getTipo().equals("saida")) {
				saldoTotal = saldoTotal.subtract(transferencia.getValor());
			}
		}
		return saldoTotal;
	}

	public BigDecimal calcularSaldoTotalNoPeriodo(Long numeroConta, LocalDateTime dataInicial, LocalDateTime dataFinal, String nomeOperador) {
		BigDecimal saldoTotalNoPeriodo = BigDecimal.ZERO;
		List<Transferencia> transferencias = transferenciaRepository.findBy(numeroConta, dataInicial, dataFinal, nomeOperador);
		for (Transferencia transferencia : transferencias) {
			if (transferencia.getTipo().equals("entrada") && transferencia.getDataTransferencia().isAfter(dataInicial) && transferencia.getDataTransferencia().isBefore(dataFinal)) {
				saldoTotalNoPeriodo = saldoTotalNoPeriodo.add(transferencia.getValor());
			} else if (transferencia.getTipo().equals("saida") && transferencia.getDataTransferencia().isAfter(dataInicial) && transferencia.getDataTransferencia().isBefore(dataFinal)) {
				saldoTotalNoPeriodo = saldoTotalNoPeriodo.subtract(transferencia.getValor());
			}
		}
		return saldoTotalNoPeriodo;
	}

	private TransferenciaDTO convertToTransferenciaDTO(Transferencia transferencia) {
		TransferenciaDTO transferenciaDTO = new TransferenciaDTO();

		transferenciaDTO.setId(transferencia.getId());
		transferenciaDTO.setDataTransferencia(transferencia.getDataTransferencia());
		transferenciaDTO.setValor(transferencia.getValor());
		transferenciaDTO.setTipo(transferencia.getTipo());
		transferenciaDTO.setNomeOperadorTransacao(transferencia.getNomeOperadorTransacao());
		transferenciaDTO.setNumeroConta(transferencia.getConta().getIdConta());
		return transferenciaDTO;
	}

	private Transferencia convertToTransferencia(TransferenciaDTO transferenciaDTO) {
		Transferencia transferencia = new Transferencia();
		transferencia.setId(transferenciaDTO.getId());
		transferencia.setDataTransferencia(transferenciaDTO.getDataTransferencia());
		transferencia.setValor(transferenciaDTO.getValor());
		transferencia.setTipo(transferenciaDTO.getTipo());
		transferencia.setNomeOperadorTransacao(transferenciaDTO.getNomeOperadorTransacao());
		return transferencia;
	}
}
