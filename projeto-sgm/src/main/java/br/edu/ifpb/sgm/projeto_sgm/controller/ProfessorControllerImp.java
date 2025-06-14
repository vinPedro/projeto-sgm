package br.edu.ifpb.sgm.projeto_sgm.controller;

import br.edu.ifpb.sgm.projeto_sgm.dto.ProfessorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProfessorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.service.ProfessorServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
public class ProfessorControllerImp {

    @Autowired
    private ProfessorServiceImp professorService;

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> criar(@RequestBody ProfessorRequestDTO dto) {
        return professorService.salvar(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> buscarPorId(@PathVariable Long id) {
        return professorService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> listarTodos() {
        return professorService.listarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> atualizar(@PathVariable Long id,
                                                          @RequestBody ProfessorRequestDTO dto) {
        return professorService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return professorService.deletar(id);
    }
}
