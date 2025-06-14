package br.edu.ifpb.sgm.projeto_sgm.controller;

import br.edu.ifpb.sgm.projeto_sgm.dto.MonitorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.MonitorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.service.MonitorServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monitores")
public class MonitorControllerImp {

    @Autowired
    private MonitorServiceImp monitorService;

    @PostMapping
    public ResponseEntity<MonitorResponseDTO> criar(@RequestBody MonitorRequestDTO dto) {
        return monitorService.salvar(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonitorResponseDTO> buscarPorId(@PathVariable Long id) {
        return monitorService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<MonitorResponseDTO>> listarTodos() {
        return monitorService.listarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonitorResponseDTO> atualizar(@PathVariable Long id,
                                                        @RequestBody MonitorRequestDTO dto) {
        return monitorService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return monitorService.deletar(id);
    }
}
