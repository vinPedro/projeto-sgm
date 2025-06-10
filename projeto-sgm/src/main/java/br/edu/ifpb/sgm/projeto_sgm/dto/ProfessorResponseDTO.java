package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProfessorResponseDTO extends PessoaResponseDTO{

    private List<DisciplinaResponseDTO> disciplinasResponseDTO;
}
