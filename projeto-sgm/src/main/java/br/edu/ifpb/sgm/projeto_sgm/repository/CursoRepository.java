package br.edu.ifpb.sgm.projeto_sgm.repository;

import br.edu.ifpb.sgm.projeto_sgm.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query(value = """
                SELECT * FROM curso c
                WHERE c.id NOT IN (
                    SELECT curso_id FROM coordenador_curso
                )
            """, nativeQuery = true)
    List<Curso> findCursosSemProfessor();


//    List<Curso> findByNomeContainingIgnoreCase(String nome);
//    List<Curso> findByInstituicao_Id(Long instituicaoId);
}
