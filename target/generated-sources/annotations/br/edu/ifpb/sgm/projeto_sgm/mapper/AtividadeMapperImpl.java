package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.AtividadeRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.AtividadeResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.MonitoriaResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Atividade;
import br.edu.ifpb.sgm.projeto_sgm.model.Monitoria;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-11T14:38:32-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class AtividadeMapperImpl extends AtividadeMapper {

    @Override
    public Atividade toEntity(AtividadeRequestDTO atividadeRequestDTO) {
        if ( atividadeRequestDTO == null ) {
            return null;
        }

        Atividade atividade = new Atividade();

        atividade.setDataHora( atividadeRequestDTO.getDataHora() );
        atividade.setDescricao( atividadeRequestDTO.getDescricao() );

        return atividade;
    }

    @Override
    public AtividadeResponseDTO toResponseDTO(Atividade atividade) {
        if ( atividade == null ) {
            return null;
        }

        AtividadeResponseDTO atividadeResponseDTO = new AtividadeResponseDTO();

        atividadeResponseDTO.setMonitoriaResponseDTO( monitoriaToMonitoriaResponseDTO( atividade.getMonitoria() ) );
        atividadeResponseDTO.setId( atividade.getId() );
        atividadeResponseDTO.setDataHora( atividade.getDataHora() );
        atividadeResponseDTO.setDescricao( atividade.getDescricao() );

        return atividadeResponseDTO;
    }

    @Override
    public void updateAtividadeFromDto(AtividadeRequestDTO dto, Atividade entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getDataHora() != null ) {
            entity.setDataHora( dto.getDataHora() );
        }
        if ( dto.getDescricao() != null ) {
            entity.setDescricao( dto.getDescricao() );
        }
    }

    protected MonitoriaResponseDTO monitoriaToMonitoriaResponseDTO(Monitoria monitoria) {
        if ( monitoria == null ) {
            return null;
        }

        MonitoriaResponseDTO monitoriaResponseDTO = new MonitoriaResponseDTO();

        monitoriaResponseDTO.setId( monitoria.getId() );
        monitoriaResponseDTO.setNumeroVaga( monitoria.getNumeroVaga() );
        monitoriaResponseDTO.setNumeroVagaBolsa( monitoria.getNumeroVagaBolsa() );
        monitoriaResponseDTO.setCargaHoraria( monitoria.getCargaHoraria() );

        return monitoriaResponseDTO;
    }
}
