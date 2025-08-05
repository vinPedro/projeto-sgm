package br.edu.ifpb.sgm.projeto_sgm.controller;

import br.edu.ifpb.sgm.projeto_sgm.dto.*;
import br.edu.ifpb.sgm.projeto_sgm.service.AlunoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoControllerImp {

    @Autowired
    private AlunoServiceImp alunoService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<AlunoResponseDTO> criar(@RequestBody AlunoRequestDTO dto) {
        encriptPassword(dto);
        return alunoService.salvar(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> buscarPorId(@PathVariable Long id) {
        return alunoService.buscarPorId(id);
    }

    @GetMapping("/cadastros")
    public ResponseEntity<List<AlunoResponseDTO>> listarTodos() {
        return alunoService.listarTodos();
    }

    @GetMapping
    public ResponseEntity<List<AlunoResponseDTO>> listarTodosCadastrados() {
        return alunoService.listarTodosCadastrados();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> atualizar(@PathVariable Long id, @RequestBody AlunoRequestDTO dto) {
        return alunoService.atualizar(id, dto);
    }

    @PutMapping("/associar/{id}")
    public ResponseEntity<AlunoResponseDTO> associar(@PathVariable Long id, @RequestBody AlunoRequestDTO dto) {
        return alunoService.associar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return alunoService.deletar(id);
    }

    private void encriptPassword(AlunoRequestDTO dto) {
        String encodedPassword = passwordEncoder.encode(dto.getSenha());
        dto.setSenha(encodedPassword);
    }

    //Métodos monitor
    @PostMapping("/monitores")
    public ResponseEntity<AlunoResponseDTO> criarMonitor(@RequestBody MonitorRequestDTO dto) {
        return alunoService.salvarAlunoMonitor(dto);
    }

    @GetMapping("/monitores/{id}")
    public ResponseEntity<MonitorResponseDTO> buscarMonitorPorId(@PathVariable Long id) {
        return alunoService.buscarMonitorPorId(id);
    }

    @GetMapping("/monitores")
    public ResponseEntity<List<AlunoResponseDTO>> listarTodosMonitores() {
        return alunoService.buscarMonitores();
    }

    @GetMapping("/null-monitoria")
    public ResponseEntity<List<AlunoResponseDTO>> listarAlunosNullMonitoria() {
        return alunoService.buscarAlunosSemMonitoria();
    }

    @PutMapping("/monitores/{id}")
    public ResponseEntity<AlunoResponseDTO> atualizarMonitor(@PathVariable Long id, @RequestBody MonitorRequestDTO dto) {
        return alunoService.atualizarMonitor(id, dto);
    }

    @DeleteMapping("/monitores/{id}")
    public ResponseEntity<Void> deletarMonitor(@PathVariable Long id) {
        return alunoService.deletarMonitor(id);
    }

    //Métodos disciplina

    @GetMapping("/disciplinas/concluintes/{id}")
    public ResponseEntity<List<AlunoResponseDTO>> listarAlunosQuePagaramDisciplina(@PathVariable Long id) {
        return alunoService.listarAlunosQuePagaramDisciplina(id);
    }

    @GetMapping("/disciplinas/null-concluintes/{id}")
    public ResponseEntity<List<AlunoResponseDTO>> listarAlunosQueNaoPagaramDisciplina(@PathVariable Long id) {
        return alunoService.listarAlunosQueNaoPagaramDisciplina(id);
    }

    @PostMapping("/disciplinas/concluintes")
    public ResponseEntity<AlunoResponseDTO> criarMonitor(@RequestBody AlunoDisciplinaPagaResquestDTO dto) {
        return alunoService.adicionarConcluinte(dto);
    }

    @DeleteMapping("/disciplina/concluinte")
    public ResponseEntity<Void> revogarConclusao(@RequestBody AlunoDisciplinaPagaResquestDTO dto) {
        return alunoService.revogarConclusao(dto);
    }

}
