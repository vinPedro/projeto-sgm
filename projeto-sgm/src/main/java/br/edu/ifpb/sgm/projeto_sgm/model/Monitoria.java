package br.edu.ifpb.sgm.projeto_sgm.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Monitoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Considere tornar esse campo uma entidade Disciplina se quiser ligação forte
    private String disciplina;

    private int numeroVaga;
    private int numeroVagaBolsa;
    private int cargaHoraria;

    @ManyToOne
    private Professor professor;

    @ManyToMany
    @JoinTable(
        name = "monitoria_selecionados",
        joinColumns = @JoinColumn(name = "monitoria_id"),
        inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> selecionados = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "monitoria_inscritos",
        joinColumns = @JoinColumn(name = "monitoria_id"),
        inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> inscritos = new ArrayList<>();

    @ManyToOne
    private ProcessoSeletivo processoSeletivo;

    @OneToMany(mappedBy = "monitoria")
    private List<Atividade> atividades = new ArrayList<>();
}
