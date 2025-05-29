package br.edu.ifpb.sgm.projeto_sgm.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Instituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnpj;
    private String email;

    @OneToMany(mappedBy = "instituicao")
    private List<Curso> cursos;

    @OneToMany(mappedBy = "instituicao")
    private List<ProcessoSeletivo> processos;

    @OneToMany(mappedBy = "instituicao")
    private List<Pessoa> pessoas;
}
