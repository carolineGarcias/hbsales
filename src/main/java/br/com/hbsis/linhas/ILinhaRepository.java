package br.com.hbsis.linhas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILinhaRepository extends JpaRepository<Linha, Long> {
    Optional<Linha> findById(Long id);

    @Override
    boolean existsById(Long id); //true or false

}