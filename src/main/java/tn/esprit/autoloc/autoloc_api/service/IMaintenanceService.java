package tn.esprit.autoloc.autoloc_api.service;

import java.util.List;
import tn.esprit.autoloc.autoloc_api.domain.Maintenance;

public interface IMaintenanceService {
    Maintenance create(Maintenance maintenance);
    Maintenance findById(Long id);
    List<Maintenance> findAll();
    Maintenance update(Long id, Maintenance maintenance);
    void deleteById(Long id);
}