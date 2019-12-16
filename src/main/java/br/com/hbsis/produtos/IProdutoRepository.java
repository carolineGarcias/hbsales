package br.com.hbsis.produtos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface IProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existByCodigoProduto(String codProduto);
    Map<Object, Object> findByCodigoProduto(String codProduto);
}
