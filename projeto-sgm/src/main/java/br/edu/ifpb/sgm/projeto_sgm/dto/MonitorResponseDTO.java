package br.edu.ifpb.sgm.projeto_sgm.dto;


import lombok.Data;

import java.util.Set;

@Data
public class MonitorResponseDTO extends AlunoResponseDTO{

    private Long id;
    private AlunoResponseDTO alunoResponseDTO;
    private Set<DisciplinaResponseDTO> disciplinaMonitoriaResponseDTO;
}
