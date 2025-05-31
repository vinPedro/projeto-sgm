package br.edu.ifpb.sgm.projeto_sgm.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessoSeletivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate inicio;
    private LocalDate fim;
    private String numero;

    @ManyToOne
    private Instituicao instituicao;

    @OneToMany(mappedBy = "processoSeletivo")
    private List<Monitoria> monitorias = new ArrayList<>();
}
