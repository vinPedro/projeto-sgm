package br.edu.ifpb.sgm.projeto_sgm.service;

import br.edu.ifpb.sgm.projeto_sgm.dto.ProcessoSeletivoRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProcessoSeletivoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.exception.InstituicaoNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.MonitoriaNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.ProcessoSeletivoNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.mapper.ProcessoSeletivoMapper;
import br.edu.ifpb.sgm.projeto_sgm.model.Instituicao;
import br.edu.ifpb.sgm.projeto_sgm.model.Monitoria;
import br.edu.ifpb.sgm.projeto_sgm.model.ProcessoSeletivo;
import br.edu.ifpb.sgm.projeto_sgm.repository.InstituicaoRepository;
import br.edu.ifpb.sgm.projeto_sgm.repository.MonitoriaRepository;
import br.edu.ifpb.sgm.projeto_sgm.repository.ProcessoSeletivoRepository;
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
public class ProcessoSeletivoServiceImp {

    @Autowired
    private ProcessoSeletivoRepository processoSeletivoRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private MonitoriaRepository monitoriaRepository;

    @Autowired
    private ProcessoSeletivoMapper processoSeletivoMapper;

    public ResponseEntity<ProcessoSeletivoResponseDTO> salvar(ProcessoSeletivoRequestDTO dto) {
        ProcessoSeletivo processo = processoSeletivoMapper.toEntity(dto);
        processo.setInstituicao(buscarInstituicao(dto.getInstituicaoId()));

        ProcessoSeletivo salvo = processoSeletivoRepository.save(processo);
        return ResponseEntity.status(HttpStatus.CREATED).body(processoSeletivoMapper.toResponseDTO(salvo));
    }

    public ResponseEntity<ProcessoSeletivoResponseDTO> buscarPorId(Long id) {
        ProcessoSeletivo processo = processoSeletivoRepository.findById(id)
                .orElseThrow(() -> new ProcessoSeletivoNotFoundException("Processo seletivo com ID " + id + " não encontrado."));
        return ResponseEntity.ok(processoSeletivoMapper.toResponseDTO(processo));
    }

    public ResponseEntity<List<ProcessoSeletivoResponseDTO>> listarTodos() {
        List<ProcessoSeletivo> processos = processoSeletivoRepository.findAll();
        List<ProcessoSeletivoResponseDTO> dtos = processos.stream()
                .map(processoSeletivoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    public ResponseEntity<ProcessoSeletivoResponseDTO> atualizar(Long id, ProcessoSeletivoRequestDTO dto) {
        ProcessoSeletivo processo = processoSeletivoRepository.findById(id)
                .orElseThrow(() -> new ProcessoSeletivoNotFoundException("Processo seletivo com ID " + id + " não encontrado."));

        processoSeletivoMapper.updateProcessoSeletivoFromDto(dto, processo);

        if (dto.getInstituicaoId() != null) {
            processo.setInstituicao(buscarInstituicao(dto.getInstituicaoId()));
        }


        ProcessoSeletivo atualizado = processoSeletivoRepository.save(processo);
        return ResponseEntity.ok(processoSeletivoMapper.toResponseDTO(atualizado));
    }

    public ResponseEntity<Void> deletar(Long id) {
        ProcessoSeletivo processo = processoSeletivoRepository.findById(id)
                .orElseThrow(() -> new ProcessoSeletivoNotFoundException("Processo seletivo com ID " + id + " não encontrado."));
        processoSeletivoRepository.delete(processo);
        return ResponseEntity.noContent().build();
    }

    // Métodos auxiliares

    private Instituicao buscarInstituicao(Long id) {
        return instituicaoRepository.findById(id)
                .orElseThrow(() -> new InstituicaoNotFoundException("Instituição com ID " + id + " não encontrada."));
    }

    private List<Monitoria> buscarMonitorias(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptyList();

        List<Long> idsNaoEncontrados = ids.stream()
                .filter(id -> monitoriaRepository.findById(id).isEmpty())
                .toList();

        if (!idsNaoEncontrados.isEmpty()) {
            throw new MonitoriaNotFoundException("IDs de monitorias inválidos: " + idsNaoEncontrados);
        }

        return ids.stream()
                .map(id -> monitoriaRepository.findById(id).get())
                .collect(Collectors.toList());
    }
}
