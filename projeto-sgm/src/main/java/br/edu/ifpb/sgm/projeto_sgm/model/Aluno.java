package br.edu.ifpb.sgm.projeto_sgm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Aluno extends Pessoa{

    @ManyToMany
    private List<Disciplina> disciplinasPagas;
}
