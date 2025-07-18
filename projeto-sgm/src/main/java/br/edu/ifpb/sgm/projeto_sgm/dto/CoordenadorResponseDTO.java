package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;

@Data
public class CoordenadorResponseDTO extends ProfessorResponseDTO{

    private CursoResponseDTO cursoResponseDTO;
}