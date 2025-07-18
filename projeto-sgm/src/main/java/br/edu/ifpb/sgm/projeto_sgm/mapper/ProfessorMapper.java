package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.ProfessorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProfessorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Professor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class ProfessorMapper {

    @Autowired
    @Lazy
    protected DisciplinaMapper disciplinaMapper;

    @Autowired
    @Lazy
    protected InstituicaoMapper instituicaoMapper;

    public abstract Professor toEntity(ProfessorRequestDTO professorRequestDTO);

    @Mapping(source = "disciplinas", target = "disciplinasResponseDTO")
    @Mapping(source = "instituicao", target = "instituicaoResponseDTO")
    public abstract ProfessorResponseDTO toResponseDTO(Professor professor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateProfessorFromDto(ProfessorRequestDTO dto, @MappingTarget Professor entity);
}
