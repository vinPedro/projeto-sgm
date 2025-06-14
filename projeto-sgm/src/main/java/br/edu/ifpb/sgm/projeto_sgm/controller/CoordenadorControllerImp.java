package br.edu.ifpb.sgm.projeto_sgm.controller;

import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.service.CoordenadorServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coordenadores")
public class CoordenadorControllerImp {

    @Autowired
    private CoordenadorServiceImp coordenadorService;

    @PostMapping
    public ResponseEntity<CoordenadorResponseDTO> criar(@RequestBody CoordenadorRequestDTO dto) {
        return coordenadorService.salvar(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoordenadorResponseDTO> buscarPorId(@PathVariable Long id) {
        return coordenadorService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<CoordenadorResponseDTO>> listarTodos() {
        return coordenadorService.listarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoordenadorResponseDTO> atualizar(@PathVariable Long id, @RequestBody CoordenadorRequestDTO dto) {
        return coordenadorService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return coordenadorService.deletar(id);
    }
}
