package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.InstituicaoRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.InstituicaoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Curso;
import br.edu.ifpb.sgm.projeto_sgm.model.Instituicao;
import br.edu.ifpb.sgm.projeto_sgm.model.Professor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class InstituicaoMapper {

    @Autowired
    @Lazy
    protected CursoMapper cursoMapper;

    @Autowired
    @Lazy
    protected ProcessoSeletivoMapper processoSeletivoMapper;

    public abstract Instituicao toEntity(InstituicaoRequestDTO instituicaoRequestDTO);

    @Mapping(source = "cursos", target = "cursosResponseDTO")
    @Mapping(source = "processos", target = "processosSeletivosResponseDTO")
    public abstract InstituicaoResponseDTO toResponseDTO(Instituicao instituicao);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateInstituicaoFromDto(InstituicaoRequestDTO dto, @MappingTarget Instituicao entity);
}
