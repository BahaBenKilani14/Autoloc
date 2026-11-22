package tn.esprit.autoloc.autoloc_api.web.dto;

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
public class MaintenanceResponseDTO {

    private Long idMaintenance;
    private LocalDate dateMaintenance;
    private String description;
    private BigDecimal cout;
    private Long idVehicule;
    private String immatriculation;
}