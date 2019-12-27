package br.com.hbsis.linhas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ILinhaRepository extends JpaRepository<Linha, Long> {
}