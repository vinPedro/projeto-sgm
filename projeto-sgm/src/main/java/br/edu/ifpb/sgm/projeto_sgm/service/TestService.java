package br.edu.ifpb.sgm.projeto_sgm.service;

import br.edu.ifpb.sgm.projeto_sgm.dto.AlunoRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProfessorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.*;
import br.edu.ifpb.sgm.projeto_sgm.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static br.edu.ifpb.sgm.projeto_sgm.util.Constants.*;

@Service
public class TestService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoServiceImp alunoServiceImp;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ProfessorServiceImp professorServiceImp;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProcessoSeletivoRepository processoSeletivoRepository;

    @Autowired
    private MonitoriaRepository monitoriaRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @PostConstruct
    private void initRoles() {
        if (roleRepository.findByRole("ROLE_" + ADMIN).isEmpty()) {
            roleRepository.save(new Role(null, "ROLE_" + ADMIN));
        }
        if (roleRepository.findByRole("ROLE_" + COORDENADOR).isEmpty()) {
            roleRepository.save(new Role(null, "ROLE_" + COORDENADOR));
        }
        if (roleRepository.findByRole("ROLE_" + DOCENTE).isEmpty()) {
            roleRepository.save(new Role(null, "ROLE_" + DOCENTE));
        }
        if (roleRepository.findByRole("ROLE_" + DISCENTE).isEmpty()) {
            roleRepository.save(new Role(null, "ROLE_" + DISCENTE));
        }
        if (roleRepository.findByRole("ROLE_" + MONITOR).isEmpty()) {
            roleRepository.save(new Role(null, "ROLE_" + MONITOR));
        }
    }

    private String encriptPassword(String senha) {
        return passwordEncoder.encode(senha);
    }

    @Transactional
    public void insertTestData() {
        Instituicao instituicao = new Instituicao();
        instituicao.setNome("Instituição Teste");
        instituicao.setCnpj("12.345.678/0001-99");
        instituicao.setEmail("contato@instituicaoteste.com");
        instituicaoRepository.save(instituicao);


        Pessoa admin = new Pessoa();
        admin.setNome("Admin SGM");
        admin.setCpf("000.000.000-00");
        admin.setEmail("admin@sgm.com");
        admin.setEmailAcademico("admin.academico@sgm.com");
        admin.setMatricula("admin");
        admin.setSenha(encriptPassword("admin123"));
        admin.setInstituicao(instituicao);
        admin.setRoles((roleRepository.findAll()));
        pessoaRepository.save(admin);

        Pessoa coordenador = new Pessoa();
        coordenador.setNome("Coordenador Teste");
        coordenador.setCpf("111.111.111-11");
        coordenador.setEmail("coordenador@sgm.com");
        coordenador.setEmailAcademico("coordenador.academico@sgm.com");
        coordenador.setMatricula("coordenador");
        coordenador.setSenha(encriptPassword("coord123"));
        coordenador.setInstituicao(instituicao);
        coordenador.setRoles(List.of(roleRepository.findByRole("ROLE_" + COORDENADOR).orElseThrow(() -> new RuntimeException("Role COORDENADOR não encontrada!"))));
        pessoaRepository.save(coordenador);

        Curso curso = new Curso();
        curso.setNome("Curso Teste");
        curso.setDuracao(4);
        curso.setInstituicao(instituicao);
        curso.setNivel(NivelCurso.GRADUACAO);
        cursoRepository.save(curso);

        Disciplina disciplina = new Disciplina();
        disciplina.setNome("Disciplina Teste");
        disciplina.setCargaHoraria(60);
        disciplina.setCurso(curso);
        disciplinaRepository.save(disciplina);

        Pessoa pessoaProfessor = new Pessoa();
        pessoaProfessor.setNome("Professor Teste");
        pessoaProfessor.setEmail("professor@instituicaoteste.com");
        pessoaProfessor.setEmailAcademico("professorAcademico@instituicaoteste.com");
        pessoaProfessor.setCpf("123.456.789-00");
        pessoaProfessor.setMatricula("12374");
        pessoaProfessor.setSenha(encriptPassword("senha"));
        pessoaProfessor.setInstituicao(instituicao);
        pessoaProfessor.setRoles(List.of(roleRepository.findByRole("ROLE_" + DOCENTE).orElseThrow(() -> new RuntimeException("Role DOCENTE não encontrada!"))));
        Professor professor = new Professor();
        professor.setPessoa(pessoaProfessor);
        professor.getDisciplinas().add(disciplina);
        professorRepository.save(professor);


        AlunoRequestDTO alunoDTO = new AlunoRequestDTO();
        alunoDTO.setNome("Joca Teste");
        alunoDTO.setCpf("222.222.222-22");
        alunoDTO.setEmail("joca@gmail.com");
        alunoDTO.setEmailAcademico("joca.academico@gmail.com");
        alunoDTO.setMatricula("123456");
        alunoDTO.setSenha(encriptPassword("senhaAluno"));
        alunoDTO.setInstituicaoId(instituicao.getId());
        alunoServiceImp.salvar(alunoDTO);

        ProcessoSeletivo processoSeletivo = new ProcessoSeletivo();
        processoSeletivo.setInstituicao(instituicao);
        processoSeletivo.setNumero("PS001");
        processoSeletivo.setInicio(LocalDate.now());
        processoSeletivo.setFim(LocalDate.now().plusMonths(2));
        processoSeletivoRepository.save(processoSeletivo);


        Monitoria monitoria = new Monitoria();
        monitoria.setDisciplina(disciplina);
        monitoria.setProfessor(professor);
        monitoria.setNumeroVaga(10);
        monitoria.setNumeroVagaBolsa(2);
        monitoria.setCargaHoraria(60);
        monitoria.setProcessoSeletivo(processoSeletivo);
        monitoriaRepository.save(monitoria);

        Atividade atividade = new Atividade();
        atividade.setDataHora(LocalDateTime.now());
        atividade.setDescricao("atividade teste");
        atividade.setMonitoria(monitoria);
        atividadeRepository.save(atividade);
    }
}