package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.ProcessoSeletivoRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProcessoSeletivoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.ProcessoSeletivo;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class ProcessoSeletivoMapper {

    @Autowired
    @Lazy
    protected InstituicaoMapper instituicaoMapper;

    @Autowired
    @Lazy
    protected MonitoriaMapper monitoriaMapper;

    public abstract ProcessoSeletivo toEntity(ProcessoSeletivoRequestDTO processoSeletivoRequestDTO);

    @Mapping(source = "instituicao", target = "instituicaoResponseDTO")
    @Mapping(source = "monitorias", target = "monitoriasResponseDTO")
    public abstract ProcessoSeletivoResponseDTO toResponseDTO(ProcessoSeletivo processoSeletivo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateProcessoSeletivoFromDto(ProcessoSeletivoRequestDTO dto, @MappingTarget ProcessoSeletivo entity);
}
