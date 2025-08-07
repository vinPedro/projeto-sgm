package br.edu.ifpb.sgm.projeto_sgm.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProcessoSeletivoResponseDTO {

    private Long id;
    private LocalDate inicio;
    private LocalDate fim;
    private String numero;
    private InstituicaoResponseDTO instituicaoResponseDTO;
    private boolean finalizado;

}
