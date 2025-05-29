package br.edu.ifpb.sgm.projeto_sgm.model;

import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String cpf;
    protected String matricula;
    protected String nome;
    protected String email;
    protected String emailAcademico;

    @ManyToOne
    protected Instituicao instituicao;
}
