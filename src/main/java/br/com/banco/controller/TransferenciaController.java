package br.com.banco.controller;

import br.com.banco.dto.SaldoDTO;
import br.com.banco.dto.TransferenciaDTO;
import br.com.banco.exception.ContaNotFoundException;
import br.com.banco.exception.TransferenciaNotFoundException;
import br.com.banco.service.TransferenciaService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {

	@Autowired
	private TransferenciaService transferenciaService;


	@GetMapping
	public ResponseEntity<List<TransferenciaDTO>> getTransferencias(
			@RequestParam(value = "numeroConta", required = false) Long numeroConta,
			@RequestParam(value = "dataInicial", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
			@RequestParam(value = "dataFinal", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
			@RequestParam(value = "nomeOperador", required = false) String nomeOperador) {

		List<TransferenciaDTO> transferencias = transferenciaService.obterTransferencias(numeroConta, dataInicial, dataFinal, nomeOperador);
		return ResponseEntity.ok(transferencias);
	}


	@GetMapping("/saldo")
	public ResponseEntity<SaldoDTO> obterSaldo(
			@RequestParam(value = "numeroConta", required = false) Long numeroConta,
			@RequestParam(value = "dataInicial", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
			@RequestParam(value = "dataFinal", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
			@RequestParam(value = "nomeOperador", required = false) String nomeOperador) {

		BigDecimal saldoTotal = transferenciaService.calcularSaldoTotal(numeroConta, dataInicial, dataFinal, nomeOperador);
		BigDecimal saldoTotalNoPeriodo = transferenciaService.calcularSaldoTotalNoPeriodo(numeroConta, dataInicial, dataFinal, nomeOperador);

		SaldoDTO saldoDTO = new SaldoDTO();
		saldoDTO.setSaldoTotal(saldoTotal);
		saldoDTO.setSaldoTotalNoPeriodo(saldoTotalNoPeriodo);

		return ResponseEntity.ok(saldoDTO);
	}


	// Obter uma transferência por ID
	@GetMapping("/{id}")
	public ResponseEntity<?> obterTransferenciaPorId(@PathVariable("id") Long id) {
		try {
			TransferenciaDTO transferencia = transferenciaService.obterTransferenciaPorId(id);
			return ResponseEntity.ok(transferencia);
		} catch (TransferenciaNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	// Adicionar uma transferência
	@PostMapping
	public ResponseEntity<?> adicionarTransferencia(@RequestBody TransferenciaDTO transferenciaDTO) {
		try {
			TransferenciaDTO transferenciaAdicionada = transferenciaService.adicionarTransferencia(transferenciaDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(transferenciaAdicionada);
		} catch (ContaNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	// Atualizar uma transferência
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarTransferencia(@PathVariable("id") Long id, @RequestBody TransferenciaDTO transferenciaDTO) {
		try {
			TransferenciaDTO transferenciaAtualizada = transferenciaService.atualizarTransferencia(id, transferenciaDTO);
			return ResponseEntity.ok(transferenciaAtualizada);
		} catch (TransferenciaNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	// Remover uma transferência
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerTransferencia(@PathVariable("id") Long id) {
		try {
			transferenciaService.removerTransferencia(id);
			return ResponseEntity.noContent().build();
		} catch (TransferenciaNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}


}


