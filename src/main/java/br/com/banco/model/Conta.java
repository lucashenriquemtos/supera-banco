package br.com.banco.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "conta")
public class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_conta")
	private Long idConta;

	@Column(name = "nome_responsavel", nullable = false)
	private String nomeResponsavel;
}
