package br.edu.ifpb.sgm.projeto_sgm.controller;

import br.edu.ifpb.sgm.projeto_sgm.dto.AtividadeRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.AtividadeResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.service.AtividadeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atividades")
public class AtividadeControllerImp {

    @Autowired
    private AtividadeServiceImp atividadeService;

    @PostMapping
    public ResponseEntity<AtividadeResponseDTO> criar(@RequestBody AtividadeRequestDTO dto) {
        return atividadeService.salvar(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtividadeResponseDTO> buscarPorId(@PathVariable Long id) {
        return atividadeService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<AtividadeResponseDTO>> listarTodas() {
        return atividadeService.listarTodas();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtividadeResponseDTO> atualizar(@PathVariable Long id, @RequestBody AtividadeRequestDTO dto) {
        return atividadeService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return atividadeService.deletar(id);
    }
}
