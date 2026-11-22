package tn.esprit.autoloc.autoloc_api.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autoloc_api.domain.Paiement;
import tn.esprit.autoloc.autoloc_api.service.IPaiementService;

import java.util.List;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
@Tag(name = "Paiements", description = "Lecture des paiements (gérés via les contrats)")
public class PaiementController {

    private final IPaiementService paiementService;

    @GetMapping
    @Operation(summary = "Lister tous les paiements")
    public List<Paiement> findAll() {
        return paiementService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un paiement par son ID")
    public Paiement findById(@PathVariable Long id) {
        return paiementService.findById(id);
    }
}