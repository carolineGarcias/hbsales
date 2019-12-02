package br.com.hbsis.categoria.produtos;

import br.com.hbsis.categoria.produtos.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByCodigoProduto(Long codigoProd);

    Optional<Produto> findByCodigoProduto(Long codigoProd);

}
