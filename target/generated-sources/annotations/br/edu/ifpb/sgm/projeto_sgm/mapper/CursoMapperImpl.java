package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.CursoRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.CursoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.DisciplinaResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.InstituicaoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Coordenador;
import br.edu.ifpb.sgm.projeto_sgm.model.Curso;
import br.edu.ifpb.sgm.projeto_sgm.model.Disciplina;
import br.edu.ifpb.sgm.projeto_sgm.model.Instituicao;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-11T14:38:32-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class CursoMapperImpl extends CursoMapper {

    @Override
    public Curso toEntity(CursoRequestDTO CursoRequestDTO) {
        if ( CursoRequestDTO == null ) {
            return null;
        }

        Curso curso = new Curso();

        curso.setNome( CursoRequestDTO.getNome() );
        curso.setDuracao( CursoRequestDTO.getDuracao() );

        return curso;
    }

    @Override
    public CursoResponseDTO toResponseDTO(Curso curso) {
        if ( curso == null ) {
            return null;
        }

        CursoResponseDTO cursoResponseDTO = new CursoResponseDTO();

        cursoResponseDTO.setInstituicaoResponseDTO( instituicaoToInstituicaoResponseDTO( curso.getInstituicao() ) );
        cursoResponseDTO.setCoordenadorResponseDTO( coordenadorToCoordenadorResponseDTO( curso.getCoordenador() ) );
        cursoResponseDTO.setDisciplinasResponseDTO( disciplinaListToDisciplinaResponseDTOList( curso.getDisciplinas() ) );
        cursoResponseDTO.setId( curso.getId() );
        cursoResponseDTO.setNome( curso.getNome() );
        cursoResponseDTO.setNivel( curso.getNivel() );
        cursoResponseDTO.setDuracao( curso.getDuracao() );

        return cursoResponseDTO;
    }

    @Override
    public void updateCursoFromDto(CursoRequestDTO dto, Curso entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getNome() != null ) {
            entity.setNome( dto.getNome() );
        }
        entity.setDuracao( dto.getDuracao() );
    }

    protected InstituicaoResponseDTO instituicaoToInstituicaoResponseDTO(Instituicao instituicao) {
        if ( instituicao == null ) {
            return null;
        }

        InstituicaoResponseDTO instituicaoResponseDTO = new InstituicaoResponseDTO();

        instituicaoResponseDTO.setId( instituicao.getId() );
        instituicaoResponseDTO.setNome( instituicao.getNome() );
        instituicaoResponseDTO.setCnpj( instituicao.getCnpj() );
        instituicaoResponseDTO.setEmail( instituicao.getEmail() );

        return instituicaoResponseDTO;
    }

    protected CoordenadorResponseDTO coordenadorToCoordenadorResponseDTO(Coordenador coordenador) {
        if ( coordenador == null ) {
            return null;
        }

        CoordenadorResponseDTO coordenadorResponseDTO = new CoordenadorResponseDTO();

        coordenadorResponseDTO.setId( coordenador.getId() );
        coordenadorResponseDTO.setCpf( coordenador.getCpf() );
        coordenadorResponseDTO.setNome( coordenador.getNome() );
        coordenadorResponseDTO.setEmail( coordenador.getEmail() );
        coordenadorResponseDTO.setEmailAcademico( coordenador.getEmailAcademico() );

        return coordenadorResponseDTO;
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
