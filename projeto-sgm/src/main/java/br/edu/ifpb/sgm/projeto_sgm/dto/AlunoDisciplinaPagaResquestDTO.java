package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AlunoDisciplinaPagaResquestDTO {

    private Long disciplinaId;
    private Long alunoId;
    private BigDecimal nota;
}
