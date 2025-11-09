import 'beneficiaire.dart';

class Virement {
  final int? id;
  final int beneficiaireId;
  final String ribSource;
  final double montant;
  final String description;
  final DateTime dateVirement;
  final String type; // 'NORMAL' ou 'INSTANTANE'
  final Beneficiaire? beneficiaire;

  Virement({
    this.id,
    required this.beneficiaireId,
    required this.ribSource,
    required this.montant,
    required this.description,
    required this.dateVirement,
    required this.type,
    this.beneficiaire,
  });

  factory Virement.fromJson(Map<String, dynamic> json) {
    return Virement(
      id: json['id'],
      beneficiaireId: json['beneficiaireId'] ?? 0,
      ribSource: json['ribSource'] ?? '',
      montant: (json['montant'] ?? 0).toDouble(),
      description: json['description'] ?? '',
      dateVirement: json['dateVirement'] != null
          ? DateTime.parse(json['dateVirement'])
          : DateTime.now(),
      type: json['type'] ?? 'NORMAL',
      beneficiaire: json['beneficiaire'] != null
          ? Beneficiaire.fromJson(json['beneficiaire'])
          : null,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      if (id != null) 'id': id,
      'beneficiaireId': beneficiaireId,
      'ribSource': ribSource,
      'montant': montant,
      'description': description,
      'dateVirement': dateVirement.toIso8601String(),
      'type': type,
    };
  }
}
