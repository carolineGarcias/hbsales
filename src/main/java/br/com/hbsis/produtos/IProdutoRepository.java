package br.com.hbsis.produtos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProdutoRepository extends JpaRepository<Produto, Long> {

  /*boolean existByCodigoProduto(String codProduto); //true or false
    Optional<Produto> findByCodigoProduto(String codProduto);*/
}
