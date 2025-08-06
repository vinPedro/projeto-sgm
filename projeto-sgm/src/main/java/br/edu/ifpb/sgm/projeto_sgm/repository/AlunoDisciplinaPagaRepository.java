package br.edu.ifpb.sgm.projeto_sgm.repository;

import br.edu.ifpb.sgm.projeto_sgm.model.AlunoDisciplinaPaga;
import br.edu.ifpb.sgm.projeto_sgm.model.embeddable.AlunoDisciplinaPagaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoDisciplinaPagaRepository extends JpaRepository<AlunoDisciplinaPaga, AlunoDisciplinaPagaId> {

    boolean existsByAlunoIdAndDisciplinaId(Long alunoId, Long disciplinaId);
}
