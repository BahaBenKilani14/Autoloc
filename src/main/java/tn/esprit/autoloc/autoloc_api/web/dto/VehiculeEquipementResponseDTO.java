package tn.esprit.autoloc.autoloc_api.web.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehiculeEquipementResponseDTO {

    private Long idVehicule;
    private String immatriculation;
    private String marque;
    private String modele;
    private Set<String> equipements;
}