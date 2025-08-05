package br.edu.ifpb.sgm.projeto_sgm.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
public class AlunoDisciplinaPagaId {

    @Column(name = "aluno_id")
    private Long alunoId;

    @Column(name = "disciplina_id")
    private Long disciplinaId;
}
