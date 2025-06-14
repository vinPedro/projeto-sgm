package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.DisciplinaResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProfessorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProfessorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Disciplina;
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
public class ProfessorMapperImpl extends ProfessorMapper {

    @Override
    public Professor toEntity(ProfessorRequestDTO professorRequestDTO) {
        if ( professorRequestDTO == null ) {
            return null;
        }

        Professor professor = new Professor();

        professor.setCpf( professorRequestDTO.getCpf() );
        professor.setNome( professorRequestDTO.getNome() );
        professor.setEmail( professorRequestDTO.getEmail() );
        professor.setEmailAcademico( professorRequestDTO.getEmailAcademico() );

        return professor;
    }

    @Override
    public ProfessorResponseDTO toResponseDTO(Professor professor) {
        if ( professor == null ) {
            return null;
        }

        ProfessorResponseDTO professorResponseDTO = new ProfessorResponseDTO();

        professorResponseDTO.setDisciplinasResponseDTO( disciplinaListToDisciplinaResponseDTOList( professor.getDisciplinas() ) );
        professorResponseDTO.setId( professor.getId() );
        professorResponseDTO.setCpf( professor.getCpf() );
        professorResponseDTO.setNome( professor.getNome() );
        professorResponseDTO.setEmail( professor.getEmail() );
        professorResponseDTO.setEmailAcademico( professor.getEmailAcademico() );

        return professorResponseDTO;
    }

    @Override
    public void updateProfessorFromDto(ProfessorRequestDTO dto, Professor entity) {
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

    protected List<DisciplinaResponseDTO> disciplinaListToDisciplinaResponseDTOList(List<Disciplina> list) {
        if ( list == null ) {
            return null;
        }

        List<DisciplinaResponseDTO> list1 = new ArrayList<DisciplinaResponseDTO>( list.size() );
        for ( Disciplina disciplina : list ) {
            list1.add( disciplinaToDisciplinaResponseDTO( disciplina ) );
        }

        return list1;
    }
}
