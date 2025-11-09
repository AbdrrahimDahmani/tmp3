package ma.enset.virementservice;

import ma.enset.virementservice.entities.TypeVirement;
import ma.enset.virementservice.entities.Virement;
import ma.enset.virementservice.repositories.VirementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableFeignClients
public class VirementServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(VirementServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(VirementRepository virementRepository) {
        return args -> {
            virementRepository.save(new Virement(null, 1L, "RIB111222333", 1000.0, "Paiement loyer", LocalDateTime.now(), TypeVirement.NORMAL));
            virementRepository.save(new Virement(null, 2L, "RIB444555666", 500.0, "Transfert familial", LocalDateTime.now(), TypeVirement.INSTANTANE));
            virementRepository.save(new Virement(null, 1L, "RIB777888999", 2500.0, "Paiement fournisseur", LocalDateTime.now(), TypeVirement.NORMAL));
            
            virementRepository.findAll().forEach(System.out::println);
        };
    }
}
