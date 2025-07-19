package br.edu.ifpb.sgm.projeto_sgm.dto;

import br.edu.ifpb.sgm.projeto_sgm.model.Professor;
import lombok.Data;

@Data
public class CoordenadorRequestDTO{

    private Long professorId;
    private Long cursoId;
}
