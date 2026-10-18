package tn.esprit.autoloc.autoloc_api.service;

import java.util.List;
import tn.esprit.autoloc.autoloc_api.domain.Equipement;

public interface IEquipementService {
    Equipement create(Equipement equipement);
    Equipement findById(Long id);
    List<Equipement> findAll();
    Equipement update(Long id, Equipement equipement);
    void deleteById(Long id);
}