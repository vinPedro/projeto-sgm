package br.edu.ifpb.sgm.projeto_sgm.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Monitoria {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String disciplina;
    private int numeroVaga;
    private int numeroVagaBolsa;
    private int cargaHoraria;

    @ManyToOne
    private Professor professor;

    @ManyToMany
    private List<Aluno> selecionados;

    @ManyToMany
    private List<Aluno> inscritos;

    @ManyToOne
    private ProcessoSeletivo processoSeletivo;

    @OneToMany(mappedBy = "monitoria")
    private List<Atividade> atividades;
}
