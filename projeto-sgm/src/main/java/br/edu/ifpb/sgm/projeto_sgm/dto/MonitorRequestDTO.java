package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;

import java.util.Set;

@Data
public class MonitorRequestDTO {

    private Long id;
    private Set<Long> disciplinasMonitoriaId;
}
