package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class AlunoResponseDTO{

    protected Long id;
    protected String cpf;
    protected String nome;
    protected String email;
    protected String emailAcademico;
    protected InstituicaoResponseDTO instituicaoResponseDTO;
    protected String matricula;
    private Set<AlunoDisciplinaPagaResponseDTO> alunoDisciplinaPagaResponseDTO;
    private Set<DisciplinaResponseDTO> disciplinasMonitoriaResponseDTO;
    private BigDecimal cre;

}
