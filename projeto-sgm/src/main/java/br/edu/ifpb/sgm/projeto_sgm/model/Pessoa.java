package br.edu.ifpb.sgm.projeto_sgm.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(length = 14, nullable = false, unique = true)
    protected String cpf;

    @Column(nullable = false)
    protected String nome;

    @Column(nullable = false, unique = true)
    protected String email;
    @Column(unique = true)
    protected String emailAcademico;

    @ManyToOne(optional = false)
    protected Instituicao instituicao;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private Aluno aluno;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private Professor professor;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private Monitor monitor;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private Coordenador coordenador;
}
