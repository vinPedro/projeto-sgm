package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.MonitorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.MonitorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Monitor;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {AlunoMapper.class, DisciplinaMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MonitorMapper {

    Monitor toEntity(MonitorRequestDTO monitorRequestDTO);

    @Mapping(source = "aluno", target = "alunoResponseDTO")
    @Mapping(source = "disciplinaMonitoria", target = "disciplinaMonitoriaResponseDTO")
    MonitorResponseDTO toResponseDTO(Monitor monitor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMonitorFromDto(MonitorRequestDTO dto, @MappingTarget Monitor entity);
}
