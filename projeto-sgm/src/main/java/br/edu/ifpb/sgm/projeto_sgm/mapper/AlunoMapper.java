package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.AlunoRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.AlunoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Aluno;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {DisciplinaMapper.class, InstituicaoMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AlunoMapper {

    Aluno toEntity(AlunoRequestDTO alunoRequestDTO);

    @Mapping(source = "disciplinasPagas", target = "disciplinasPagasResponseDTO")
    @Mapping(source = "instituicao", target = "instituicaoResponseDTO")
    AlunoResponseDTO toResponseDTO(Aluno aluno);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAlunoFromDto(AlunoRequestDTO dto, @MappingTarget Aluno entity);
}
