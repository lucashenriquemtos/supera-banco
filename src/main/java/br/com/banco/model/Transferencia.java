package br.com.banco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;



@Entity
@Data
@Table(name = "transferencia")
@AllArgsConstructor
@NoArgsConstructor
public class Transferencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "data_transferencia", nullable = false)
	private LocalDateTime dataTransferencia;

	@Digits(integer = 18, fraction = 2)
	@Column(name = "valor", nullable = false)
	private BigDecimal valor;

	@Column(name = "tipo", nullable = false)
	private String tipo;

	@Column(name = "nome_operador_transacao")
	private String nomeOperadorTransacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conta_id", nullable = false)
	private Conta conta;
}
