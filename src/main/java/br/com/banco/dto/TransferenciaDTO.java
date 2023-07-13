package br.com.banco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaDTO {

	private Long id;
	private LocalDateTime dataTransferencia;

	@Digits(integer = 18, fraction = 2)
	private BigDecimal valor;

	private String tipo;
	private String nomeOperadorTransacao;

	@NotNull
	private Long numeroConta;
}
