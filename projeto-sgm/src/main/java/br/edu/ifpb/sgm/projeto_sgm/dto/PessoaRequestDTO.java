package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;

@Data
public abstract class PessoaRequestDTO {

    protected String cpf;
    protected String nome;
    protected String email;
    protected String emailAcademico;
    protected Long instituicaoId;
}
