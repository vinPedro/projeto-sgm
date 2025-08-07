package br.edu.ifpb.sgm.projeto_sgm.service;

import br.edu.ifpb.sgm.projeto_sgm.dto.AlunoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.MonitoriaInscritosResquestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.MonitoriaRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.MonitoriaResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.exception.*;
import br.edu.ifpb.sgm.projeto_sgm.mapper.AlunoMapper;
import br.edu.ifpb.sgm.projeto_sgm.mapper.MonitoriaMapper;
import br.edu.ifpb.sgm.projeto_sgm.model.*;
import br.edu.ifpb.sgm.projeto_sgm.model.embeddable.MonitoriaInscritoId;
import br.edu.ifpb.sgm.projeto_sgm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MonitoriaServiceImp {

    @Autowired
    private MonitoriaRepository monitoriaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProcessoSeletivoRepository processoSeletivoRepository;

    @Autowired
    private MonitoriaInscricoesRepository monitoriaInscricoesRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private AlunoDisciplinaPagaRepository alunoDisciplinaPagaRepository;

    @Autowired
    private MonitoriaMapper monitoriaMapper;

    @Autowired
    private AlunoMapper alunoMapper;

    public ResponseEntity<MonitoriaResponseDTO> salvar(MonitoriaRequestDTO dto) {
        Monitoria monitoria = monitoriaMapper.toEntity(dto);

        monitoria.setDisciplina(buscarDisciplina(dto.getDisciplinaId()));
        monitoria.setProfessor(buscarProfessor(dto.getProfessorId()));
        monitoria.setProcessoSeletivo(buscarProcesso(dto.getProcessoSeletivoId()));
//        monitoria.setInscricoes(buscarInscritosMonitoria(dto.getInscricoesId()));

        Monitoria salva = monitoriaRepository.save(monitoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(monitoriaMapper.toResponseDTO(salva));
    }

    public ResponseEntity<MonitoriaResponseDTO> buscarPorId(Long id) {
        Monitoria monitoria = monitoriaRepository.findById(id)
                .orElseThrow(() -> new MonitoriaNotFoundException("Monitoria com ID " + id + " não encontrada."));
        return ResponseEntity.ok(monitoriaMapper.toResponseDTO(monitoria));
    }

    public ResponseEntity<List<MonitoriaResponseDTO>> listarTodos() {
        List<Monitoria> monitorias = monitoriaRepository.findAll();
        List<MonitoriaResponseDTO> dtos = monitorias.stream()
                .map(monitoriaMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    public ResponseEntity<List<MonitoriaResponseDTO>> listarMonitoriasPorProcessoSeletivo(Long id) {
        List<Monitoria> monitorias = monitoriaRepository.findByProcessoSeletivoId(id);
        List<MonitoriaResponseDTO> dtos = monitorias.stream()
                .map(monitoriaMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    public ResponseEntity<MonitoriaResponseDTO> atualizar(Long id, MonitoriaRequestDTO dto) {
        Monitoria monitoria = monitoriaRepository.findById(id)
                .orElseThrow(() -> new MonitoriaNotFoundException("Monitoria com ID " + id + " não encontrada."));

        monitoriaMapper.updateMonitoriaFromDto(dto, monitoria);

        if (dto.getDisciplinaId() != null) {
            monitoria.setDisciplina(buscarDisciplina(dto.getDisciplinaId()));
        }

        if (dto.getProfessorId() != null) {
            monitoria.setProfessor(buscarProfessor(dto.getProfessorId()));
        }

//        if (dto.getInscricoesId() != null) {
//            monitoria.setInscricoes(buscarInscritosMonitoria(dto.getInscricoesId()));
//        }


        if (dto.getProcessoSeletivoId() != null) {
            monitoria.setProcessoSeletivo(buscarProcesso(dto.getProcessoSeletivoId()));
        }



        Monitoria atualizada = monitoriaRepository.save(monitoria);
        return ResponseEntity.ok(monitoriaMapper.toResponseDTO(atualizada));
    }

    public ResponseEntity<Void> deletar(Long id) {
        Monitoria monitoria = monitoriaRepository.findById(id)
                .orElseThrow(() -> new MonitoriaNotFoundException("Monitoria com ID " + id + " não encontrada."));
        monitoriaRepository.delete(monitoria);
        return ResponseEntity.noContent().build();
    }

    //Métodos de Inscrição em monitoria

    public ResponseEntity<MonitoriaResponseDTO> inscreverAluno(MonitoriaInscritosResquestDTO dto) {
        Monitoria monitoria = monitoriaRepository.findById(dto.getMonitoriaId())
                .orElseThrow(() -> new MonitoriaNotFoundException("Monitoria não encontrada"));

        LocalDate dataAtual = LocalDate.now();

        if(monitoria.getProcessoSeletivo().getFim().isBefore(dataAtual)){
            throw new IllegalArgumentException("O processo já chegou ao fim, não permitindo mais inscrições");
        }

        if(monitoria.getProcessoSeletivo().isFinalizado()){
            throw new IllegalArgumentException("O processo já foi finalizado, não permitindo mais inscrições");
        }

        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado"));

        if(!alunoDisciplinaPagaRepository.existsByAlunoIdAndDisciplinaId(aluno.getId(), monitoria.getDisciplina().getId())){
            throw new IllegalArgumentException("Você não concluiu essa Disciplina! ");
        }

        MonitoriaInscritoId id = new MonitoriaInscritoId();
        id.setMonitoriaId(monitoria.getId());
        id.setAlunoId(aluno.getId());

        MonitoriaInscritos inscricao = new MonitoriaInscritos();
        inscricao.setId(id);
        inscricao.setMonitoria(monitoria);
        inscricao.setAluno(aluno);

        monitoria.getInscricoes().add(inscricao); // importante manter a relação bidirecional

        Monitoria salva =  monitoriaRepository.save(monitoria);// salvar apenas a monitoria já propaga para as inscrições

        return ResponseEntity.status(HttpStatus.CREATED).body(monitoriaMapper.toResponseDTO(salva));
    }

    public ResponseEntity<List<MonitoriaResponseDTO>> listarInscricoesAluno(Long id) {
        List<Monitoria> monitorias = monitoriaInscricoesRepository.findMonitoriasByAlunoId(id);
        List<MonitoriaResponseDTO> dtos = monitorias.stream()
                .map(monitoriaMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    public ResponseEntity<List<AlunoResponseDTO>> listarAlunosInscritosPorMonitoria(Long monitoriaId) {
        List<Aluno> alunos = monitoriaInscricoesRepository.buscarAlunosPorMonitoria(monitoriaId);
        List<AlunoResponseDTO> dtos = alunos.stream()
                .map(alunoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    public ResponseEntity<?> cancelarInscricao(MonitoriaInscritosResquestDTO dto) {
        MonitoriaInscritoId monitoriaInscritoId = new MonitoriaInscritoId();
        monitoriaInscritoId.setMonitoriaId(dto.getMonitoriaId());
        monitoriaInscritoId.setAlunoId(dto.getAlunoId());

        if (!monitoriaInscricoesRepository.existsById(monitoriaInscritoId)) {
            return ResponseEntity.notFound().build();
        }

        monitoriaInscricoesRepository.deleteById(monitoriaInscritoId);
        return ResponseEntity.noContent().build();
    }

    // Métodos auxiliares

    private Disciplina buscarDisciplina(Long id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new DisciplinaNotFoundException("Disciplina com ID " + id + " não encontrada."));
    }

    private Professor buscarProfessor(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new ProfessorNotFoundException("Professor com ID " + id + " não encontrado."));
    }


    private ProcessoSeletivo buscarProcesso(Long id) {
        return processoSeletivoRepository.findById(id)
                .orElseThrow(() -> new ProcessoSeletivoNotFoundException("Processo seletivo com ID " + id + " não encontrado."));
    }

//    private List<MonitoriaInscritos> buscarInscritosMonitoria(List<Long> ids) {
//        if (ids == null || ids.isEmpty()) return Collections.emptyList();
//
//        List<Long> idsNaoEncontrados = ids.stream()
//                .filter(id -> monitoriaInscricoesRepository.findById(id).isEmpty())
//                .toList();
//
//        if (!idsNaoEncontrados.isEmpty()) {
//            throw new MonitoriaNotFoundException("IDs de disciplinas inválidos: " + idsNaoEncontrados);
//        }
//
//        return ids.stream()
//                .map(id -> monitoriaInscricoesRepository.findById(id).get())
//                .collect(Collectors.toList());
//    }
}
