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
public class Coordenador {

    @Id
    protected Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Professor professor;

    @OneToMany
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
}
