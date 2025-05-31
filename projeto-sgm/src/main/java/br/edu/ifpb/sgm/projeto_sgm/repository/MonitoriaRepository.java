package br.edu.ifpb.sgm.projeto_sgm.repository;

import br.edu.ifpb.sgm.projeto_sgm.model.Monitoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitoriaRepository extends JpaRepository<Monitoria, Long> {
    List<Monitoria> findByProfessor_Id(Long professorId);
    List<Monitoria> findByProcessoSeletivo_Id(Long processoId);
    List<Monitoria> findBySelecionados_Id(Long alunoId);
    List<Monitoria> findByInscritos_Id(Long alunoId);
}
