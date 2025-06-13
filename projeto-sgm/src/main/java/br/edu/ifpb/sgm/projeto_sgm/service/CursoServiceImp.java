package br.edu.ifpb.sgm.projeto_sgm.service;

import br.edu.ifpb.sgm.projeto_sgm.dto.CursoRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.CursoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.exception.*;
import br.edu.ifpb.sgm.projeto_sgm.mapper.CursoMapper;
import br.edu.ifpb.sgm.projeto_sgm.model.*;
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
public class CursoServiceImp {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private CursoMapper cursoMapper;



    public ResponseEntity<CursoResponseDTO> salvar(CursoRequestDTO dto) {
        Curso curso = cursoMapper.toEntity(dto);

        curso.setInstituicao(buscarInstituicao(dto.getInstituicaoId()));
        curso.setCoordenador(buscarCoordenador(dto.getCoordenadorID()));
        curso.setDisciplinas(buscarDisciplinas(dto.getDisciplinasId()));
        curso.setNivel(NivelCurso.valueOf(dto.getNivelString()));

        Curso salvo = cursoRepository.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoMapper.toResponseDTO(salvo));
    }

    public ResponseEntity<CursoResponseDTO> buscarPorId(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(CursoNotFoundException::new);
        return ResponseEntity.ok(cursoMapper.toResponseDTO(curso));
    }


    public ResponseEntity<List<CursoResponseDTO>> listarTodos() {
        List<Curso> cursos = cursoRepository.findAll();
        List<CursoResponseDTO> dtos = cursos.stream()
                .map(cursoMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }


    public ResponseEntity<CursoResponseDTO> atualizar(Long id, CursoRequestDTO dto) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(CursoNotFoundException::new);

        cursoMapper.updateCursoFromDto(dto, curso);

        if (dto.getInstituicaoId() != null) {
            curso.setInstituicao(buscarInstituicao(dto.getInstituicaoId()));
        }

        if (dto.getCoordenadorID() != null) {
            curso.setCoordenador(buscarCoordenador(dto.getCoordenadorID()));
        }

        if (dto.getDisciplinasId() != null) {
            curso.setDisciplinas(buscarDisciplinas(dto.getDisciplinasId()));
        }

        if (dto.getNivelString() != null) {
            curso.setNivel(NivelCurso.valueOf(dto.getNivelString()));
        }

        Curso atualizado = cursoRepository.save(curso);
        return ResponseEntity.ok(cursoMapper.toResponseDTO(atualizado));
    }


    public ResponseEntity<Void> deletar(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(CursoNotFoundException::new);
        cursoRepository.delete(curso);
        return ResponseEntity.noContent().build();
    }

    private Instituicao buscarInstituicao(Long id) {
        return instituicaoRepository.findById(id)
                .orElseThrow(() -> new InstituicaoNotFoundException("Instituição com ID " + id + " não encontrada."));
    }

    private Coordenador buscarCoordenador(Long id) {
        return coordenadorRepository.findById(id)
                .orElseThrow(() -> new CoordenadorNotFoundException("Coordenador com ID " + id + " não encontrado."));
    }

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
}
