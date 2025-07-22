package br.edu.ifpb.sgm.projeto_sgm.service;

import br.edu.ifpb.sgm.projeto_sgm.dto.PessoaRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.PessoaResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.exception.InstituicaoNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.PessoaNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.mapper.PessoaMapper;
import br.edu.ifpb.sgm.projeto_sgm.model.Instituicao;
import br.edu.ifpb.sgm.projeto_sgm.model.Pessoa;
import br.edu.ifpb.sgm.projeto_sgm.model.Role;
import br.edu.ifpb.sgm.projeto_sgm.repository.InstituicaoRepository;
import br.edu.ifpb.sgm.projeto_sgm.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PessoaServiceImp implements UserDetailsService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaMapper pessoaMapper;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    public ResponseEntity<PessoaResponseDTO> salvar(PessoaRequestDTO dto) {
        Pessoa pessoa = pessoaMapper.toEntity(dto);
        pessoa.setInstituicao(buscarInstituicao(dto.getInstituicaoId()));
        Pessoa salva = pessoaRepository.save(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaMapper.toResponseDTO(salva));
    }

    public ResponseEntity<PessoaResponseDTO> buscarPorId(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(PessoaNotFoundException::new);
        return ResponseEntity.ok(pessoaMapper.toResponseDTO(pessoa));
    }

    public ResponseEntity<List<PessoaResponseDTO>> listarTodas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        List<PessoaResponseDTO> dtos = pessoas.stream()
                .map(pessoaMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    public ResponseEntity<PessoaResponseDTO> atualizar(Long id, PessoaRequestDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(PessoaNotFoundException::new);

        pessoaMapper.updatePessoaFromDto(dto, pessoa);

        if (dto.getInstituicaoId()!= null) {
            pessoa.setInstituicao(buscarInstituicao(dto.getInstituicaoId()));
        }

        Pessoa atualizada = pessoaRepository.save(pessoa);
        return ResponseEntity.ok(pessoaMapper.toResponseDTO(atualizada));
    }

    public ResponseEntity<Void> deletar(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(PessoaNotFoundException::new);
        pessoaRepository.delete(pessoa);
        return ResponseEntity.noContent().build();
    }

    private Instituicao buscarInstituicao(Long id) {
        return instituicaoRepository.findById(id)
                .orElseThrow(() -> new InstituicaoNotFoundException("Instituição com ID " + id + " não encontrada."));
    }

    public PessoaResponseDTO findDtoByMatricula(String matricula) {
        PessoaResponseDTO user = pessoaRepository.findByDTOMatricula(matricula)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        List<Role> roles = pessoaRepository.listRole(user.getId());
        user.setRoles(roles);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String matricula) throws UsernameNotFoundException {
        return pessoaRepository.findByMatricula(matricula)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
