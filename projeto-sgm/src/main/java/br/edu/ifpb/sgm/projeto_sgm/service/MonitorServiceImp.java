package br.edu.ifpb.sgm.projeto_sgm.service;

import br.edu.ifpb.sgm.projeto_sgm.dto.MonitorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.MonitorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.exception.AlunoNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.DisciplinaNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.MonitorNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.mapper.MonitorMapper;
import br.edu.ifpb.sgm.projeto_sgm.model.Aluno;
import br.edu.ifpb.sgm.projeto_sgm.model.Disciplina;
import br.edu.ifpb.sgm.projeto_sgm.model.Monitor;
import br.edu.ifpb.sgm.projeto_sgm.repository.AlunoRepository;
import br.edu.ifpb.sgm.projeto_sgm.repository.DisciplinaRepository;
import br.edu.ifpb.sgm.projeto_sgm.repository.MonitorRepository;
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
public class MonitorServiceImp {

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private MonitorMapper monitorMapper;

    public ResponseEntity<MonitorResponseDTO> salvar(MonitorRequestDTO dto) {
        Monitor monitor = monitorMapper.toEntity(dto);
        monitor.setAluno(buscarAluno(dto.getAlunoId()));
        monitor.setDisciplinaMonitoria(buscarDisciplinas(dto.getDisciplinaMonitoriaId()));
        Monitor salvo = monitorRepository.save(monitor);
        return ResponseEntity.status(HttpStatus.CREATED).body(monitorMapper.toResponseDTO(salvo));
    }

    public ResponseEntity<MonitorResponseDTO> buscarPorId(Long id) {
        Monitor monitor = monitorRepository.findById(id)
                .orElseThrow(() -> new MonitorNotFoundException("Monitor com ID " + id + " não encontrado."));
        return ResponseEntity.ok(monitorMapper.toResponseDTO(monitor));
    }

    public ResponseEntity<List<MonitorResponseDTO>> listarTodos() {
        List<Monitor> monitores = monitorRepository.findAll();
        List<MonitorResponseDTO> dtos = monitores.stream()
                .map(monitorMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    public ResponseEntity<MonitorResponseDTO> atualizar(Long id, MonitorRequestDTO dto) {
        Monitor monitor = monitorRepository.findById(id)
                .orElseThrow(() -> new MonitorNotFoundException("Monitor com ID " + id + " não encontrado."));

        monitorMapper.updateMonitorFromDto(dto, monitor);

        if (dto.getAlunoId() != null) {
            monitor.setAluno(buscarAluno(dto.getAlunoId()));
        }

        if (dto.getDisciplinaMonitoriaId() != null) {
            monitor.setDisciplinaMonitoria(buscarDisciplinas(dto.getDisciplinaMonitoriaId()));
        }

        Monitor atualizado = monitorRepository.save(monitor);
        return ResponseEntity.ok(monitorMapper.toResponseDTO(atualizado));
    }

    public ResponseEntity<Void> deletar(Long id) {
        Monitor monitor = monitorRepository.findById(id)
                .orElseThrow(() -> new MonitorNotFoundException("Monitor com ID " + id + " não encontrado."));
        monitorRepository.delete(monitor);
        return ResponseEntity.noContent().build();
    }

    private Aluno buscarAluno(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException("Aluno com ID " + id + " não encontrado."));
    }

    private Set<Disciplina> buscarDisciplinas(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptySet();

        Set<Long> idsNaoEncontrados = ids.stream()
                .filter(id -> disciplinaRepository.findById(id).isEmpty())
                .collect(Collectors.toSet());

        if (!idsNaoEncontrados.isEmpty()) {
            throw new DisciplinaNotFoundException("IDs de disciplinas inválidos: " + idsNaoEncontrados);
        }

        return ids.stream()
                .map(id -> disciplinaRepository.findById(id).get())
                .collect(Collectors.toSet());
    }
}
