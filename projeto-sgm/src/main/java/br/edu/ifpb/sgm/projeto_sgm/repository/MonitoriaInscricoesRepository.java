package br.edu.ifpb.sgm.projeto_sgm.repository;

import br.edu.ifpb.sgm.projeto_sgm.model.Aluno;
import br.edu.ifpb.sgm.projeto_sgm.model.Monitoria;
import br.edu.ifpb.sgm.projeto_sgm.model.MonitoriaInscritos;
import br.edu.ifpb.sgm.projeto_sgm.model.embeddable.MonitoriaInscritoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MonitoriaInscricoesRepository extends JpaRepository<MonitoriaInscritos, MonitoriaInscritoId> {

    // Alternativamente, para retornar diretamente as monitorias
    @Query("SELECT mi.monitoria FROM MonitoriaInscritos mi WHERE mi.aluno.id = :alunoId")
    List<Monitoria> findMonitoriasByAlunoId(Long alunoId);

    @Query("SELECT mi.aluno FROM MonitoriaInscritos mi WHERE mi.monitoria.id = :monitoriaId")
    List<Aluno> buscarAlunosPorMonitoria(@Param("monitoriaId") Long monitoriaId);


}
