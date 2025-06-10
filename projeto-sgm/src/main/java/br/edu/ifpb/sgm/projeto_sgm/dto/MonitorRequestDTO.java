package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;

import java.util.Set;

@Data
public class MonitorRequestDTO extends AlunoRequestDTO{

    private Long alunoId;
    private Set<Long> disciplinaMonitoriaId;
}
