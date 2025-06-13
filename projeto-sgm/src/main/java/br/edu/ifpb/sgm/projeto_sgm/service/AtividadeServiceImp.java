package br.edu.ifpb.sgm.projeto_sgm.service;

import br.edu.ifpb.sgm.projeto_sgm.dto.AtividadeRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.AtividadeResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.exception.MonitoriaNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.AtividadeNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.mapper.AtividadeMapper;
import br.edu.ifpb.sgm.projeto_sgm.model.Atividade;
import br.edu.ifpb.sgm.projeto_sgm.model.Monitoria;
import br.edu.ifpb.sgm.projeto_sgm.repository.AtividadeRepository;
import br.edu.ifpb.sgm.projeto_sgm.repository.MonitoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AtividadeServiceImp {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private MonitoriaRepository monitoriaRepository;

    @Autowired
    private AtividadeMapper atividadeMapper;

    public ResponseEntity<AtividadeResponseDTO> salvar(AtividadeRequestDTO dto) {
        Atividade atividade = atividadeMapper.toEntity(dto);
        atividade.setMonitoria(buscarMonitoria(dto.getMonitoriaId()));
        Atividade salva = atividadeRepository.save(atividade);
        return ResponseEntity.status(HttpStatus.CREATED).body(atividadeMapper.toResponseDTO(salva));
    }

    public ResponseEntity<AtividadeResponseDTO> buscarPorId(Long id) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(AtividadeNotFoundException::new);
        return ResponseEntity.ok(atividadeMapper.toResponseDTO(atividade));
    }

    public ResponseEntity<List<AtividadeResponseDTO>> listarTodas() {
        List<Atividade> atividades = atividadeRepository.findAll();
        List<AtividadeResponseDTO> dtos = atividades.stream()
                .map(atividadeMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    public ResponseEntity<AtividadeResponseDTO> atualizar(Long id, AtividadeRequestDTO dto) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(AtividadeNotFoundException::new);

        atividadeMapper.updateAtividadeFromDto(dto, atividade);

        if (dto.getMonitoriaId() != null) {
            atividade.setMonitoria(buscarMonitoria(dto.getMonitoriaId()));
        }

        Atividade atualizada = atividadeRepository.save(atividade);
        return ResponseEntity.ok(atividadeMapper.toResponseDTO(atualizada));
    }

    public ResponseEntity<Void> deletar(Long id) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(AtividadeNotFoundException::new);
        atividadeRepository.delete(atividade);
        return ResponseEntity.noContent().build();
    }

    private Monitoria buscarMonitoria(Long id) {
        return monitoriaRepository.findById(id)
                .orElseThrow(() -> new MonitoriaNotFoundException("Monitoria com ID " + id + " não encontrada."));
    }
}
