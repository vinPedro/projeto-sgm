package br.edu.ifpb.sgm.projeto_sgm.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class MonitoriaInscritoId {

    @Column(name = "monitoria_id")
    private Long monitoriaId;

    @Column(name = "aluno_id")
    private Long alunoId;

}
