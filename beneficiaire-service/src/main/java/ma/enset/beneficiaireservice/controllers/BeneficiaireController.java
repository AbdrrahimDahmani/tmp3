package ma.enset.beneficiaireservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.enset.beneficiaireservice.entities.Beneficiaire;
import ma.enset.beneficiaireservice.repositories.BeneficiaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beneficiaires")
@Tag(name = "Beneficiaire", description = "API de gestion des bénéficiaires")
public class BeneficiaireController {
    
    @Autowired
    private BeneficiaireRepository beneficiaireRepository;
    
    @GetMapping
    @Operation(summary = "Récupérer tous les bénéficiaires")
    public List<Beneficiaire> getAllBeneficiaires() {
        return beneficiaireRepository.findAll();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un bénéficiaire par ID")
    public Beneficiaire getBeneficiaireById(@PathVariable Long id) {
        return beneficiaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bénéficiaire non trouvé"));
    }
    
    @PostMapping
    @Operation(summary = "Créer un nouveau bénéficiaire")
    public Beneficiaire createBeneficiaire(@RequestBody Beneficiaire beneficiaire) {
        return beneficiaireRepository.save(beneficiaire);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un bénéficiaire")
    public Beneficiaire updateBeneficiaire(@PathVariable Long id, @RequestBody Beneficiaire beneficiaire) {
        beneficiaire.setId(id);
        return beneficiaireRepository.save(beneficiaire);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un bénéficiaire")
    public void deleteBeneficiaire(@PathVariable Long id) {
        beneficiaireRepository.deleteById(id);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Rechercher des bénéficiaires par nom")
    public List<Beneficiaire> searchBeneficiaires(@RequestParam String nom) {
        return beneficiaireRepository.findByNomContaining(nom);
    }
}
