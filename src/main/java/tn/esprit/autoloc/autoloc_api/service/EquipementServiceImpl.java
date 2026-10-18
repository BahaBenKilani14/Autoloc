package tn.esprit.autoloc.autoloc_api.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autoloc_api.domain.Equipement;
import tn.esprit.autoloc.autoloc_api.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autoloc_api.repository.IEquipementRepository;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;

    @Override
    public Equipement create(Equipement equipement) {
        if (equipement.getIdEquipement() != null) {
            throw new IllegalArgumentException("Un nouvel équipement ne doit pas avoir d'identifiant");
        }
        verifier(equipement);
        return equipementRepository.save(equipement);
    }

    @Override
    public Equipement findById(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }

    @Override
    public List<Equipement> findAll() {
        return equipementRepository.findAll();
    }

    @Override
    public Equipement update(Long id, Equipement equipement) {
        Equipement existant = findById(id);
        verifier(equipement);
        existant.setNom(equipement.getNom());
        existant.setDescription(equipement.getDescription());
        existant.setQuantite(equipement.getQuantite());
        return equipementRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        equipementRepository.deleteById(id);
    }

    private void verifier(Equipement equipement) {
        if (equipement.getNom() == null || equipement.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'équipement est obligatoire");
        }
        if (equipement.getQuantite() != null && equipement.getQuantite() < 0) {
            throw new IllegalArgumentException("La quantité ne peut pas être négative");
        }
    }
}