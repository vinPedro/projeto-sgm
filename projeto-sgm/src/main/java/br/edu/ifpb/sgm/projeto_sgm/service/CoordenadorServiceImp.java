package br.edu.ifpb.sgm.projeto_sgm.service;

import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.CoordenadorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.exception.CoordenadorNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.CursoNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.exception.InstituicaoNotFoundException;
import br.edu.ifpb.sgm.projeto_sgm.mapper.CoordenadorMapper;
import br.edu.ifpb.sgm.projeto_sgm.model.Coordenador;
import br.edu.ifpb.sgm.projeto_sgm.model.Curso;
import br.edu.ifpb.sgm.projeto_sgm.model.Instituicao;
import br.edu.ifpb.sgm.projeto_sgm.repository.CoordenadorRepository;
import br.edu.ifpb.sgm.projeto_sgm.repository.CursoRepository;
import br.edu.ifpb.sgm.projeto_sgm.repository.InstituicaoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar as operações de CRUD para a entidade Coordenador.
 */
@Service
@Transactional
public class CoordenadorServiceImp {

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private CoordenadorMapper coordenadorMapper;

    @Autowired
    private EntityManager entityManager; // Usado para a operação de "remover cargo"

    /**
     * Cria uma nova entidade Coordenador (e consequentemente Pessoa e Professor) no banco de dados.
     * @param dto DTO com os dados para a criação do novo coordenador.
     * @return O Coordenador recém-criado.
     */
    public ResponseEntity<CoordenadorResponseDTO> salvar(CoordenadorRequestDTO dto) {
        coordenadorRepository.findByCurso_Id(dto.getCursoId()).ifPresent(c -> {
            throw new IllegalStateException("O curso com ID " + dto.getCursoId() + " já possui um coordenador.");
        });

        Curso curso = buscarCurso(dto.getCursoId());
        Instituicao instituicao = instituicaoRepository.findById(dto.getInstituicaoId())
                .orElseThrow(() -> new InstituicaoNotFoundException("Instituição com ID " + dto.getInstituicaoId() + " não encontrada."));

        Coordenador coordenador = new Coordenador();
        coordenador.setNome(dto.getNome());
        coordenador.setCpf(dto.getCpf());
        coordenador.setEmail(dto.getEmail());
        coordenador.setEmailAcademico(dto.getEmailAcademico());
        coordenador.setInstituicao(instituicao);
        coordenador.setCurso(curso);

        Coordenador salvo = coordenadorRepository.save(coordenador);
        return ResponseEntity.status(HttpStatus.CREATED).body(coordenadorMapper.toResponseDTO(salvo));
    }

    /**
     * Busca um Coordenador pelo seu ID.
     * @param id O ID do Coordenador.
     * @return O Coordenador encontrado.
     */
    public ResponseEntity<CoordenadorResponseDTO> buscarPorId(Long id) {
        Coordenador coordenador = coordenadorRepository.findById(id)
                .orElseThrow(() -> new CoordenadorNotFoundException("Coordenador com ID " + id + " não encontrado."));
        return ResponseEntity.ok(coordenadorMapper.toResponseDTO(coordenador));
    }

    /**
     * Lista todos os Coordenadores cadastrados.
     * @return Uma lista de todos os Coordenadores.
     */
    public ResponseEntity<List<CoordenadorResponseDTO>> listarTodos() {
        List<Coordenador> coordenadores = coordenadorRepository.findAll();
        List<CoordenadorResponseDTO> dtos = coordenadores.stream()
                .map(coordenadorMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Atualiza os dados de um Coordenador existente.
     * @param id O ID do Coordenador a ser atualizado.
     * @param dto DTO com os novos dados.
     * @return O Coordenador atualizado.
     */
    public ResponseEntity<CoordenadorResponseDTO> atualizar(Long id, CoordenadorRequestDTO dto) {
        Coordenador coordenador = coordenadorRepository.findById(id)
                .orElseThrow(() -> new CoordenadorNotFoundException("Coordenador com ID " + id + " não encontrado."));

        coordenador.setNome(dto.getNome());
        coordenador.setEmail(dto.getEmail());
        coordenador.setEmailAcademico(dto.getEmailAcademico());

        if (dto.getCursoId() != null) {
            coordenador.setCurso(buscarCurso(dto.getCursoId()));
        }
        if (dto.getInstituicaoId() != null){
            Instituicao instituicao = instituicaoRepository.findById(dto.getInstituicaoId())
                    .orElseThrow(() -> new InstituicaoNotFoundException("Instituição com ID " + dto.getInstituicaoId() + " não encontrada."));
            coordenador.setInstituicao(instituicao);
        }

        Coordenador atualizado = coordenadorRepository.save(coordenador);
        return ResponseEntity.ok(coordenadorMapper.toResponseDTO(atualizado));
    }

    /**
     * Remove o cargo de Coordenador de um Professor.
     * Esta operação deleta a entrada apenas da tabela 'coordenador'.
     *
     * @param id O ID do Coordenador cujo cargo será removido.
     * @return Resposta sem conteúdo (204 No Content).
     */
    public ResponseEntity<Void> deletar(Long id) {
        if (!coordenadorRepository.existsById(id)) {
            throw new CoordenadorNotFoundException("Coordenador com ID " + id + " não encontrado.");
        }

        // CORREÇÃO: Usando parâmetro posicional (?1) em vez de nomeado (:id)
        String sql = "DELETE FROM coordenador WHERE id = ?1";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, id); // Define o primeiro parâmetro (?) com o valor do id
        query.executeUpdate();

        return ResponseEntity.noContent().build();
    }

    private Curso buscarCurso(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new CursoNotFoundException("Curso com ID " + id + " não encontrado."));
    }
}