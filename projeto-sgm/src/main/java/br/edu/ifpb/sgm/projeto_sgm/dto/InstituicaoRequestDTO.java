package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;

import java.util.List;

@Data
public class InstituicaoRequestDTO {

    private String nome;
    private String cnpj;
    private String email;
    private List<Long> cursosId;
    private List<Long> processosId;
}
