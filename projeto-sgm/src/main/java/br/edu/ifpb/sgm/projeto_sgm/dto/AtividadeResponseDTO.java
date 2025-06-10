package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AtividadeResponseDTO {

    private Long id;
    private LocalDateTime dataHora;
    private String descricao;
    private MonitoriaResponseDTO monitoriaResponseDTO;
}
