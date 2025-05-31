package br.edu.ifpb.sgm.projeto_sgm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Instituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnpj;
    private String email;

    @OneToMany(mappedBy = "instituicao")
    private List<Curso> cursos = new ArrayList<>();

    @OneToMany(mappedBy = "instituicao")
    private List<ProcessoSeletivo> processos = new ArrayList<>();
}
