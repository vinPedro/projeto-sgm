package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Coordenador;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class CoordenadorMapper {

    @Autowired
    @Lazy
    protected CursoMapper cursoMapper;

    public abstract Coordenador toEntity(CoordenadorRequestDTO CoordenadorRequestDTO);

    @Mapping(source = "curso", target = "cursoResponseDTO")
    public abstract CoordenadorResponseDTO toResponseDTO(Coordenador coordenador);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateCoordenadorFromDto(CoordenadorRequestDTO dto, @MappingTarget Coordenador entity);
}
