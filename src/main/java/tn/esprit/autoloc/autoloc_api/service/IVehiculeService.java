package tn.esprit.autoloc.autoloc_api.service;

import java.util.List;
import tn.esprit.autoloc.autoloc_api.domain.Vehicule;

public interface IVehiculeService {
    Vehicule create(Vehicule vehicule);
    Vehicule findById(Long id);
    List<Vehicule> findAll();
    Vehicule update(Long id, Vehicule vehicule);
    void deleteById(Long id);
}