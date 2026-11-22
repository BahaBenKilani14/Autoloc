package tn.esprit.autoloc.autoloc_api.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AffectationEquipementRequest {

    @NotNull(message = "L'identifiant du véhicule est obligatoire")
    private Long idVehicule;

    @NotNull(message = "L'identifiant de l'équipement est obligatoire")
    private Long idEquipement;
}