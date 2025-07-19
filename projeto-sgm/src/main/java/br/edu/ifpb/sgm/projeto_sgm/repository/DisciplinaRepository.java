package br.edu.ifpb.sgm.projeto_sgm.repository;

import br.edu.ifpb.sgm.projeto_sgm.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
//    List<Disciplina> findByNomeContainingIgnoreCase(String nome);
//    List<Disciplina> findByCurso_Id(Long cursoId);
//    List<Disciplina> findByProfessor_Id(Long professorId);
}
