package br.edu.ifpb.sgm.projeto_sgm.service;

import br.edu.ifpb.sgm.projeto_sgm.model.*;
import br.edu.ifpb.sgm.projeto_sgm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TestService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

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

    public void insertTestData() {
        // 1. Crie a Instituição
        Instituicao instituicao = new Instituicao();
        instituicao.setNome("Instituição Teste");
        instituicao.setCnpj("12.345.678/0001-99");
        instituicao.setEmail("contato@instituicaoteste.com");
        instituicaoRepository.save(instituicao);

        // 2. Crie o Professor (agora com CPF)
        Professor professor = new Professor();
        professor.setNome("Professor Teste");
        professor.setEmail("professor@instituicaoteste.com");
        professor.setCpf("123.456.789-00"); // Adicionando o CPF
        professor.setInstituicao(instituicao); // Associação com a instituição
        professorRepository.save(professor);

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
        disciplina.setCurso(curso); // Associação com o curso
        disciplina.setProfessor(professor); // Associação com o professor
        disciplinaRepository.save(disciplina);

        // 5. Crie o Aluno
        Aluno aluno = new Aluno();
        aluno.setNome("Joca Teste");
        aluno.setMatricula("123456");
        aluno.setCpf("111.222.333-44");
        aluno.setEmail("deda0048@gmail.com");
        aluno.setInstituicao(instituicao); // Associação com a instituição
        alunoRepository.save(aluno);

        // 6. Crie o Processo Seletivo
        ProcessoSeletivo processoSeletivo = new ProcessoSeletivo();
        processoSeletivo.setInstituicao(instituicao); // Associação com a instituição
        processoSeletivo.setNumero("PS001");
        processoSeletivo.setInicio(LocalDate.now());
        processoSeletivo.setFim(LocalDate.now().plusMonths(2));
        processoSeletivoRepository.save(processoSeletivo);

        // 7. Crie a Monitoria
        Monitoria monitoria = new Monitoria();
        monitoria.setDisciplina(disciplina);  // Associação com a disciplina
        monitoria.setProfessor(professor);   // Associação com o professor
        monitoria.setNumeroVaga(10);         // Definindo o número de vagas
        monitoria.setNumeroVagaBolsa(2);     // Definindo o número de vagas de bolsa
        monitoria.setCargaHoraria(60);       // Definindo a carga horária
        monitoria.setProcessoSeletivo(processoSeletivo);  // Associação com o processo seletivo
        monitoriaRepository.save(monitoria);

        // 8. Insira o Aluno na Monitoria (como "inscrito")
        monitoria.getInscritos().add(aluno); // Adiciona o aluno à lista de inscritos
        monitoriaRepository.save(monitoria); // Salva a monitoria novamente para persistir a alteração

        // 9. Insira o Aluno na Monitoria (como "selecionado")
        monitoria.getSelecionados().add(aluno); // Adiciona o aluno à lista de selecionados
        monitoriaRepository.save(monitoria); // Salva a monitoria novamente para persistir a alteração

        // 10. Criar e associar atividades à monitoria (opcional)
        Atividade atividade = new Atividade();
        atividade.setMonitoria(monitoria); // Associação com a monitoria
        atividade.setDescricao("Atividade de Monitoria Teste");
        atividade.setDataHora(LocalDateTime.now());
        atividadeRepository.save(atividade);

        System.out.println("Dados de teste inseridos!");
    }




}

