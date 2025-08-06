package br.edu.ifpb.sgm.projeto_sgm.service;

import br.edu.ifpb.sgm.projeto_sgm.dto.*;
import br.edu.ifpb.sgm.projeto_sgm.exception.DisciplinaNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.AlunoNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.InstituicaoNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.MonitoriaNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.mapper.AlunoMapper;
import br.edu.ifpb.sgm.projeto_sgm.mapper.PessoaMapper;
import br.edu.ifpb.sgm.projeto_sgm.model.*;
import br.edu.ifpb.sgm.projeto_sgm.model.embeddable.AlunoDisciplinaPagaId;
import br.edu.ifpb.sgm.projeto_sgm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static br.edu.ifpb.sgm.projeto_sgm.util.Constants.*;

@Service
@Transactional
public class AlunoServiceImp {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private AlunoDisciplinaPagaRepository alunoDisciplinaPagaRepository;

    @Autowired
    private AlunoMapper alunoMapper;

    @Autowired
    private PessoaMapper pessoaMapper;


    public ResponseEntity<AlunoResponseDTO> salvar(AlunoRequestDTO alunoRequestDTO){

        Pessoa pessoa = pessoaMapper.fromPessoa(alunoRequestDTO);
        pessoa.setInstituicao(buscarInstituicao(alunoRequestDTO.getInstituicaoId()));

        Role alunoRole = roleRepository.findByRole("ROLE_" + DISCENTE)
                .orElseThrow(() -> new RuntimeException("ERRO CRÍTICO: Role DISCENTE não encontrada no banco!"));
        pessoa.setRoles(List.of(alunoRole));

        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        Aluno aluno = new Aluno();
//        aluno.setDisciplinasPagas(buscarDisciplinas(alunoRequestDTO.getDisciplinasPagasId()));
        aluno.setDisciplinaMonitoria(buscarDisciplinas(alunoRequestDTO.getDisciplinasMonitoriaId()));
        aluno.setPessoa(pessoaSalva);
        aluno.setCre(alunoRequestDTO.getCre());


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

    public ResponseEntity<List<AlunoResponseDTO>> listarTodosCadastrados() {
        List<Aluno> alunos = alunoRepository.findByCadastradoTrue();
        List<AlunoResponseDTO> dtos = alunos.stream()
                .map(alunoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    public ResponseEntity<AlunoResponseDTO> atualizar(Long id, AlunoRequestDTO dto) {

        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(AlunoNotFoundException::new);


        Pessoa pesssoaAtualizada = pessoaMapper.fromPessoa(dto);


        if (dto.getInstituicaoId() != null) {
            pesssoaAtualizada.setInstituicao(buscarInstituicao(dto.getInstituicaoId()));
        }

        pessoaMapper.updatePessoaFromPessoa(pesssoaAtualizada, pessoa);

        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(AlunoNotFoundException::new);
        alunoMapper.updateAlunoFromDto(dto, aluno);

//        if (dto.getDisciplinasPagasId() != null) {
//            aluno.setDisciplinasPagas(buscarDisciplinas(dto.getDisciplinasPagasId()));
//        }

        if (dto.getDisciplinasMonitoriaId() != null) {
            aluno.setDisciplinaMonitoria(buscarDisciplinas(dto.getDisciplinasMonitoriaId()));
        }


        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        aluno.setPessoa(pessoaSalva);

        Aluno atualizado = alunoRepository.save(aluno);
        return ResponseEntity.ok(alunoMapper.toResponseDTO(atualizado));
    }

    public ResponseEntity<Void> deletar(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(AlunoNotFoundException::new);
        aluno.setCadastrado(false);
        aluno.setDisciplinaMonitoria(null);

        alunoRepository.save(aluno);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<AlunoResponseDTO> associar(Long id, AlunoRequestDTO alunoRequestDTO){
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow();

        Aluno aluno = new Aluno();

//        aluno.setDisciplinasPagas(buscarDisciplinas(alunoRequestDTO.getDisciplinasPagasId()));
        aluno.setDisciplinaMonitoria(buscarDisciplinas(alunoRequestDTO.getDisciplinasMonitoriaId()));
        aluno.setPessoa(pessoa);

        Aluno salvo = alunoRepository.save(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoMapper.toResponseDTO(salvo));
    }

    public ResponseEntity<List<AlunoResponseDTO>> buscarMonitores(){
        List<Aluno> alunos = alunoRepository.findAlunosComMonitoria();
        List<AlunoResponseDTO> dtos = alunos.stream()
                .map(alunoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    public ResponseEntity<List<AlunoResponseDTO>> buscarAlunosSemMonitoria(){
        List<Aluno> alunos = alunoRepository.findAlunosSemMonitoria();
        List<AlunoResponseDTO> dtos = alunos.stream()
                .map(alunoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    public ResponseEntity<MonitorResponseDTO> buscarMonitorPorId(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException("Aluno com ID " + id + " não encontrado."));
        AlunoResponseDTO alunoResponseDTO = alunoMapper.toResponseDTO(aluno);

        MonitorResponseDTO monitorResponseDTO = new MonitorResponseDTO();
//        monitorResponseDTO.setDisciplinasMonitoriaResponseDTO(alunoResponseDTO.getDisciplinasMonitoriaResponseDTO());
        monitorResponseDTO.setAlunoResponseDTO(alunoResponseDTO);

        return ResponseEntity.ok(monitorResponseDTO);
    }

    public ResponseEntity<AlunoResponseDTO> salvarAlunoMonitor(MonitorRequestDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.getId()).orElseThrow();

        Aluno aluno = alunoRepository.findById(dto.getId())
                .orElseThrow(() -> new AlunoNotFoundException("Aluno com ID " + dto.getId() + " não encontrado."));

        Role monitorRole = roleRepository.findByRole("ROLE_" + MONITOR)
                .orElseThrow(() -> new RuntimeException("ERRO CRÍTICO: Role MONITOR não encontrada no banco!"));

        if (dto.getDisciplinasMonitoriaId() != null) {
            aluno.setDisciplinaMonitoria(buscarDisciplinas(dto.getDisciplinasMonitoriaId()));
            pessoa.getRoles().add(monitorRole);
        }

        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        aluno.setPessoa(pessoaSalva);

        Aluno salvo = alunoRepository.save(aluno);

        return ResponseEntity.status(HttpStatus.CREATED).body(alunoMapper.toResponseDTO(salvo));
    }

    public ResponseEntity<AlunoResponseDTO> atualizarMonitor(Long id, MonitorRequestDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow();

        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException("Aluno com ID " + id + " não encontrado."));



        if (dto.getDisciplinasMonitoriaId() == null) {
            Role monitorRole = roleRepository.findByRole("ROLE_" + MONITOR)
                    .orElseThrow(() -> new RuntimeException("ERRO CRÍTICO: Role MONITOR não encontrada no banco!"));

            pessoa.getRoles().remove(monitorRole);
        }

        aluno.setDisciplinaMonitoria(buscarDisciplinas(dto.getDisciplinasMonitoriaId()));
        aluno.setPessoa(pessoa);

        Aluno atualizado = alunoRepository.save(aluno);
        return ResponseEntity.ok(alunoMapper.toResponseDTO(atualizado));
    }

    public ResponseEntity<Void> deletarMonitor(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow();

        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException("Aluno com ID " + id + " não encontrado."));

        if (pessoa != null) {
            Role monitorRole = roleRepository.findByRole("ROLE_" + MONITOR)
                    .orElseThrow(() -> new RuntimeException("ERRO CRÍTICO: Role MONITOR não encontrada no banco!"));

            pessoa.getRoles().remove(monitorRole);
        }

        aluno.setDisciplinaMonitoria(null);
        aluno.setPessoa(pessoa);

        alunoRepository.save(aluno);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> revogarConclusao(AlunoDisciplinaPagaResquestDTO dto) {

        AlunoDisciplinaPagaId alunoDisciplinaPagaId = new AlunoDisciplinaPagaId();
        alunoDisciplinaPagaId.setAlunoId(dto.getAlunoId());
        alunoDisciplinaPagaId.setDisciplinaId(dto.getDisciplinaId());

        if (!alunoDisciplinaPagaRepository.existsById(alunoDisciplinaPagaId)) {
            return ResponseEntity.notFound().build();
        }

        alunoDisciplinaPagaRepository.deleteById(alunoDisciplinaPagaId);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<AlunoResponseDTO> adicionarConcluinte(AlunoDisciplinaPagaResquestDTO dto){
        Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplinaId())
                .orElseThrow(() -> new DisciplinaNotFoundException("Disciplina não encontrada"));


        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado"));



        AlunoDisciplinaPagaId id = new AlunoDisciplinaPagaId();
        id.setDisciplinaId(disciplina.getId());
        id.setAlunoId(aluno.getId());

        AlunoDisciplinaPaga conclusao = new AlunoDisciplinaPaga();
        conclusao.setId(id);
        conclusao.setDisciplina(disciplina);
        conclusao.setAluno(aluno);
        conclusao.setNota(dto.getNota());

        aluno.getDisciplinasPagas().add(conclusao);

        Aluno salvo = alunoRepository.save(aluno);

        return ResponseEntity.status(HttpStatus.CREATED).body(alunoMapper.toResponseDTO(salvo));
    }

    public ResponseEntity<List<AlunoResponseDTO>> listarAlunosQuePagaramDisciplina(Long id) {
        List<Aluno> alunos = alunoRepository.listarAlunosQuePagaramDisciplina(id);
        List<AlunoResponseDTO> dtos = alunos.stream()
                .map(alunoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    public ResponseEntity<List<AlunoResponseDTO>> listarAlunosQueNaoPagaramDisciplina(Long id) {
        List<Aluno> alunos = alunoRepository.listarAlunosQueNaoPagaramDisciplina(id);
        List<AlunoResponseDTO> dtos = alunos.stream()
                .map(alunoMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(dtos);
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

    private Instituicao buscarInstituicao(Long id) {
        return instituicaoRepository.findById(id)
                .orElseThrow(() -> new InstituicaoNotFoundException("Instituição com ID " + id + " não encontrada."));
    }
}
