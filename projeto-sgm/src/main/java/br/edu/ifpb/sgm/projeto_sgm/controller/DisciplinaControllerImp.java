package br.edu.ifpb.sgm.projeto_sgm.controller;

import br.edu.ifpb.sgm.projeto_sgm.dto.DisciplinaRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.DisciplinaResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.service.DisciplinaServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaControllerImp {

    @Autowired
    private DisciplinaServiceImp disciplinaService;

    @PostMapping
    public ResponseEntity<DisciplinaResponseDTO> criar(@RequestBody DisciplinaRequestDTO dto) {
        return disciplinaService.salvar(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> buscarPorId(@PathVariable Long id) {
        return disciplinaService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<DisciplinaResponseDTO>> listarTodos() {
        return disciplinaService.listarTodos();
    }

    @GetMapping("/null-Professores")
    public ResponseEntity<List<DisciplinaResponseDTO>> listarDisciplinasSemProfessor() {
        return disciplinaService.listarDisciplinasSemProfessor();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> atualizar(@PathVariable Long id, @RequestBody DisciplinaRequestDTO dto) {
        return disciplinaService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return disciplinaService.deletar(id);
    }

    @GetMapping("/professor/{id}")
    public ResponseEntity<List<DisciplinaResponseDTO>> buscarDisciplinasDeProfessor(@PathVariable Long id) {
        return disciplinaService.listarDisciplinasDeProfessor(id);
    }
}
