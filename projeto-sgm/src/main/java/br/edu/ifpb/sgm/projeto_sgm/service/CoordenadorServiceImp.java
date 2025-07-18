package br.edu.ifpb.sgm.projeto_sgm.service;

import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.exception.CursoNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.CoordenadorNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.DisciplinaNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.InstituicaoNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.mapper.CoordenadorMapper;
import br.edu.ifpb.sgm.projeto_sgm.model.Coordenador;
import br.edu.ifpb.sgm.projeto_sgm.model.Curso;
import br.edu.ifpb.sgm.projeto_sgm.model.Disciplina;
import br.edu.ifpb.sgm.projeto_sgm.model.Instituicao;
import br.edu.ifpb.sgm.projeto_sgm.repository.CoordenadorRepository;
import br.edu.ifpb.sgm.projeto_sgm.repository.CursoRepository;
import br.edu.ifpb.sgm.projeto_sgm.repository.DisciplinaRepository;
import br.edu.ifpb.sgm.projeto_sgm.repository.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CoordenadorServiceImp {

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private CoordenadorMapper coordenadorMapper;

    public ResponseEntity<CoordenadorResponseDTO> salvar(CoordenadorRequestDTO dto) {
        Coordenador coordenador = coordenadorMapper.toEntity(dto);
        coordenador.setCurso(buscarCurso(dto.getCursoId()));
        coordenador.setInstituicao(buscarInstituicao(dto.getInstituicaoId()));
        coordenador.setDisciplinas(buscarDisciplinas(dto.getDisciplinasId()));
        Coordenador salvo = coordenadorRepository.save(coordenador);
        return ResponseEntity.status(HttpStatus.CREATED).body(coordenadorMapper.toResponseDTO(salvo));
    }

    public ResponseEntity<CoordenadorResponseDTO> buscarPorId(Long id) {
        Coordenador coordenador = coordenadorRepository.findById(id)
                .orElseThrow(CoordenadorNotFoundException::new);
        return ResponseEntity.ok(coordenadorMapper.toResponseDTO(coordenador));
    }

    public ResponseEntity<List<CoordenadorResponseDTO>> listarTodos() {
        List<Coordenador> coordenadores = coordenadorRepository.findAll();
        List<CoordenadorResponseDTO> dtos = coordenadores.stream()
                .map(coordenadorMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    public ResponseEntity<CoordenadorResponseDTO> atualizar(Long id, CoordenadorRequestDTO dto) {
        Coordenador coordenador = coordenadorRepository.findById(id)
                .orElseThrow(CoordenadorNotFoundException::new);

        coordenadorMapper.updateCoordenadorFromDto(dto, coordenador);

        if (dto.getCursoId() != null) {
            coordenador.setCurso(buscarCurso(dto.getCursoId()));
        }

        if (dto.getDisciplinasId() != null) {
            coordenador.setDisciplinas(buscarDisciplinas(dto.getDisciplinasId()));
        }

        if (dto.getInstituicaoId() != null) {
            coordenador.setInstituicao(buscarInstituicao(dto.getInstituicaoId()));
        }

        Coordenador atualizado = coordenadorRepository.save(coordenador);
        return ResponseEntity.ok(coordenadorMapper.toResponseDTO(atualizado));
    }

    public ResponseEntity<Void> deletar(Long id) {
        Coordenador coordenador = coordenadorRepository.findById(id)
                .orElseThrow(CoordenadorNotFoundException::new);

        Curso curso = cursoRepository.findById(coordenador.getCurso().getId())
                .orElseThrow(CursoNotFoundException::new);

        curso.setCoordenador(null);

        coordenadorRepository.delete(coordenador);
        return ResponseEntity.noContent().build();
    }

    private Curso buscarCurso(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new CursoNotFoundException("Curso com ID " + id + " não encontrado."));
    }


    // Auxiliar
    private List<Disciplina> buscarDisciplinas(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptyList();

        List<Long> idsNaoEncontrados = ids.stream()
                .filter(id -> disciplinaRepository.findById(id).isEmpty())
                .toList();

        if (!idsNaoEncontrados.isEmpty()) {
            throw new DisciplinaNotFoundException("IDs de disciplinas inválidos: " + idsNaoEncontrados);
        }

        return ids.stream()
                .map(id -> disciplinaRepository.findById(id).get())
                .collect(Collectors.toList());
    }

    private Instituicao buscarInstituicao(Long id) {
        return instituicaoRepository.findById(id)
                .orElseThrow(() -> new InstituicaoNotFoundException("Instituição com ID " + id + " não encontrada."));
    }

}