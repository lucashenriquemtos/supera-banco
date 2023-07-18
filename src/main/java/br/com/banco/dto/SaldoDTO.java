package br.com.banco.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class SaldoDTO {
	private BigDecimal saldoTotal;
	private BigDecimal saldoTotalNoPeriodo;
}
