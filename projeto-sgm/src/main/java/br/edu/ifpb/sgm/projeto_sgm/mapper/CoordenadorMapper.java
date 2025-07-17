package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Coordenador;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class CoordenadorMapper {

    public abstract Coordenador toEntity(CoordenadorRequestDTO coordenadorRequestDTO);

    /**
     * Mapeia a entidade Coordenador para o DTO de resposta.
     * As anotações @Mapping definem as regras de "tradução".
     */
    @Mapping(source = "nome", target = "nomeProfessor") // Pega o campo "nome" da entidade e coloca em "nomeProfessor" no DTO
    @Mapping(source = "email", target = "emailProfessor") // Pega "email" e coloca em "emailProfessor"
    @Mapping(source = "curso.nome", target = "nomeCurso") // Pega o nome de dentro do objeto curso
    public abstract CoordenadorResponseDTO toResponseDTO(Coordenador coordenador);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateCoordenadorFromDto(CoordenadorRequestDTO dto, @MappingTarget Coordenador entity);
}