package br.com.hbsis.categoria.linhas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILinhaRepository extends JpaRepository<Linha, Long> {
}