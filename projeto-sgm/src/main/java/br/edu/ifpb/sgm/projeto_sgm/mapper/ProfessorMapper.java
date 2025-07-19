package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.ProfessorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProfessorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Professor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring",
        uses = {DisciplinaMapper.class, InstituicaoMapper.class, PessoaMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProfessorMapper {

    @Mapping(target = "pessoa", ignore = true)
    @Mapping(target = "coordenador", ignore = true)
    Professor toEntity(ProfessorRequestDTO professorRequestDTO);

    @Mapping(source = "disciplinas", target = "disciplinasResponseDTO")
    @Mapping(source = "pessoa.id", target = "id")
    @Mapping(source = "pessoa.cpf", target = "cpf")
    @Mapping(source = "pessoa.nome", target = "nome")
    @Mapping(source = "pessoa.email", target = "email")
    @Mapping(source = "pessoa.emailAcademico", target = "emailAcademico")
    @Mapping(source = "instituicao", target = "instituicaoResponseDTO")
    ProfessorResponseDTO toResponseDTO(Professor professor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfessorFromDto(ProfessorRequestDTO dto, @MappingTarget Professor entity);
}
