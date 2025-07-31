package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CoordenadorRequestDTO {

    private Long id;
    private Set<Long> cursosId;
}
