package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;
import java.util.List;

@Data
public class MonitoriaResquestDTO {

    private Long disciplinaId;
    private int numeroVaga;
    private int numeroVagaBolsa;
    private int cargaHoraria;
    private Long professorId;
    private List<Long> selecionadosId;
    private List<Long> inscritosId;
    private Long processoSeletivoId;
    private List<Long> atividadesId;
}
