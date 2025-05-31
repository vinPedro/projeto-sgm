package br.edu.ifpb.sgm.projeto_sgm.repository;

import br.edu.ifpb.sgm.projeto_sgm.model.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MonitorRepository extends JpaRepository<Monitor, Long> {
    List<Monitor> findByDisciplinaMonitoria_Id(Long disciplinaId);
    List<Monitor> findByNomeContainingIgnoreCase(String nome);

    //Buscar monitores por disciplina e instituição
    @Query("SELECT mo FROM Monitor mo JOIN mo.disciplinaMonitoria d WHERE d.id = :disciplinaId AND mo.instituicao.id = :instituicaoId")
    List<Monitor> findMonitoresByDisciplinaAndInstituicao(@Param("disciplinaId") Long disciplinaId, @Param("instituicaoId") Long instituicaoId);
}
