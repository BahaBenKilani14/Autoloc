package tn.esprit.autoloc.autoloc_api.service;

import java.util.List;
import tn.esprit.autoloc.autoloc_api.domain.Maintenance;
import tn.esprit.autoloc.autoloc_api.web.dto.AffectationMaintenanceRequest;
import tn.esprit.autoloc.autoloc_api.web.dto.MaintenanceResponseDTO;

public interface IMaintenanceService {
    Maintenance create(Maintenance maintenance);
    Maintenance findById(Long id);
    List<Maintenance> findAll();
    Maintenance update(Long id, Maintenance maintenance);
    void deleteById(Long id);
    MaintenanceResponseDTO affecterMaintenanceAVehicule(AffectationMaintenanceRequest request);
    List<Maintenance> findMaintenancesByVehiculeId(Long idVehicule);
}