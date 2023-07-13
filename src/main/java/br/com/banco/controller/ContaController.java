package br.com.banco.controller;

import br.com.banco.dto.ContaDTO;
import br.com.banco.exception.ContaNotFoundException;
import br.com.banco.service.ContaService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

	@Autowired
	private ContaService contaService;


	// Obter todas as contas
	@GetMapping
	public ResponseEntity<List<ContaDTO>> obterContas() {
		try {
			List<ContaDTO> contas = contaService.obterTodasContas();
			return ResponseEntity.ok(contas);
		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// Obter uma conta por ID
	@GetMapping("/contas/{id}")
	public ResponseEntity<?> obterContaPorId(@PathVariable("id") Long id) {
		try {
			ContaDTO conta = contaService.obterContaPorId(id);
			return ResponseEntity.ok(conta);
		} catch (ContaNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	// Adicionar uma conta
	@PostMapping("/contas")
	public ResponseEntity<?> adicionarConta(@RequestBody ContaDTO contaDTO) {
		try {
			ContaDTO contaAdicionada = contaService.adicionarConta(contaDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(contaAdicionada);
		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	// Atualizar uma conta
	@PutMapping("/contas/{id}")
	public ResponseEntity<?> atualizarConta(@PathVariable("id") Long id, @RequestBody ContaDTO contaDTO) {
		try {
			ContaDTO contaAtualizada = contaService.atualizarConta(id, contaDTO);
			return ResponseEntity.ok(contaAtualizada);
		} catch (ContaNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	// Remover uma conta
	@DeleteMapping("/contas/{id}")
	public ResponseEntity<?> removerConta(@PathVariable("id") Long id) {
		try {
			contaService.removerConta(id);
			return ResponseEntity.noContent().build();
		} catch (ContaNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}

