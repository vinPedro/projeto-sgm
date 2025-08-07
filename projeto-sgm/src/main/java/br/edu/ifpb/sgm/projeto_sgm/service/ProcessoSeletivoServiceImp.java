package br.edu.ifpb.sgm.projeto_sgm.service;

import br.edu.ifpb.sgm.projeto_sgm.dto.*;
import br.edu.ifpb.sgm.projeto_sgm.exception.AlunoNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.InstituicaoNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.MonitoriaNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.ProcessoSeletivoNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.mapper.ProcessoSeletivoMapper;
import br.edu.ifpb.sgm.projeto_sgm.model.*;
import br.edu.ifpb.sgm.projeto_sgm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static br.edu.ifpb.sgm.projeto_sgm.util.Constants.MONITOR;

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
    private AlunoRepository alunoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private RoleRepository roleRepository;

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

    public ResponseEntity<Void> resultadoProcesso(Long id){
        ProcessoSeletivo processoSeletivo = processoSeletivoRepository.findById(id)
                .orElseThrow(() -> new ProcessoSeletivoNotFoundException("Processo com ID " + id + "não encontrado"));

        LocalDate dataAtual = LocalDate.now();

        if(processoSeletivo.getFim().isAfter(dataAtual)){
            throw new IllegalArgumentException("A data do processo não chegou ao fim");
        }

        List<Monitoria> monitorias = monitoriaRepository.findByProcessoSeletivoId(id);

        BigDecimal pesoNota = new BigDecimal("0.6");
        BigDecimal pesoCre = new BigDecimal("0.4");

        for(Monitoria monitoria : monitorias){
            Disciplina disciplina = monitoria.getDisciplina();

            monitoria.getInscricoes().sort((a, b) -> {
                BigDecimal notaA = calcularPontuacao(a, disciplina, pesoNota, pesoCre);
                BigDecimal notaB = calcularPontuacao(b, disciplina, pesoNota, pesoCre);
                return notaB.compareTo(notaA); // Maior primeiro
            });

            for(int i = 0; i < Math.min(monitoria.getNumeroVaga(), monitoria.getInscricoes().size()); i++){
                Aluno aluno = monitoria.getInscricoes().get(i).getAluno();
                monitoria.getInscricoes().get(i).setSelecionado(true);
                aluno.getDisciplinaMonitoria().add(
                        monitoria.getInscricoes().get(i).getMonitoria().getDisciplina()
                );
                Pessoa pessoa = salvarComoMonitor(aluno.getId());
                aluno.setPessoa(pessoa);
                alunoRepository.save(aluno);
            }

        }

        processoSeletivo.setFinalizado(true);
        processoSeletivoRepository.save(processoSeletivo);

        monitoriaRepository.saveAll(monitorias);

        return ResponseEntity.ok().build();
    }

    private BigDecimal calcularPontuacao(MonitoriaInscritos inscricao, Disciplina disciplina, BigDecimal pesoNota, BigDecimal pesoCre) {
        Optional<AlunoDisciplinaPaga> disciplinaPagaOpt = buscarPorIdAluno(
                inscricao.getAluno().getDisciplinasPagas(),
                inscricao.getAluno().getId(),
                disciplina.getId()
        );

        if (disciplinaPagaOpt.isEmpty()) {
            return inscricao.getAluno().getCre().multiply(pesoCre);
        }

        BigDecimal nota = disciplinaPagaOpt.get().getNota();
        BigDecimal cre = inscricao.getAluno().getCre();
        return nota.multiply(pesoNota).add(cre.multiply(pesoCre));
    }

    public Optional<AlunoDisciplinaPaga> buscarPorIdAluno(Set<AlunoDisciplinaPaga> lista, Long alunoId, Long disciplinaId) {

        if (lista == null) return Optional.empty();

        return lista.stream()
                .filter(adp -> adp.getId().getAlunoId().equals(alunoId)
                        && adp.getId().getDisciplinaId().equals(disciplinaId))
                .findFirst();
    }

    public Pessoa salvarComoMonitor(long id){
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow();

        Role monitorRole = roleRepository.findByRole("ROLE_" + MONITOR)
                .orElseThrow(() -> new RuntimeException("ERRO CRÍTICO: Role MONITOR não encontrada no banco!"));

        pessoa.getRoles().add(monitorRole);

        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        return pessoaSalva;
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
