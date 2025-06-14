package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.CursoRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.CursoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Curso;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class CursoMapper {

    @Autowired
    @Lazy
    protected InstituicaoMapper instituicaoMapper;

    @Autowired
    @Lazy
    protected CoordenadorMapper coordenadorMapper;

    @Autowired
    @Lazy
    protected DisciplinaMapper disciplinaMapper;

    public abstract Curso toEntity(CursoRequestDTO CursoRequestDTO);

    @Mapping(source = "instituicao", target = "instituicaoResponseDTO")
    @Mapping(source = "coordenador", target = "coordenadorResponseDTO")
    @Mapping(source = "disciplinas", target = "disciplinasResponseDTO")
    public abstract CursoResponseDTO toResponseDTO(Curso curso);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateCursoFromDto(CursoRequestDTO dto, @MappingTarget Curso entity);
}
