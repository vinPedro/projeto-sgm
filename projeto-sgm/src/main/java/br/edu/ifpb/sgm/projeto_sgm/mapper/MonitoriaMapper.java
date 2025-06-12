package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.MonitoriaRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.MonitoriaResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Monitoria;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {ProfessorMapper.class, AlunoMapper.class, ProcessoSeletivoMapper.class, AtividadeMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MonitoriaMapper {

    Monitoria toEntity(MonitoriaRequestDTO monitoriaRequestDTO);

    @Mapping(source = "professor", target = "professorResponseDTO")
    @Mapping(source = "selecionados", target = "selecionadosResponseDTO")
    @Mapping(source = "inscritos", target = "inscritosResponseDTO")
    @Mapping(source = "processoSeletivo", target = "processoSeletivoResponseDTO")
    @Mapping(source = "atividades", target = "atividadesResponseDTO")
    MonitoriaResponseDTO toResponseDTO( Monitoria monitoria);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMonitoriaFromDto( MonitoriaRequestDTO dto, @MappingTarget  Monitoria entity);
}
