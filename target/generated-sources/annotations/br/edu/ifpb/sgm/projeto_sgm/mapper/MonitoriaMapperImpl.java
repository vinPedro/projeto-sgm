package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.AlunoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.AtividadeResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.MonitoriaRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.MonitoriaResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProcessoSeletivoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProfessorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Aluno;
import br.edu.ifpb.sgm.projeto_sgm.model.Atividade;
import br.edu.ifpb.sgm.projeto_sgm.model.Monitoria;
import br.edu.ifpb.sgm.projeto_sgm.model.ProcessoSeletivo;
import br.edu.ifpb.sgm.projeto_sgm.model.Professor;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-14T12:54:29-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class MonitoriaMapperImpl extends MonitoriaMapper {

    @Override
    public Monitoria toEntity(MonitoriaRequestDTO monitoriaRequestDTO) {
        if ( monitoriaRequestDTO == null ) {
            return null;
        }

        Monitoria monitoria = new Monitoria();

        monitoria.setNumeroVaga( monitoriaRequestDTO.getNumeroVaga() );
        monitoria.setNumeroVagaBolsa( monitoriaRequestDTO.getNumeroVagaBolsa() );
        monitoria.setCargaHoraria( monitoriaRequestDTO.getCargaHoraria() );

        return monitoria;
    }

    @Override
    public MonitoriaResponseDTO toResponseDTO(Monitoria monitoria) {
        if ( monitoria == null ) {
            return null;
        }

        MonitoriaResponseDTO monitoriaResponseDTO = new MonitoriaResponseDTO();

        monitoriaResponseDTO.setProfessorResponseDTO( professorToProfessorResponseDTO( monitoria.getProfessor() ) );
        monitoriaResponseDTO.setSelecionadosResponseDTO( alunoListToAlunoResponseDTOList( monitoria.getSelecionados() ) );
        monitoriaResponseDTO.setInscritosResponseDTO( alunoListToAlunoResponseDTOList( monitoria.getInscritos() ) );
        monitoriaResponseDTO.setProcessoSeletivoResponseDTO( processoSeletivoToProcessoSeletivoResponseDTO( monitoria.getProcessoSeletivo() ) );
        monitoriaResponseDTO.setAtividadesResponseDTO( atividadeListToAtividadeResponseDTOList( monitoria.getAtividades() ) );
        monitoriaResponseDTO.setId( monitoria.getId() );
        monitoriaResponseDTO.setNumeroVaga( monitoria.getNumeroVaga() );
        monitoriaResponseDTO.setNumeroVagaBolsa( monitoria.getNumeroVagaBolsa() );
        monitoriaResponseDTO.setCargaHoraria( monitoria.getCargaHoraria() );

        return monitoriaResponseDTO;
    }

    @Override
    public void updateMonitoriaFromDto(MonitoriaRequestDTO dto, Monitoria entity) {
        if ( dto == null ) {
            return;
        }

        entity.setNumeroVaga( dto.getNumeroVaga() );
        entity.setNumeroVagaBolsa( dto.getNumeroVagaBolsa() );
        entity.setCargaHoraria( dto.getCargaHoraria() );
    }

    protected ProfessorResponseDTO professorToProfessorResponseDTO(Professor professor) {
        if ( professor == null ) {
            return null;
        }

        ProfessorResponseDTO professorResponseDTO = new ProfessorResponseDTO();

        professorResponseDTO.setId( professor.getId() );
        professorResponseDTO.setCpf( professor.getCpf() );
        professorResponseDTO.setNome( professor.getNome() );
        professorResponseDTO.setEmail( professor.getEmail() );
        professorResponseDTO.setEmailAcademico( professor.getEmailAcademico() );

        return professorResponseDTO;
    }

    protected AlunoResponseDTO alunoToAlunoResponseDTO(Aluno aluno) {
        if ( aluno == null ) {
            return null;
        }

        AlunoResponseDTO alunoResponseDTO = new AlunoResponseDTO();

        alunoResponseDTO.setId( aluno.getId() );
        alunoResponseDTO.setCpf( aluno.getCpf() );
        alunoResponseDTO.setNome( aluno.getNome() );
        alunoResponseDTO.setEmail( aluno.getEmail() );
        alunoResponseDTO.setEmailAcademico( aluno.getEmailAcademico() );
        alunoResponseDTO.setMatricula( aluno.getMatricula() );

        return alunoResponseDTO;
    }

    protected List<AlunoResponseDTO> alunoListToAlunoResponseDTOList(List<Aluno> list) {
        if ( list == null ) {
            return null;
        }

        List<AlunoResponseDTO> list1 = new ArrayList<AlunoResponseDTO>( list.size() );
        for ( Aluno aluno : list ) {
            list1.add( alunoToAlunoResponseDTO( aluno ) );
        }

        return list1;
    }

    protected ProcessoSeletivoResponseDTO processoSeletivoToProcessoSeletivoResponseDTO(ProcessoSeletivo processoSeletivo) {
        if ( processoSeletivo == null ) {
            return null;
        }

        ProcessoSeletivoResponseDTO processoSeletivoResponseDTO = new ProcessoSeletivoResponseDTO();

        processoSeletivoResponseDTO.setId( processoSeletivo.getId() );
        processoSeletivoResponseDTO.setInicio( processoSeletivo.getInicio() );
        processoSeletivoResponseDTO.setFim( processoSeletivo.getFim() );
        processoSeletivoResponseDTO.setNumero( processoSeletivo.getNumero() );

        return processoSeletivoResponseDTO;
    }

    protected AtividadeResponseDTO atividadeToAtividadeResponseDTO(Atividade atividade) {
        if ( atividade == null ) {
            return null;
        }

        AtividadeResponseDTO atividadeResponseDTO = new AtividadeResponseDTO();

        atividadeResponseDTO.setId( atividade.getId() );
        atividadeResponseDTO.setDataHora( atividade.getDataHora() );
        atividadeResponseDTO.setDescricao( atividade.getDescricao() );

        return atividadeResponseDTO;
    }

    protected List<AtividadeResponseDTO> atividadeListToAtividadeResponseDTOList(List<Atividade> list) {
        if ( list == null ) {
            return null;
        }

        List<AtividadeResponseDTO> list1 = new ArrayList<AtividadeResponseDTO>( list.size() );
        for ( Atividade atividade : list ) {
            list1.add( atividadeToAtividadeResponseDTO( atividade ) );
        }

        return list1;
    }
}
