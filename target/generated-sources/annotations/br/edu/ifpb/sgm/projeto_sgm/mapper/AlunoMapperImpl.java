package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.AlunoRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.AlunoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.DisciplinaResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Aluno;
import br.edu.ifpb.sgm.projeto_sgm.model.Disciplina;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-11T15:25:39-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class AlunoMapperImpl implements AlunoMapper {

    @Autowired
    private DisciplinaMapper disciplinaMapper;
    @Autowired
    private InstituicaoMapper instituicaoMapper;

    @Override
    public Aluno toEntity(AlunoRequestDTO alunoRequestDTO) {
        if ( alunoRequestDTO == null ) {
            return null;
        }

        Aluno aluno = new Aluno();

        aluno.setCpf( alunoRequestDTO.getCpf() );
        aluno.setNome( alunoRequestDTO.getNome() );
        aluno.setEmail( alunoRequestDTO.getEmail() );
        aluno.setEmailAcademico( alunoRequestDTO.getEmailAcademico() );
        aluno.setMatricula( alunoRequestDTO.getMatricula() );

        return aluno;
    }

    @Override
    public AlunoResponseDTO toResponseDTO(Aluno aluno) {
        if ( aluno == null ) {
            return null;
        }

        AlunoResponseDTO alunoResponseDTO = new AlunoResponseDTO();

        alunoResponseDTO.setDisciplinasPagasResponseDTO( disciplinaSetToDisciplinaResponseDTOSet( aluno.getDisciplinasPagas() ) );
        alunoResponseDTO.setInstituicaoResponseDTO( instituicaoMapper.toResponseDTO( aluno.getInstituicao() ) );
        alunoResponseDTO.setId( aluno.getId() );
        alunoResponseDTO.setCpf( aluno.getCpf() );
        alunoResponseDTO.setNome( aluno.getNome() );
        alunoResponseDTO.setEmail( aluno.getEmail() );
        alunoResponseDTO.setEmailAcademico( aluno.getEmailAcademico() );
        alunoResponseDTO.setMatricula( aluno.getMatricula() );

        return alunoResponseDTO;
    }

    @Override
    public void updateAlunoFromDto(AlunoRequestDTO dto, Aluno entity) {
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

    protected Set<DisciplinaResponseDTO> disciplinaSetToDisciplinaResponseDTOSet(Set<Disciplina> set) {
        if ( set == null ) {
            return null;
        }

        Set<DisciplinaResponseDTO> set1 = LinkedHashSet.newLinkedHashSet( set.size() );
        for ( Disciplina disciplina : set ) {
            set1.add( disciplinaMapper.toResponseDTO( disciplina ) );
        }

        return set1;
    }
}
