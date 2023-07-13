package br.com.banco.repository;

import br.com.banco.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
	@Query("SELECT t FROM Transferencia t " +
			"WHERE (:numeroConta IS NULL OR t.conta.idConta = :numeroConta) " +
			"AND (:dataInicial IS NULL OR t.dataTransferencia >= :dataInicial) " +
			"AND (:dataFinal IS NULL OR t.dataTransferencia <= :dataFinal) " +
			"AND (:nomeOperador IS NULL OR t.nomeOperadorTransacao = :nomeOperador)")
	List<Transferencia> findBy(Long numeroConta, LocalDateTime dataInicial, LocalDateTime dataFinal, String nomeOperador);
}
