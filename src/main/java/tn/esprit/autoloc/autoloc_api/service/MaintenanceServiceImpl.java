package tn.esprit.autoloc.autoloc_api.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autoloc_api.domain.Maintenance;
import tn.esprit.autoloc.autoloc_api.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autoloc_api.repository.IMaintenanceRepository;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;

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

    private void verifier(Maintenance maintenance) {
        if (maintenance.getCout() != null && maintenance.getCout().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le coût de maintenance ne doit pas être négatif");
        }
    }
}