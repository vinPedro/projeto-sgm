package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProfessorRequestDTO{

    protected String cpf;
    protected String nome;
    protected String email;
    protected String emailAcademico;
    protected Long instituicaoId;
    private List<Long> disciplinasId;
}
