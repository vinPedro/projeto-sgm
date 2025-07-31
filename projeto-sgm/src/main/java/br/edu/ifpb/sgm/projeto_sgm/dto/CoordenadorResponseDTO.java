package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;
import java.util.Set;

@Data
public class CoordenadorResponseDTO {

    private ProfessorResponseDTO professorResponseDTO;
    private Set<CursoResponseDTO> cursosResponseDTO;
}
