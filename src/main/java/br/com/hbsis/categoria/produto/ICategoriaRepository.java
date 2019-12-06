package br.com.hbsis.categoria.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query(value = "select * from categorias where cod_categoria like :code ", nativeQuery = true)
    Optional<Categoria> findByCode(@Param("code") String code);

}
