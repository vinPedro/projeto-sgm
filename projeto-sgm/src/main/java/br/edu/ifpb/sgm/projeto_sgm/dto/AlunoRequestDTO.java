package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;
import java.util.Set;

@Data
public class AlunoRequestDTO extends PessoaRequestDTO{

    protected String matricula;
    private Set<Long> disciplinasPagasId;
}
