package br.com.hbsis.vendas;

import br.com.hbsis.fornecedor.Fornecedor;

import java.util.List;
import java.util.Optional;

public interface IVendasRepository {

    Optional<Vendas> findByFornecedorId(Long id);
    Optional<Vendas> findById(Long id);
    void deleteById(Long id);
    void save(Vendas vendasExistente);
}
