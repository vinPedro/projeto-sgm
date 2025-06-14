package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.CursoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.DisciplinaRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.DisciplinaResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProfessorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Curso;
import br.edu.ifpb.sgm.projeto_sgm.model.Disciplina;
import br.edu.ifpb.sgm.projeto_sgm.model.Professor;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-14T12:54:29-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class DisciplinaMapperImpl extends DisciplinaMapper {

    @Override
    public Disciplina toEntity(DisciplinaRequestDTO disciplinaRequestDTO) {
        if ( disciplinaRequestDTO == null ) {
            return null;
        }

        Disciplina disciplina = new Disciplina();

        disciplina.setNome( disciplinaRequestDTO.getNome() );
        disciplina.setCargaHoraria( disciplinaRequestDTO.getCargaHoraria() );

        return disciplina;
    }

    @Override
    public DisciplinaResponseDTO toResponseDTO(Disciplina disciplina) {
        if ( disciplina == null ) {
            return null;
        }

        DisciplinaResponseDTO disciplinaResponseDTO = new DisciplinaResponseDTO();

        disciplinaResponseDTO.setCursoResponseDTO( cursoToCursoResponseDTO( disciplina.getCurso() ) );
        disciplinaResponseDTO.setProfessorResponseDTO( professorToProfessorResponseDTO( disciplina.getProfessor() ) );
        disciplinaResponseDTO.setId( disciplina.getId() );
        disciplinaResponseDTO.setNome( disciplina.getNome() );
        disciplinaResponseDTO.setCargaHoraria( disciplina.getCargaHoraria() );

        return disciplinaResponseDTO;
    }

    @Override
    public void updateDisciplinaFromDto(DisciplinaRequestDTO dto, Disciplina entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getNome() != null ) {
            entity.setNome( dto.getNome() );
        }
        entity.setCargaHoraria( dto.getCargaHoraria() );
    }

    protected CursoResponseDTO cursoToCursoResponseDTO(Curso curso) {
        if ( curso == null ) {
            return null;
        }

        CursoResponseDTO cursoResponseDTO = new CursoResponseDTO();

        cursoResponseDTO.setId( curso.getId() );
        cursoResponseDTO.setNome( curso.getNome() );
        cursoResponseDTO.setNivel( curso.getNivel() );
        cursoResponseDTO.setDuracao( curso.getDuracao() );

        return cursoResponseDTO;
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
}
