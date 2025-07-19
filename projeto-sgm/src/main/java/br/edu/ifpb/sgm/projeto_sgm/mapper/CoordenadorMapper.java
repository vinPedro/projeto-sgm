package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Coordenador;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring",
        uses = {CursoMapper.class, ProfessorMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CoordenadorMapper {

    @Mapping(target = "professor", ignore = true)
    Coordenador toEntity(CoordenadorRequestDTO coordenadorRequestDTO);

     // Pega "email" e coloca em "emailProfessor"
    @Mapping(source = "professor", target = "professorResponseDTO")
    @Mapping(source = "curso", target = "cursoResponseDTO") // Pega o nome de dentro do objeto curso
    @Mapping(source = "professor.id", target = "id")
    CoordenadorResponseDTO toResponseDTO(Coordenador coordenador);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCoordenadorFromDto(CoordenadorRequestDTO dto, @MappingTarget Coordenador entity);
}