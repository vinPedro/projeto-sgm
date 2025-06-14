package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.CursoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Coordenador;
import br.edu.ifpb.sgm.projeto_sgm.model.Curso;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-14T12:54:29-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class CoordenadorMapperImpl extends CoordenadorMapper {

    @Override
    public Coordenador toEntity(CoordenadorRequestDTO CoordenadorRequestDTO) {
        if ( CoordenadorRequestDTO == null ) {
            return null;
        }

        Coordenador coordenador = new Coordenador();

        coordenador.setCpf( CoordenadorRequestDTO.getCpf() );
        coordenador.setNome( CoordenadorRequestDTO.getNome() );
        coordenador.setEmail( CoordenadorRequestDTO.getEmail() );
        coordenador.setEmailAcademico( CoordenadorRequestDTO.getEmailAcademico() );

        return coordenador;
    }

    @Override
    public CoordenadorResponseDTO toResponseDTO(Coordenador coordenador) {
        if ( coordenador == null ) {
            return null;
        }

        CoordenadorResponseDTO coordenadorResponseDTO = new CoordenadorResponseDTO();

        coordenadorResponseDTO.setCursoResponseDTO( cursoToCursoResponseDTO( coordenador.getCurso() ) );
        coordenadorResponseDTO.setId( coordenador.getId() );
        coordenadorResponseDTO.setCpf( coordenador.getCpf() );
        coordenadorResponseDTO.setNome( coordenador.getNome() );
        coordenadorResponseDTO.setEmail( coordenador.getEmail() );
        coordenadorResponseDTO.setEmailAcademico( coordenador.getEmailAcademico() );

        return coordenadorResponseDTO;
    }

    @Override
    public void updateCoordenadorFromDto(CoordenadorRequestDTO dto, Coordenador entity) {
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
}
