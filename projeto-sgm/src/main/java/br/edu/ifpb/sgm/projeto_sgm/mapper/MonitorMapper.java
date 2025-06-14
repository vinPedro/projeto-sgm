package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.MonitorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.MonitorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Monitor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class MonitorMapper {

    @Autowired
    @Lazy
    protected AlunoMapper alunoMapper;

    @Autowired
    @Lazy
    protected DisciplinaMapper disciplinaMapper;

    public abstract Monitor toEntity(MonitorRequestDTO monitorRequestDTO);

    @Mapping(source = "aluno", target = "alunoResponseDTO")
    @Mapping(source = "disciplinaMonitoria", target = "disciplinaMonitoriaResponseDTO")
    public abstract MonitorResponseDTO toResponseDTO(Monitor monitor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateMonitorFromDto(MonitorRequestDTO dto, @MappingTarget Monitor entity);
}
