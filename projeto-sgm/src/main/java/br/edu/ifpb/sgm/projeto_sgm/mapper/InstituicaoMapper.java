package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.InstituicaoRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.InstituicaoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Instituicao;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {CursoMapper.class, ProcessoSeletivoMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface InstituicaoMapper {

    Instituicao toEntity(InstituicaoRequestDTO instituicaoRequestDTO);

    @Mapping(source = "cursos", target = "cursosResponseDTO")
    @Mapping(source = "processos", target = "processosSeletivosResponseDTO")
    InstituicaoResponseDTO toResponseDTO(Instituicao instituicao);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInstituicaoFromDto(InstituicaoRequestDTO dto, @MappingTarget Instituicao entity);
}
