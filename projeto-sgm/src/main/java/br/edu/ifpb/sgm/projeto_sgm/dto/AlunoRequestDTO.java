package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class AlunoRequestDTO{

    protected String cpf;
    protected String nome;
    protected String email;
    protected String emailAcademico;
    private String senha;
    protected Long instituicaoId;
    protected String matricula;
    private Set<Long> disciplinasMonitoriaId;
    private BigDecimal cre;

}
