package ma.enset.beneficiaireservice.repositories;

import ma.enset.beneficiaireservice.entities.Beneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeneficiaireRepository extends JpaRepository<Beneficiaire, Long> {
    List<Beneficiaire> findByNomContaining(String nom);
}
