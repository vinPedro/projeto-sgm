package br.edu.ifpb.sgm.projeto_sgm.repository;

import br.edu.ifpb.sgm.projeto_sgm.model.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitorRepository extends JpaRepository<Monitor, Long> {
    List<Monitor> findByDisciplinaMonitoria_Id(Long disciplinaId);
    List<Monitor> findByNomeContainingIgnoreCase(String nome);
}
