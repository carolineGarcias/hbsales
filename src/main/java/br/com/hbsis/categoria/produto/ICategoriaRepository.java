package br.com.hbsis.categoria.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    @Override
    boolean existsById(Long id);

    Categoria findByCodCategoria(String codCategoria);

    //boolean existsCategoriaByFornecedorId(Long idFornecedor);
    boolean existsCategoriaByCodCategoria(String codCategoria);
}
