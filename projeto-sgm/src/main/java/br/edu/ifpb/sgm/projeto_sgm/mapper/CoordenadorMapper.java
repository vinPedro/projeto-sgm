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
    protected DisciplinaMapper disciplinaMapper;

    @Autowired
    @Lazy
    protected InstituicaoMapper instituicaoMapper;

    @Autowired
    @Lazy
    protected CursoMapper cursoMapper;

    public abstract Coordenador toEntity(CoordenadorRequestDTO coordenadorRequestDTO);

     // Pega "email" e coloca em "emailProfessor"
     @Mapping(source = "disciplinas", target = "disciplinasResponseDTO")
     @Mapping(source = "instituicao", target = "instituicaoResponseDTO")
    @Mapping(source = "curso", target = "cursoResponseDTO") // Pega o nome de dentro do objeto curso
    public abstract CoordenadorResponseDTO toResponseDTO(Coordenador coordenador);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateCoordenadorFromDto(CoordenadorRequestDTO dto, @MappingTarget Coordenador entity);
}