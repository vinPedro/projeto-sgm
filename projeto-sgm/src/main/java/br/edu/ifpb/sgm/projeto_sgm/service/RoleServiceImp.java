package br.edu.ifpb.sgm.projeto_sgm.service;

import br.edu.ifpb.sgm.projeto_sgm.dto.RoleRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.RoleResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.mapper.RoleMapper;
import br.edu.ifpb.sgm.projeto_sgm.model.Role;
import br.edu.ifpb.sgm.projeto_sgm.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
public class RoleServiceImp {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    public ResponseEntity<RoleResponseDTO> criar(RoleRequestDTO dto) {
        Role role = roleMapper.toEntity(dto);
        Role salva = roleRepository.save(role);
        RoleResponseDTO response = roleMapper.toDTO(salva);
        return ResponseEntity.created(URI.create("/roles/" + response.getId())).body(response);
    }

    public ResponseEntity<RoleResponseDTO> buscarPorId(Long id) {
        return roleRepository.findById(id)
                .map(role -> ResponseEntity.ok(roleMapper.toDTO(role)))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<RoleResponseDTO>> listarTodas() {
        List<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok(roleMapper.toDTOList(roles));
    }

    public ResponseEntity<Void> deletar(Long id) {
        if (!roleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        roleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<RoleResponseDTO> atualizar(Long id, RoleRequestDTO dto) {
        return roleRepository.findById(id)
                .map(role -> {
                    roleMapper.updateEntityFromDTO(dto, role);
                    Role atualizado = roleRepository.save(role);
                    return ResponseEntity.ok(roleMapper.toDTO(atualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
