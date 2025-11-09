class Beneficiaire {
  final int? id;
  final String nom;
  final String prenom;
  final String rib;
  final String type; // 'PHYSIQUE' ou 'MORALE'

  Beneficiaire({
    this.id,
    required this.nom,
    required this.prenom,
    required this.rib,
    required this.type,
  });

  factory Beneficiaire.fromJson(Map<String, dynamic> json) {
    return Beneficiaire(
      id: json['id'],
      nom: json['nom'] ?? '',
      prenom: json['prenom'] ?? '',
      rib: json['rib'] ?? '',
      type: json['type'] ?? 'PHYSIQUE',
    );
  }

  Map<String, dynamic> toJson() {
    return {
      if (id != null) 'id': id,
      'nom': nom,
      'prenom': prenom,
      'rib': rib,
      'type': type,
    };
  }

  String get fullName => '$nom $prenom';
}
