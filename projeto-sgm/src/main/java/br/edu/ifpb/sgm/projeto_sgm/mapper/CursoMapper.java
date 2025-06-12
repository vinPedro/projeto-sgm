package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.CursoRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.CursoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Curso;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {InstituicaoMapper.class, CoordenadorMapper.class, DisciplinaMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CursoMapper {

    Curso toEntity(CursoRequestDTO CursoRequestDTO);

    @Mapping(source = "instituicao", target = "instituicaoResponseDTO")
    @Mapping(source = "coordenador", target = "coordenadorResponseDTO")
    @Mapping(source = "disciplinas", target = "disciplinasResponseDTO")
    CursoResponseDTO toResponseDTO(Curso curso);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCursoFromDto(CursoRequestDTO dto, @MappingTarget Curso entity);
}
