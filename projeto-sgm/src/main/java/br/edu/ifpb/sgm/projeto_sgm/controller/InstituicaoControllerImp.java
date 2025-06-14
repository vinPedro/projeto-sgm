package br.edu.ifpb.sgm.projeto_sgm.controller;

import br.edu.ifpb.sgm.projeto_sgm.dto.InstituicaoRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.InstituicaoResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.service.InstituicaoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instituicoes")
public class InstituicaoControllerImp {

    @Autowired
    private InstituicaoServiceImp instituicaoService;

    @PostMapping
    public ResponseEntity<InstituicaoResponseDTO> criar(@RequestBody InstituicaoRequestDTO dto) {
        return instituicaoService.salvar(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return instituicaoService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<InstituicaoResponseDTO>> listarTodos() {
        return instituicaoService.listarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstituicaoResponseDTO> atualizar(@PathVariable Long id,
                                                            @RequestBody InstituicaoRequestDTO dto) {
        return instituicaoService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return instituicaoService.deletar(id);
    }
}
