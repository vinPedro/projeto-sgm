package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;

@Data
public class CoordenadorResponseDTO{

    private Long id;
    protected ProfessorResponseDTO professorResponseDTO;
    protected CursoResponseDTO cursoResponseDTO;
}