package br.edu.ifpb.sgm.projeto_sgm.repository;

import br.edu.ifpb.sgm.projeto_sgm.model.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
//    List<Atividade> findByMonitoria_Id(Long monitoriaId);
//
//    //Buscar atividades de um aluno selecionado em uma monitoria
//    @Query("SELECT a FROM Atividade a WHERE a.monitoria.id = :monitoriaId AND :alunoId MEMBER OF a.monitoria.selecionados")
//    List<Atividade> findAtividadesDeAlunoEmMonitoria(@Param("alunoId") Long alunoId, @Param("monitoriaId") Long monitoriaId);
}
