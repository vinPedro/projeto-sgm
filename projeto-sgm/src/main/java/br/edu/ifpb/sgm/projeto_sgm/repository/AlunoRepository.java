package br.edu.ifpb.sgm.projeto_sgm.repository;

import br.edu.ifpb.sgm.projeto_sgm.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    List<Aluno> findByCadastradoTrue();

    @Query("SELECT a FROM Aluno a WHERE SIZE(a.disciplinaMonitoria) > 0")
    List<Aluno> findAlunosComMonitoria();

    @Query("SELECT a FROM Aluno a WHERE SIZE(a.disciplinaMonitoria) = 0")
    List<Aluno> findAlunosSemMonitoria();

//    Optional<Aluno> findByMatricula(String matricula);
//    List<Aluno> findByNomeContainingIgnoreCase(String nome);
//    List<Aluno> findByDisciplinasPagas_Id(Long disciplinaId);

//    //Buscar todos alunos inscritos em uma monitoria específica
//    @Query("SELECT a FROM Monitoria m JOIN m.inscritos a WHERE m.id = :monitoriaId")
//    List<Aluno> findAlunosInscritosNaMonitoria(@Param("monitoriaId") Long monitoriaId);

}
