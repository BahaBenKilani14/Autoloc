package tn.esprit.autoloc.autoloc_api.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.autoloc_api.domain.Maintenance;
import tn.esprit.autoloc.autoloc_api.domain.Vehicule;
import tn.esprit.autoloc.autoloc_api.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autoloc_api.repository.IMaintenanceRepository;
import tn.esprit.autoloc.autoloc_api.repository.IVehiculeRepository;
import tn.esprit.autoloc.autoloc_api.web.dto.AffectationMaintenanceRequest;
import tn.esprit.autoloc.autoloc_api.web.dto.MaintenanceResponseDTO;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Maintenance create(Maintenance maintenance) {
        if (maintenance.getIdMaintenance() != null) {
            throw new IllegalArgumentException("Une nouvelle maintenance ne doit pas avoir d'identifiant");
        }
        verifier(maintenance);
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance findById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    @Override
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance update(Long id, Maintenance maintenance) {
        Maintenance existante = findById(id);
        verifier(maintenance);
        existante.setDateMaintenance(maintenance.getDateMaintenance());
        existante.setDescription(maintenance.getDescription());
        existante.setCout(maintenance.getCout());
        existante.setVehicule(maintenance.getVehicule());
        return maintenanceRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }

    @Override
    public MaintenanceResponseDTO affecterMaintenanceAVehicule(AffectationMaintenanceRequest request) {
        Vehicule vehicule = vehiculeRepository.findById(request.getIdVehicule())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", request.getIdVehicule()));

        Maintenance maintenance = new Maintenance();
        maintenance.setDateMaintenance(request.getDateMaintenance());
        maintenance.setDescription(request.getDescription());
        maintenance.setCout(request.getCout());
        maintenance.setVehicule(vehicule);

        Maintenance saved = maintenanceRepository.save(maintenance);

        return new MaintenanceResponseDTO(
                saved.getIdMaintenance(),
                saved.getDateMaintenance(),
                saved.getDescription(),
                saved.getCout(),
                vehicule.getIdVehicule(),
                vehicule.getImmatriculation()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Maintenance> findMaintenancesByVehiculeId(Long idVehicule) {
        if (!vehiculeRepository.existsById(idVehicule)) {
            throw new ResourceNotFoundException("Vehicule", idVehicule);
        }
        return maintenanceRepository.findByVehicule_IdVehicule(idVehicule);
    }

    private void verifier(Maintenance maintenance) {
        if (maintenance.getCout() != null && maintenance.getCout().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le coût de maintenance ne doit pas être négatif");
        }
    }
}