package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.AlunoDisciplinaPagaResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.AlunoDisciplinaPaga;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(
        componentModel = "spring",
        uses = {DisciplinaMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class AlunoDisciplinaPagaMapper {

    @Autowired
    @Lazy
    protected AlunoMapper alunoMapper;

    @Mapping(source = "disciplina", target = "disciplinaResponseDTO")
    @Mapping(source = "aluno", target = "alunoResponseDTO")
    public abstract AlunoDisciplinaPagaResponseDTO toResponseDTO(AlunoDisciplinaPaga entity);
}
