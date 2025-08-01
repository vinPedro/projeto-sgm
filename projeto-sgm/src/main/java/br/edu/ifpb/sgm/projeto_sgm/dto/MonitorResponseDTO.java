package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;

import java.util.Set;

@Data
public class MonitorResponseDTO {

    private AlunoResponseDTO alunoResponseDTO;
    private Set<DisciplinaResponseDTO> disciplinasMonitoriaResponseDTO;
}
