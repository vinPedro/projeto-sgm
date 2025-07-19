package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;
import java.util.Set;

@Data
public class AlunoRequestDTO{

    protected String cpf;
    protected String nome;
    protected String email;
    protected String emailAcademico;
    protected Long instituicaoId;
    protected String matricula;
    private Set<Long> disciplinasPagasId;

}
