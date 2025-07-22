package br.edu.ifpb.sgm.projeto_sgm.dto;

import br.edu.ifpb.sgm.projeto_sgm.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class PessoaResponseDTO {

    protected Long id;
    protected String cpf;
    protected String nome;
    protected String email;
    protected String emailAcademico;
    protected String matricula;
    protected InstituicaoResponseDTO instituicaoResponseDTO;
    protected List<Role> roles;

    public PessoaResponseDTO(Long id, String nome, String cpf, String matricula) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.matricula = matricula;
    }
}
