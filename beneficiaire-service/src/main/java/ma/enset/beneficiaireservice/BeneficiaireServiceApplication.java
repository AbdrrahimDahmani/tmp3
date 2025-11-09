package ma.enset.beneficiaireservice;

import ma.enset.beneficiaireservice.entities.Beneficiaire;
import ma.enset.beneficiaireservice.entities.TypeBeneficiaire;
import ma.enset.beneficiaireservice.repositories.BeneficiaireRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BeneficiaireServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BeneficiaireServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BeneficiaireRepository beneficiaireRepository) {
        return args -> {
            beneficiaireRepository.save(new Beneficiaire(null, "Alami", "Ahmed", "RIB123456789", TypeBeneficiaire.PHYSIQUE));
            beneficiaireRepository.save(new Beneficiaire(null, "Bennani", "Fatima", "RIB987654321", TypeBeneficiaire.PHYSIQUE));
            beneficiaireRepository.save(new Beneficiaire(null, "Entreprise ABC", "Société", "RIB555666777", TypeBeneficiaire.MORALE));
            
            beneficiaireRepository.findAll().forEach(System.out::println);
        };
    }
}
