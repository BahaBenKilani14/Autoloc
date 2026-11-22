package tn.esprit.autoloc.autoloc_api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autoloc_api.domain.Equipement;
import tn.esprit.autoloc.autoloc_api.domain.Vehicule;
import tn.esprit.autoloc.autoloc_api.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autoloc_api.repository.IEquipementRepository;
import tn.esprit.autoloc.autoloc_api.repository.IVehiculeRepository;
import tn.esprit.autoloc.autoloc_api.web.dto.AffectationEquipementRequest;
import tn.esprit.autoloc.autoloc_api.web.dto.VehiculeEquipementResponseDTO;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IEquipementRepository equipementRepository;

    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouveau véhicule ne doit pas avoir d'identifiant");
        }
        verifier(vehicule);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        Vehicule existant = findById(id);
        verifier(vehicule);
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        existant.setAgence(vehicule.getAgence());
        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    @Override
    public VehiculeEquipementResponseDTO affecterEquipementAVehicule(AffectationEquipementRequest request) {
        Vehicule vehicule = vehiculeRepository.findById(request.getIdVehicule())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", request.getIdVehicule()));

        Equipement equipement = equipementRepository.findById(request.getIdEquipement())
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", request.getIdEquipement()));

        if (vehicule.getEquipements() == null) {
            vehicule.setEquipements(new ArrayList<>());
        }
        if (!vehicule.getEquipements().contains(equipement)) {
            vehicule.getEquipements().add(equipement);
        }

        Vehicule saved = vehiculeRepository.save(vehicule);

        Set<String> nomsEquipements = saved.getEquipements() == null
                ? Set.of()
                : saved.getEquipements().stream()
                  .map(Equipement::getNom)
                  .collect(Collectors.toSet());

        return new VehiculeEquipementResponseDTO(
                saved.getIdVehicule(),
                saved.getImmatriculation(),
                saved.getMarque(),
                saved.getModele(),
                nomsEquipements
        );
    }

    private void verifier(Vehicule vehicule) {
        if (vehicule.getImmatriculation() == null || vehicule.getImmatriculation().trim().isEmpty()) {
            throw new IllegalArgumentException("L'immatriculation du véhicule est obligatoire");
        }
        if (vehicule.getTarifJournalier() != null && vehicule.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif");
        }
    }
}