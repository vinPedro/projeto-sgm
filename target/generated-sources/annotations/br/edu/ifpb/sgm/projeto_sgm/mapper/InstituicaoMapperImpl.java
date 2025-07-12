package br.edu.ifpb.sgm.projeto_sgm.mapper;

import br.edu.ifpb.sgm.projeto_sgm.dto.CursoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.InstituicaoRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.InstituicaoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProcessoSeletivoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.Curso;
import br.edu.ifpb.sgm.projeto_sgm.model.Instituicao;
import br.edu.ifpb.sgm.projeto_sgm.model.ProcessoSeletivo;
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
public class InstituicaoMapperImpl extends InstituicaoMapper {

    @Override
    public Instituicao toEntity(InstituicaoRequestDTO instituicaoRequestDTO) {
        if ( instituicaoRequestDTO == null ) {
            return null;
        }

        Instituicao instituicao = new Instituicao();

        instituicao.setNome( instituicaoRequestDTO.getNome() );
        instituicao.setCnpj( instituicaoRequestDTO.getCnpj() );
        instituicao.setEmail( instituicaoRequestDTO.getEmail() );

        return instituicao;
    }

    @Override
    public InstituicaoResponseDTO toResponseDTO(Instituicao instituicao) {
        if ( instituicao == null ) {
            return null;
        }

        InstituicaoResponseDTO instituicaoResponseDTO = new InstituicaoResponseDTO();

        instituicaoResponseDTO.setCursosResponseDTO( cursoListToCursoResponseDTOList( instituicao.getCursos() ) );
        instituicaoResponseDTO.setProcessosSeletivosResponseDTO( processoSeletivoListToProcessoSeletivoResponseDTOList( instituicao.getProcessos() ) );
        instituicaoResponseDTO.setId( instituicao.getId() );
        instituicaoResponseDTO.setNome( instituicao.getNome() );
        instituicaoResponseDTO.setCnpj( instituicao.getCnpj() );
        instituicaoResponseDTO.setEmail( instituicao.getEmail() );

        return instituicaoResponseDTO;
    }

    @Override
    public void updateInstituicaoFromDto(InstituicaoRequestDTO dto, Instituicao entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getNome() != null ) {
            entity.setNome( dto.getNome() );
        }
        if ( dto.getCnpj() != null ) {
            entity.setCnpj( dto.getCnpj() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
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

    protected List<CursoResponseDTO> cursoListToCursoResponseDTOList(List<Curso> list) {
        if ( list == null ) {
            return null;
        }

        List<CursoResponseDTO> list1 = new ArrayList<CursoResponseDTO>( list.size() );
        for ( Curso curso : list ) {
            list1.add( cursoToCursoResponseDTO( curso ) );
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

    protected List<ProcessoSeletivoResponseDTO> processoSeletivoListToProcessoSeletivoResponseDTOList(List<ProcessoSeletivo> list) {
        if ( list == null ) {
            return null;
        }

        List<ProcessoSeletivoResponseDTO> list1 = new ArrayList<ProcessoSeletivoResponseDTO>( list.size() );
        for ( ProcessoSeletivo processoSeletivo : list ) {
            list1.add( processoSeletivoToProcessoSeletivoResponseDTO( processoSeletivo ) );
        }

        return list1;
    }
}
