package br.edu.ifpb.sgm.projeto_sgm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Professor extends Pessoa{

    @OneToMany(mappedBy = "professor")
    private List<Disciplina> disciplinas;
}
