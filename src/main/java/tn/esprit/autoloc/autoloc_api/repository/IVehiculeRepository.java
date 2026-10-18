package tn.esprit.autoloc.autoloc_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autoloc_api.domain.Vehicule;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {
}