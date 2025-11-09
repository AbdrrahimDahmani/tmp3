package ma.enset.virementservice.repositories;

import ma.enset.virementservice.entities.Virement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VirementRepository extends JpaRepository<Virement, Long> {
    List<Virement> findByBeneficiaireId(Long beneficiaireId);
}
