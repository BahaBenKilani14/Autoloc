package tn.esprit.autoloc.autoloc_api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "equipement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipement;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private Integer quantite;

    @ManyToMany(mappedBy = "equipements")
    private List<Vehicule> vehicules;
}