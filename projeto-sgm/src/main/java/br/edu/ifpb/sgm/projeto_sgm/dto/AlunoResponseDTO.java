package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;
import java.util.Set;

@Data
public class AlunoResponseDTO extends PessoaResponseDTO{

    protected String matricula;
    private Set<DisciplinaResponseDTO> disciplinasPagasResponseDTO;
}
