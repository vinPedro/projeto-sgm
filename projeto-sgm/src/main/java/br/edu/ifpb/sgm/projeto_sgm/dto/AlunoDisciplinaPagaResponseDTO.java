package br.edu.ifpb.sgm.projeto_sgm.dto;


import br.edu.ifpb.sgm.projeto_sgm.model.embeddable.AlunoDisciplinaPagaId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AlunoDisciplinaPagaResponseDTO {

    private AlunoDisciplinaPagaId id;
    private DisciplinaResponseDTO disciplinaResponseDTO;
    private AlunoResponseDTO alunoResponseDTO;
    private BigDecimal nota;
}
