package br.edu.ifpb.sgm.projeto_sgm.repository;

import br.edu.ifpb.sgm.projeto_sgm.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    @Query(value = "SELECT * FROM disciplina WHERE professor_id IS NULL", nativeQuery = true)
    List<Disciplina> buscarDisciplinasSemProfessor();

    @Query(value = "SELECT * FROM disciplina WHERE professor_id = :professorId", nativeQuery = true)
    List<Disciplina> findByProfessorId(@Param("professorId") Long professorId);

//    List<Disciplina> findByNomeContainingIgnoreCase(String nome);
//    List<Disciplina> findByCurso_Id(Long cursoId);
//    List<Disciplina> findByProfessor_Id(Long professorId);
}
