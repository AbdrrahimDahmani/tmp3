package ma.enset.virementservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.enset.virementservice.clients.BeneficiaireRestClient;
import ma.enset.virementservice.entities.Virement;
import ma.enset.virementservice.repositories.VirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/virements")
@Tag(name = "Virement", description = "API de gestion des virements bancaires")
public class VirementController {
    
    @Autowired
    private VirementRepository virementRepository;
    
    @Autowired
    private BeneficiaireRestClient beneficiaireRestClient;
    
    @GetMapping
    @Operation(summary = "Récupérer tous les virements")
    public List<Virement> getAllVirements() {
        List<Virement> virements = virementRepository.findAll();
        virements.forEach(virement -> {
            try {
                virement.setBeneficiaire(beneficiaireRestClient.getBeneficiaireById(virement.getBeneficiaireId()));
            } catch (Exception e) {
                virement.setBeneficiaire(null);
            }
        });
        return virements;
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un virement par ID")
    public Virement getVirementById(@PathVariable Long id) {
        Virement virement = virementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Virement non trouvé"));
        try {
            virement.setBeneficiaire(beneficiaireRestClient.getBeneficiaireById(virement.getBeneficiaireId()));
        } catch (Exception e) {
            virement.setBeneficiaire(null);
        }
        return virement;
    }
    
    @PostMapping
    @Operation(summary = "Créer un nouveau virement")
    public Virement createVirement(@RequestBody Virement virement) {
        return virementRepository.save(virement);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un virement")
    public Virement updateVirement(@PathVariable Long id, @RequestBody Virement virement) {
        virement.setId(id);
        return virementRepository.save(virement);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un virement")
    public void deleteVirement(@PathVariable Long id) {
        virementRepository.deleteById(id);
    }
    
    @GetMapping("/beneficiaire/{beneficiaireId}")
    @Operation(summary = "Récupérer les virements par bénéficiaire")
    public List<Virement> getVirementsByBeneficiaire(@PathVariable Long beneficiaireId) {
        return virementRepository.findByBeneficiaireId(beneficiaireId);
    }
}
