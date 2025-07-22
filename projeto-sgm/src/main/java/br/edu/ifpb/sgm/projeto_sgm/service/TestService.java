package br.edu.ifpb.sgm.projeto_sgm.service;

import br.edu.ifpb.sgm.projeto_sgm.dto.AlunoRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.PessoaRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProfessorRequestDTO;
import br.edu.ifpb.sgm.projeto_sgm.dto.ProfessorResponseDTO;
import br.edu.ifpb.sgm.projeto_sgm.model.*;
import br.edu.ifpb.sgm.projeto_sgm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private  ProfessorServiceImp professorServiceImp;

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
    protected PasswordEncoder passwordEncoder;

    private void encriptPassword(AlunoRequestDTO pessoa) {
        String encodedPassword = passwordEncoder.encode(pessoa.getSenha());
        pessoa.setSenha(encodedPassword);
    }

    private void encriptPassword(ProfessorRequestDTO pessoa) {
        String encodedPassword = passwordEncoder.encode(pessoa.getSenha());
        pessoa.setSenha(encodedPassword);
    }

    public void insertTestData() {
        // 1. Crie a Instituição
        Instituicao instituicao = new Instituicao();
        instituicao.setNome("Instituição Teste");
        instituicao.setCnpj("12.345.678/0001-99");
        instituicao.setEmail("contato@instituicaoteste.com");
        instituicaoRepository.save(instituicao);

        // 2. Crie o Professor (agora com CPF)
        ProfessorRequestDTO professor = new ProfessorRequestDTO();
        professor.setNome("Professor Teste");
        professor.setEmail("professor@instituicaoteste.com");
        professor.setEmailAcademico("professorAcademico@instituicaoteste.com");
        professor.setCpf("123.456.789-00"); // Adicionando o CPF
        professor.setInstituicaoId(1L);
        professor.setMatricula("12374");
        professor.setSenha("senha");// Associação com a instituição
        encriptPassword(professor);
        professorServiceImp.salvar(professor);

        // 3. Crie o Curso
        Curso curso = new Curso();
        curso.setNome("Curso Teste");
        curso.setDuracao(4);
        curso.setInstituicao(instituicao); // Associação com a instituição
        curso.setNivel(NivelCurso.GRADUACAO); // Nível do curso
        cursoRepository.save(curso);

        // 4. Crie a Disciplina
        Disciplina disciplina = new Disciplina();
        disciplina.setNome("Disciplina Teste");
        disciplina.setCargaHoraria(60);
        disciplina.setCurso(curso); // Associação com o curso// Associação com o professor
        disciplinaRepository.save(disciplina);

        // 5. Crie o Aluno


        AlunoRequestDTO alunoRequestDTO =  new AlunoRequestDTO();


        alunoRequestDTO.setNome("Joca Teste");

        alunoRequestDTO.setCpf("111.222.333-44");
        alunoRequestDTO.setEmail("deda0048@gmail.com");
        alunoRequestDTO.setEmailAcademico("dedaAcademico0048@gmail.com");
        alunoRequestDTO.setInstituicaoId(1L); // Associação com a instituição
        alunoRequestDTO.setMatricula("123456");
        alunoRequestDTO.setSenha("senhaAluno");
        encriptPassword(alunoRequestDTO);
        alunoServiceImp.salvar(alunoRequestDTO);



        // 6. Crie o Processo Seletivo
        ProcessoSeletivo processoSeletivo = new ProcessoSeletivo();
        processoSeletivo.setInstituicao(instituicao); // Associação com a instituição
        processoSeletivo.setNumero("PS001");
        processoSeletivo.setInicio(LocalDate.now());
        processoSeletivo.setFim(LocalDate.now().plusMonths(2));
        processoSeletivoRepository.save(processoSeletivo);

        // 7. Crie a Monitoria
        Professor professor1 = professorRepository.findById(1L).orElseThrow();

        Monitoria monitoria = new Monitoria();
        monitoria.setDisciplina(disciplina);  // Associação com a disciplina
        monitoria.setProfessor(professor1);   // Associação com o professor
        monitoria.setNumeroVaga(10);         // Definindo o número de vagas
        monitoria.setNumeroVagaBolsa(2);     // Definindo o número de vagas de bolsa
        monitoria.setCargaHoraria(60);       // Definindo a carga horária
        monitoria.setProcessoSeletivo(processoSeletivo);  // Associação com o processo seletivo
        monitoriaRepository.save(monitoria);


        Atividade atividade = new Atividade();
        atividade.setDataHora(LocalDateTime.now());
        atividade.setDescricao("atividade teste");
        atividade.setMonitoria(monitoria);
        atividadeRepository.save(atividade);
        }

}

