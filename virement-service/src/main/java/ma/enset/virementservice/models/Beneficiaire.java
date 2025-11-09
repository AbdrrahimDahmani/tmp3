package ma.enset.virementservice.models;

import lombok.Data;

@Data
public class Beneficiaire {
    private Long id;
    private String nom;
    private String prenom;
    private String rib;
    private String type;
}
