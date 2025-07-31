package br.edu.ifpb.sgm.projeto_sgm.repository;

import br.edu.ifpb.sgm.projeto_sgm.model.Aluno;
import br.edu.ifpb.sgm.projeto_sgm.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    List<Professor> findByCadastradoTrue();

    @Query("SELECT p FROM Professor p WHERE SIZE(p.cursos) > 0")
    List<Professor> findProfessoresComCurso();

    @Query("SELECT p FROM Professor p WHERE SIZE(p.cursos) = 0")
    List<Professor> findProfessoresSemCurso();

//    List<Professor> findByNomeContainingIgnoreCase(String nome);
//    List<Professor> findByInstituicao_Id(Long instituicaoId);
}
