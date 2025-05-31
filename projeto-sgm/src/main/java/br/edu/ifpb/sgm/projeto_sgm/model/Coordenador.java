package br.edu.ifpb.sgm.projeto_sgm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coordenador")
public class Coordenador extends Professor {

    @OneToOne(optional = false)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
}
