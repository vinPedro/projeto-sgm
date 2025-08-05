package br.edu.ifpb.sgm.projeto_sgm.model;

import br.edu.ifpb.sgm.projeto_sgm.model.embeddable.AlunoDisciplinaPagaId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "aluno_disciplinas_pagas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDisciplinaPaga {

    @EmbeddedId
    private AlunoDisciplinaPagaId id = new AlunoDisciplinaPagaId();

    @ManyToOne
    @MapsId("alunoId")
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @MapsId("disciplinaId")
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @Column(nullable = false)
    private BigDecimal nota;
}
