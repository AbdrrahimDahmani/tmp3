package ma.enset.virementservice.clients;

import ma.enset.virementservice.models.Beneficiaire;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "beneficiaire-service")
public interface BeneficiaireRestClient {
    
    @GetMapping("/api/beneficiaires/{id}")
    Beneficiaire getBeneficiaireById(@PathVariable Long id);
}
