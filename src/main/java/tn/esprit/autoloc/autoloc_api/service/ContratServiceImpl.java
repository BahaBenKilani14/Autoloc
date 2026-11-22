package tn.esprit.autoloc.autoloc_api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.autoloc_api.domain.Contrat;
import tn.esprit.autoloc.autoloc_api.domain.Reservation;
import tn.esprit.autoloc.autoloc_api.domain.Vehicule;
import tn.esprit.autoloc.autoloc_api.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autoloc_api.repository.IContratRepository;
import tn.esprit.autoloc.autoloc_api.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Contrat create(Contrat contrat) {
        if (contrat.getIdContrat() != null) {
            throw new IllegalArgumentException("Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        verifier(contrat);
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat findById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat contrat) {
        Contrat existant = findById(id);
        verifier(contrat);
        existant.setDateSignature(contrat.getDateSignature());
        existant.setMontantTotal(contrat.getMontantTotal());
        existant.setValide(contrat.getValide());
        return contratRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrat> findContratsByVehiculeId(Long idVehicule) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule));

        if (vehicule.getReservations() == null) {
            return List.of();
        }

        return vehicule.getReservations().stream()
                .map(Reservation::getContrat)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

    private void verifier(Contrat contrat) {
        if (contrat.getMontantTotal() == null
                || contrat.getMontantTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le montant total ne doit pas être négatif");
        }
    }
}