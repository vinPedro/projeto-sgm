package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.AtividadeRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.AtividadeResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Atividade;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class AtividadeMapper {

    @Autowired
    @Lazy
    protected MonitoriaMapper monitoriaMapper;

    public abstract Atividade toEntity(AtividadeRequestDTO atividadeRequestDTO);

    @Mapping(source = "monitoria", target = "monitoriaResponseDTO")
    public abstract AtividadeResponseDTO toResponseDTO(Atividade atividade);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateAtividadeFromDto(AtividadeRequestDTO dto, @MappingTarget Atividade entity);
}
