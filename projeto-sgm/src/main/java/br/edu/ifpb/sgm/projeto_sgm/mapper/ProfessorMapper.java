package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.ProfessorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProfessorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Professor;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {DisciplinaMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProfessorMapper {

    Professor toEntity(ProfessorRequestDTO professorRequestDTO);

    @Mapping(source = "disciplinas", target = "disciplinasResponseDTO")
    ProfessorResponseDTO toResponseDTO(Professor professor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfessorFromDto(ProfessorRequestDTO dto, @MappingTarget Professor entity);
}
