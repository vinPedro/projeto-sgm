package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AtividadeRequestDTO {

    private LocalDateTime dataHora;
    private String descricao;
    private Long monitoriaId;
    private String matricula;
}
