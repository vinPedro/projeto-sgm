package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;
import java.util.List;

@Data
public class InstituicaoResponseDTO {

    private Long id;
    private String nome;
    private String cnpj;
    private String email;
    private List<CursoResponseDTO> cursosResponseDTO;
    private List<ProcessoSeletivoResponseDTO> processosSeletivosResponseDTO;
}
