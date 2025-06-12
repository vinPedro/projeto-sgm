package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.AtividadeRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.AtividadeResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Atividade;
import br.edu.ifpb.sgm.projeto_sgm.model.Coordenador;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {CursoMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CoordenadorMapper {

    Coordenador toEntity(CoordenadorRequestDTO CoordenadorRequestDTO);

    @Mapping(source = "curso", target = "cursoResponseDTO")
    CoordenadorResponseDTO toResponseDTO(Coordenador coordenador);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCoordenadorFromDto(CoordenadorRequestDTO dto, @MappingTarget Coordenador entity);
}
