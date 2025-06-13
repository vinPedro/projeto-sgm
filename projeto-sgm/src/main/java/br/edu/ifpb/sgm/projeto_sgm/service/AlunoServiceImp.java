package br.edu.ifpb.sgm.projeto_sgm.service;

import br.edu.ifpb.sgm.projeto_sgm.dto.AlunoRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.AlunoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.exception.DisciplinaNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.AlunoNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.mapper.AlunoMapper;
import br.edu.ifpb.sgm.projeto_sgm.model.Aluno;
import br.edu.ifpb.sgm.projeto_sgm.model.Disciplina;
import br.edu.ifpb.sgm.projeto_sgm.repository.AlunoRepository;
import br.edu.ifpb.sgm.projeto_sgm.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlunoServiceImp {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private AlunoMapper alunoMapper;


    public ResponseEntity<AlunoResponseDTO> salvar(AlunoRequestDTO alunoRequestDTO){
        Aluno aluno = alunoMapper.toEntity(alunoRequestDTO);
        aluno.setDisciplinasPagas(buscarDisciplinas(alunoRequestDTO.getDisciplinasPagasId()));
        Aluno salvo = alunoRepository.save(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoMapper.toResponseDTO(salvo));
    }

    public ResponseEntity<AlunoResponseDTO> buscarPorId(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(AlunoNotFoundException::new);
        return ResponseEntity.ok(alunoMapper.toResponseDTO(aluno));
    }

    public ResponseEntity<List<AlunoResponseDTO>> listarTodos() {
        List<Aluno> alunos = alunoRepository.findAll();
        List<AlunoResponseDTO> dtos = alunos.stream()
                .map(alunoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    public ResponseEntity<AlunoResponseDTO> atualizar(Long id, AlunoRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(AlunoNotFoundException::new);
        alunoMapper.updateAlunoFromDto(dto, aluno);

        if (dto.getDisciplinasPagasId() != null) {
            aluno.setDisciplinasPagas(buscarDisciplinas(dto.getDisciplinasPagasId()));
        }

        Aluno atualizado = alunoRepository.save(aluno);
        return ResponseEntity.ok(alunoMapper.toResponseDTO(atualizado));
    }

    public ResponseEntity<Void> deletar(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(AlunoNotFoundException::new);
        alunoRepository.delete(aluno);
        return ResponseEntity.noContent().build();
    }

    private Set<Disciplina> buscarDisciplinas(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptySet();

        // Encontra todos os IDs inexistentes
        Set<Long> idsNaoEncontrados = ids.stream()
                .filter(id -> disciplinaRepository.findById(id).isEmpty())
                .collect(Collectors.toSet());

        if (!idsNaoEncontrados.isEmpty()) {
            throw new DisciplinaNotFoundException("IDs de disciplinas inválidos: " + idsNaoEncontrados);
        }

        // Busca segura, já que todos existem
        return ids.stream()
                .map(id -> disciplinaRepository.findById(id).get())
                .collect(Collectors.toSet());

    }
}
