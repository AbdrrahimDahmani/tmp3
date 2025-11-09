package ma.enset.virementservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Virement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long beneficiaireId;
    private String ribSource;
    private Double montant;
    private String description;
    private LocalDateTime dateVirement;
    
    @Enumerated(EnumType.STRING)
    private TypeVirement type;
    
    @Transient
    private Object beneficiaire;
    
    public Virement(Long id, Long beneficiaireId, String ribSource, Double montant, String description, LocalDateTime dateVirement, TypeVirement type) {
        this.id = id;
        this.beneficiaireId = beneficiaireId;
        this.ribSource = ribSource;
        this.montant = montant;
        this.description = description;
        this.dateVirement = dateVirement;
        this.type = type;
    }
}
