package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProfessorRequestDTO extends PessoaRequestDTO{

    private List<Long> disciplinasId;
}
