package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {

    private String token;
    private PessoaResponseDTO user;
}
