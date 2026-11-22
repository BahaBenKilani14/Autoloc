package tn.esprit.autoloc.autoloc_api.service;

import java.util.List;
import tn.esprit.autoloc.autoloc_api.domain.Vehicule;
import tn.esprit.autoloc.autoloc_api.web.dto.AffectationEquipementRequest;
import tn.esprit.autoloc.autoloc_api.web.dto.VehiculeEquipementResponseDTO;

public interface IVehiculeService {
    Vehicule create(Vehicule vehicule);
    Vehicule findById(Long id);
    List<Vehicule> findAll();
    Vehicule update(Long id, Vehicule vehicule);
    void deleteById(Long id);
    VehiculeEquipementResponseDTO affecterEquipementAVehicule(AffectationEquipementRequest request);
    List<Vehicule> findVehiculesByContratId(Long idContrat);
    List<Vehicule> findVehiculesByMaintenanceId(Long idMaintenance);
}