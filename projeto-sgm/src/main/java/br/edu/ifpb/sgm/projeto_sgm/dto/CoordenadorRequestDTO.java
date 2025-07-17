package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;

@Data
public class CoordenadorRequestDTO {
    // Dados para criar a nova Pessoa/Professor
    private String nome;
    private String cpf;
    private String email;
    private String emailAcademico;
    private Long instituicaoId;

    // ID do curso que ele vai coordenar
    private Long cursoId;
}
