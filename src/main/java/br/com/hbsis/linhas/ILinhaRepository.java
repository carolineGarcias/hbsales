package br.com.hbsis.linhas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILinhaRepository extends JpaRepository<Linha, Long> {

    Optional<Linha> findById(Long idLinha);

    @Override
    boolean existsById(Long idLinha); //true or false
    boolean existsByCodLinha(String codLinha);
    Linha findByCodLinha(String codLinha);
}