package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;

@Data
public class PessoaRequestDTO {

    protected String cpf;
    protected String nome;
    protected String email;
    protected String emailAcademico;
    protected String matricula;
    protected Long instituicaoId;
    protected String senha;


}
