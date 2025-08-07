package br.edu.ifpb.sgm.projeto_sgm.controller;

import br.edu.ifpb.sgm.projeto_sgm.dto.*;
import br.edu.ifpb.sgm.projeto_sgm.model.Professor;
import br.edu.ifpb.sgm.projeto_sgm.service.ProfessorServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
public class ProfessorControllerImp {

    @Autowired
    private ProfessorServiceImp professorService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> criar(@RequestBody ProfessorRequestDTO dto) {
        encriptPassword(dto);
        return professorService.salvar(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> buscarPorId(@PathVariable Long id) {
        return professorService.buscarPorId(id);
    }

    @GetMapping("/cadastros")
    public ResponseEntity<List<ProfessorResponseDTO>> listarTodos() {
        return professorService.listarTodos();
    }


    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> listarTodosCadastrados() {
        return professorService.listarTodosCadastrados();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> atualizar(@PathVariable Long id, @RequestBody ProfessorRequestDTO dto) {
        if(dto.getSenha() != null){
            encriptPassword(dto);
        }
        return professorService.atualizar(id, dto);
    }

    @PutMapping("/associar/{id}")
    public ResponseEntity<ProfessorResponseDTO> associar(@PathVariable Long id, @RequestBody ProfessorRequestDTO dto) {
        return professorService.associar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return professorService.deletar(id);
    }


    //Coordenadores
    @PostMapping("/coordenadores")
    public ResponseEntity<ProfessorResponseDTO> criarCoordenador(@RequestBody CoordenadorRequestDTO dto) {
        return professorService.salvarProfessorCoordenador(dto);
    }

    @GetMapping("/coordenadores/{id}")
    public ResponseEntity<CoordenadorResponseDTO> buscarCoordenadorPorId(@PathVariable Long id) {
        return professorService.buscarCoordenadorPorId(id);
    }

    @GetMapping("/coordenadores")
    public ResponseEntity<List<ProfessorResponseDTO>> listarTodosCoordenadores() {
        return professorService.buscarCoordenadores();
    }

    @GetMapping("/null-coordenacao")
    public ResponseEntity<List<ProfessorResponseDTO>> listarProfessoresNullCoordenacao() {
        return professorService.buscarProfessoresSemCoordenacao();
    }

    @PutMapping("/coordenadores/{id}")
    public ResponseEntity<ProfessorResponseDTO> atualizarCoordenador(@PathVariable Long id, @RequestBody CoordenadorRequestDTO dto) {
        return professorService.atualizarCoordenador(id, dto);
    }

    @DeleteMapping("/coordenadores/{id}")
    public ResponseEntity<Void> deletarCoordenador(@PathVariable Long id) {
        return professorService.deletarCoordenador(id);
    }

    private void encriptPassword(ProfessorRequestDTO dto) {
        String encodedPassword = passwordEncoder.encode(dto.getSenha());
        dto.setSenha(encodedPassword);
    }
}
