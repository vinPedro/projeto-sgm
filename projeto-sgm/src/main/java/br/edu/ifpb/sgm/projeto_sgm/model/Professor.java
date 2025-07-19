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
public class Professor {

    @Id
    protected Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Pessoa pessoa;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private Coordenador coordenador;

    @OneToMany(mappedBy = "professor")
    @JoinColumn(name = "professor_id")
    private List<Disciplina> disciplinas = new ArrayList<>();
}
