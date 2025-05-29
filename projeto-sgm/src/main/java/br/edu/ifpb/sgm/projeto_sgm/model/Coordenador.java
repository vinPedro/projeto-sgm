package br.edu.ifpb.sgm.projeto_sgm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Coordenador extends Professor{

    @OneToOne
    private Curso curso;
}
