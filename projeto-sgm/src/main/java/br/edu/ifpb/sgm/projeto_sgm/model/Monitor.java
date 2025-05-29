package br.edu.ifpb.sgm.projeto_sgm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Monitor extends Aluno{

    @ManyToMany
    private List<Disciplina> disciplinaMonitoria;

    @OneToMany(mappedBy = "monitor")
    private List<Atividade> atividades;
}
