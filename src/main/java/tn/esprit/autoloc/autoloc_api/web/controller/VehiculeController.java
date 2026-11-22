package tn.esprit.autoloc.autoloc_api.web.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autoloc_api.domain.Vehicule;
import tn.esprit.autoloc.autoloc_api.service.IVehiculeService;
import tn.esprit.autoloc.autoloc_api.web.dto.AffectationEquipementRequest;
import tn.esprit.autoloc.autoloc_api.web.dto.VehiculeEquipementResponseDTO;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @GetMapping
    public List<Vehicule> findAll() {
        return vehiculeService.findAll();
    }

    @GetMapping("/{id}")
    public Vehicule findById(@PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return vehiculeService.create(vehicule);
    }

    @PostMapping("/affectation-equipement")
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeEquipementResponseDTO affecterEquipementAVehicule(
            @Valid @RequestBody AffectationEquipementRequest request) {
        return vehiculeService.affecterEquipementAVehicule(request);
    }

    @PutMapping("/{id}")
    public Vehicule update(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        return vehiculeService.update(id, vehicule);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}