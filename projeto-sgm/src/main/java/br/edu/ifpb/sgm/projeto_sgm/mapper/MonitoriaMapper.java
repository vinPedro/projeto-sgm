package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.MonitoriaRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.MonitoriaResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Monitoria;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class MonitoriaMapper {

    @Autowired
    @Lazy
    protected AtividadeMapper atividadeMapper;

    @Autowired
    @Lazy
    protected ProfessorMapper professorMapper;

    @Autowired
    @Lazy
    protected ProcessoSeletivoMapper processoSeletivoMapper;

    @Autowired
    @Lazy
    protected AlunoMapper alunoMapper;

    public abstract Monitoria toEntity(MonitoriaRequestDTO monitoriaRequestDTO);

    @Mapping(source = "professor", target = "professorResponseDTO")
    @Mapping(source = "selecionados", target = "selecionadosResponseDTO")
    @Mapping(source = "inscritos", target = "inscritosResponseDTO")
    @Mapping(source = "processoSeletivo", target = "processoSeletivoResponseDTO")
    @Mapping(source = "atividades", target = "atividadesResponseDTO")
    public abstract MonitoriaResponseDTO toResponseDTO( Monitoria monitoria);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateMonitoriaFromDto( MonitoriaRequestDTO dto, @MappingTarget  Monitoria entity);
}
