package br.com.hbsis.categoria.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByCodCategoria(String codCategoria);
    boolean existsCategoriaByCodCategoria(String codCategoria);
    @Override
    boolean existsById(Long id);
    //Categoria findByCodCategoria(String codCategoria);
}
