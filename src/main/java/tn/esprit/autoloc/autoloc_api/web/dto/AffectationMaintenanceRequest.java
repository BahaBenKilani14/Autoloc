package tn.esprit.autoloc.autoloc_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AffectationMaintenanceRequest {

    @NotNull(message = "L'identifiant du véhicule est obligatoire")
    private Long idVehicule;

    @NotNull(message = "La date de maintenance est obligatoire")
    private LocalDate dateMaintenance;

    @NotBlank(message = "La description est obligatoire")
    @Size(max = 255, message = "La description ne doit pas dépasser 255 caractères")
    private String description;

    @NotNull(message = "Le coût est obligatoire")
    @PositiveOrZero(message = "Le coût ne doit pas être négatif")
    private BigDecimal cout;
}