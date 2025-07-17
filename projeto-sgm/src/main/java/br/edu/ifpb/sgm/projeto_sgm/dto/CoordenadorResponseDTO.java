package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;

@Data
public class CoordenadorResponseDTO {
    private Long id;
    private String nomeProfessor;
    private String emailProfessor;
    private String nomeCurso;
}