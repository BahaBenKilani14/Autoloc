package tn.esprit.autoloc.autoloc_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autoloc_api.domain.Maintenance;

import java.util.List;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findByVehicule_IdVehicule(Long idVehicule);
}