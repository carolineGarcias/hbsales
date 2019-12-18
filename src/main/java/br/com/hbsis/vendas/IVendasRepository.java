package br.com.hbsis.vendas;

import br.com.hbsis.fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface IVendasRepository extends JpaRepository<Vendas, Long> {

    @Query(value = "SELECT * FROM dbo.seg_vendas WHERE id_vendas_fornecedor=:idFornecedor ",nativeQuery = true)

    Optional<Vendas> findById(Long id);

}
