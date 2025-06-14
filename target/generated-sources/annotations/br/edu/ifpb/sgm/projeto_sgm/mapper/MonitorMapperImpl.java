package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.AlunoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.DisciplinaResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.MonitorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.MonitorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Aluno;
import br.edu.ifpb.sgm.projeto_sgm.model.Disciplina;
import br.edu.ifpb.sgm.projeto_sgm.model.Monitor;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-14T12:54:29-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class MonitorMapperImpl extends MonitorMapper {

    @Override
    public Monitor toEntity(MonitorRequestDTO monitorRequestDTO) {
        if ( monitorRequestDTO == null ) {
            return null;
        }

        Monitor monitor = new Monitor();

        monitor.setCpf( monitorRequestDTO.getCpf() );
        monitor.setNome( monitorRequestDTO.getNome() );
        monitor.setEmail( monitorRequestDTO.getEmail() );
        monitor.setEmailAcademico( monitorRequestDTO.getEmailAcademico() );
        monitor.setMatricula( monitorRequestDTO.getMatricula() );

        return monitor;
    }

    @Override
    public MonitorResponseDTO toResponseDTO(Monitor monitor) {
        if ( monitor == null ) {
            return null;
        }

        MonitorResponseDTO monitorResponseDTO = new MonitorResponseDTO();

        monitorResponseDTO.setAlunoResponseDTO( alunoToAlunoResponseDTO( monitor.getAluno() ) );
        monitorResponseDTO.setDisciplinaMonitoriaResponseDTO( disciplinaSetToDisciplinaResponseDTOSet( monitor.getDisciplinaMonitoria() ) );
        monitorResponseDTO.setId( monitor.getId() );
        monitorResponseDTO.setCpf( monitor.getCpf() );
        monitorResponseDTO.setNome( monitor.getNome() );
        monitorResponseDTO.setEmail( monitor.getEmail() );
        monitorResponseDTO.setEmailAcademico( monitor.getEmailAcademico() );
        monitorResponseDTO.setMatricula( monitor.getMatricula() );

        return monitorResponseDTO;
    }

    @Override
    public void updateMonitorFromDto(MonitorRequestDTO dto, Monitor entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCpf() != null ) {
            entity.setCpf( dto.getCpf() );
        }
        if ( dto.getNome() != null ) {
            entity.setNome( dto.getNome() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getEmailAcademico() != null ) {
            entity.setEmailAcademico( dto.getEmailAcademico() );
        }
        if ( dto.getMatricula() != null ) {
            entity.setMatricula( dto.getMatricula() );
        }
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

    protected DisciplinaResponseDTO disciplinaToDisciplinaResponseDTO(Disciplina disciplina) {
        if ( disciplina == null ) {
            return null;
        }

        DisciplinaResponseDTO disciplinaResponseDTO = new DisciplinaResponseDTO();

        disciplinaResponseDTO.setId( disciplina.getId() );
        disciplinaResponseDTO.setNome( disciplina.getNome() );
        disciplinaResponseDTO.setCargaHoraria( disciplina.getCargaHoraria() );

        return disciplinaResponseDTO;
    }

    protected Set<DisciplinaResponseDTO> disciplinaSetToDisciplinaResponseDTOSet(Set<Disciplina> set) {
        if ( set == null ) {
            return null;
        }

        Set<DisciplinaResponseDTO> set1 = LinkedHashSet.newLinkedHashSet( set.size() );
        for ( Disciplina disciplina : set ) {
            set1.add( disciplinaToDisciplinaResponseDTO( disciplina ) );
        }

        return set1;
    }
}
