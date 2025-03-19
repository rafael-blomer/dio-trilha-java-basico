package br.com.rafaelblomer.infrastructure.repositories;

import br.com.rafaelblomer.infrastructure.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
