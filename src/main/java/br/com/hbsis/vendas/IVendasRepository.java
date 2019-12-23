package br.com.hbsis.vendas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;

public interface IVendasRepository extends JpaRepository<Vendas, Long> {

    @Query(value = "select count(1) from seg_vendas where fim_vendas >= :inicioVendas and id_fornecedor = :fornecedorId", nativeQuery = true)
    long existVendasHoje(
            @Param("inicioVendas") LocalDate inicioVendas,
            @Param("fornecedorId") long fornecedorId);

}