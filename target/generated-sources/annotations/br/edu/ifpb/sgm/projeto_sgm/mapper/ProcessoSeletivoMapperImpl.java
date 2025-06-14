package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.InstituicaoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.MonitoriaResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProcessoSeletivoRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProcessoSeletivoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Instituicao;
import br.edu.ifpb.sgm.projeto_sgm.model.Monitoria;
import br.edu.ifpb.sgm.projeto_sgm.model.ProcessoSeletivo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-14T12:54:29-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class ProcessoSeletivoMapperImpl extends ProcessoSeletivoMapper {

    @Override
    public ProcessoSeletivo toEntity(ProcessoSeletivoRequestDTO processoSeletivoRequestDTO) {
        if ( processoSeletivoRequestDTO == null ) {
            return null;
        }

        ProcessoSeletivo processoSeletivo = new ProcessoSeletivo();

        processoSeletivo.setInicio( processoSeletivoRequestDTO.getInicio() );
        processoSeletivo.setFim( processoSeletivoRequestDTO.getFim() );
        processoSeletivo.setNumero( processoSeletivoRequestDTO.getNumero() );

        return processoSeletivo;
    }

    @Override
    public ProcessoSeletivoResponseDTO toResponseDTO(ProcessoSeletivo processoSeletivo) {
        if ( processoSeletivo == null ) {
            return null;
        }

        ProcessoSeletivoResponseDTO processoSeletivoResponseDTO = new ProcessoSeletivoResponseDTO();

        processoSeletivoResponseDTO.setInstituicaoResponseDTO( instituicaoToInstituicaoResponseDTO( processoSeletivo.getInstituicao() ) );
        processoSeletivoResponseDTO.setMonitoriasResponseDTO( monitoriaListToMonitoriaResponseDTOList( processoSeletivo.getMonitorias() ) );
        processoSeletivoResponseDTO.setId( processoSeletivo.getId() );
        processoSeletivoResponseDTO.setInicio( processoSeletivo.getInicio() );
        processoSeletivoResponseDTO.setFim( processoSeletivo.getFim() );
        processoSeletivoResponseDTO.setNumero( processoSeletivo.getNumero() );

        return processoSeletivoResponseDTO;
    }

    @Override
    public void updateProcessoSeletivoFromDto(ProcessoSeletivoRequestDTO dto, ProcessoSeletivo entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getInicio() != null ) {
            entity.setInicio( dto.getInicio() );
        }
        if ( dto.getFim() != null ) {
            entity.setFim( dto.getFim() );
        }
        if ( dto.getNumero() != null ) {
            entity.setNumero( dto.getNumero() );
        }
    }

    protected InstituicaoResponseDTO instituicaoToInstituicaoResponseDTO(Instituicao instituicao) {
        if ( instituicao == null ) {
            return null;
        }

        InstituicaoResponseDTO instituicaoResponseDTO = new InstituicaoResponseDTO();

        instituicaoResponseDTO.setId( instituicao.getId() );
        instituicaoResponseDTO.setNome( instituicao.getNome() );
        instituicaoResponseDTO.setCnpj( instituicao.getCnpj() );
        instituicaoResponseDTO.setEmail( instituicao.getEmail() );

        return instituicaoResponseDTO;
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

    protected List<MonitoriaResponseDTO> monitoriaListToMonitoriaResponseDTOList(List<Monitoria> list) {
        if ( list == null ) {
            return null;
        }

        List<MonitoriaResponseDTO> list1 = new ArrayList<MonitoriaResponseDTO>( list.size() );
        for ( Monitoria monitoria : list ) {
            list1.add( monitoriaToMonitoriaResponseDTO( monitoria ) );
        }

        return list1;
    }
}
