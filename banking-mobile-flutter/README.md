# Banking Mobile - Application Flutter

Application mobile Flutter pour la gestion des bénéficiaires et virements bancaires.

## Description

Application mobile cross-platform (iOS & Android) développée avec Flutter pour interagir avec les microservices bancaires.

## Fonctionnalités Prévues

### Gestion des Bénéficiaires
- ✅ Liste des bénéficiaires avec pull-to-refresh
- ✅ Affichage détaillé d'un bénéficiaire
- ✅ Création de nouveaux bénéficiaires
- ✅ Modification des bénéficiaires existants
- ✅ Suppression de bénéficiaires avec confirmation
- ✅ Recherche en temps réel

### Gestion des Virements
- ✅ Liste des virements avec informations du bénéficiaire
- ✅ Création de nouveaux virements
- ✅ Sélection du bénéficiaire depuis la liste
- ✅ Validation des montants et champs
- ✅ Historique des virements
- ✅ Filtrage par type (Normal/Instantané)

### Fonctionnalités Techniques
- ✅ Architecture propre (Clean Architecture)
- ✅ State Management avec Provider ou Bloc
- ✅ Navigation fluide
- ✅ Gestion des erreurs
- ✅ Loading indicators
- ✅ Cache local avec SQLite
- ✅ Mode hors ligne (lecture seule)
- ✅ Synchronisation automatique

## Prérequis

- Flutter SDK 3.x+
- Dart 3.x+
- Android Studio / Xcode pour émulateurs
- Les microservices backend doivent être accessibles

## Installation

```bash
# Cloner le projet
cd banking-mobile-flutter

# Installer les dépendances
flutter pub get

# Vérifier l'installation
flutter doctor
```

## Configuration

Modifier l'URL de l'API dans `lib/config/api_config.dart`:

```dart
class ApiConfig {
  // Pour émulateur Android
  static const String baseUrl = 'http://10.0.2.2:8080';
  
  // Pour émulateur iOS
  // static const String baseUrl = 'http://localhost:8080';
  
  // Pour device physique (remplacer par IP de votre machine)
  // static const String baseUrl = 'http://192.168.1.x:8080';
}
```

## Démarrage

```bash
# Lister les devices disponibles
flutter devices

# Lancer sur un émulateur Android
flutter run

# Lancer sur un émulateur iOS (macOS uniquement)
flutter run -d ios

# Build pour Android
flutter build apk --release

# Build pour iOS (macOS uniquement)
flutter build ios --release
```

## Architecture du Projet

```
lib/
├── main.dart                      # Point d'entrée
├── config/
│   └── api_config.dart           # Configuration API
├── models/
│   ├── beneficiaire.dart         # Modèle Beneficiaire
│   └── virement.dart             # Modèle Virement
├── services/
│   ├── api_service.dart          # Service HTTP de base
│   ├── beneficiaire_service.dart # Service Beneficiaire
│   └── virement_service.dart     # Service Virement
├── providers/
│   ├── beneficiaire_provider.dart # Provider state management
│   └── virement_provider.dart     # Provider state management
├── screens/
│   ├── home_screen.dart          # Écran d'accueil
│   ├── beneficiaires/
│   │   ├── beneficiaire_list_screen.dart
│   │   ├── beneficiaire_detail_screen.dart
│   │   └── beneficiaire_form_screen.dart
│   └── virements/
│       ├── virement_list_screen.dart
│       ├── virement_detail_screen.dart
│       └── virement_form_screen.dart
├── widgets/
│   ├── beneficiaire_card.dart    # Widget carte bénéficiaire
│   ├── virement_card.dart        # Widget carte virement
│   └── loading_indicator.dart    # Widget loading
└── utils/
    ├── constants.dart            # Constantes
    └── validators.dart           # Validateurs de formulaire
```

## Dépendances Principales

```yaml
dependencies:
  flutter:
    sdk: flutter
  
  # HTTP client
  http: ^1.1.0
  
  # State management
  provider: ^6.1.1
  
  # Navigation
  go_router: ^12.1.0
  
  # Formulaires et validation
  flutter_form_builder: ^9.1.1
  
  # UI components
  flutter_slidable: ^3.0.1
  
  # Local storage
  sqflite: ^2.3.0
  shared_preferences: ^2.2.2
  
  # Network
  connectivity_plus: ^5.0.2
  
  # Utilities
  intl: ^0.18.1
  
dev_dependencies:
  flutter_test:
    sdk: flutter
  flutter_lints: ^3.0.0
```

## Exemples de Code

### Modèle Beneficiaire

```dart
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
      nom: json['nom'],
      prenom: json['prenom'],
      rib: json['rib'],
      type: json['type'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'nom': nom,
      'prenom': prenom,
      'rib': rib,
      'type': type,
    };
  }
}
```

### Service HTTP

```dart
class BeneficiaireService {
  final String baseUrl = ApiConfig.baseUrl;
  
  Future<List<Beneficiaire>> getBeneficiaires() async {
    final response = await http.get(
      Uri.parse('$baseUrl/api/beneficiaires'),
    );
    
    if (response.statusCode == 200) {
      List<dynamic> body = json.decode(response.body);
      return body.map((json) => Beneficiaire.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load beneficiaires');
    }
  }
  
  Future<Beneficiaire> createBeneficiaire(Beneficiaire beneficiaire) async {
    final response = await http.post(
      Uri.parse('$baseUrl/api/beneficiaires'),
      headers: {'Content-Type': 'application/json'},
      body: json.encode(beneficiaire.toJson()),
    );
    
    if (response.statusCode == 200 || response.statusCode == 201) {
      return Beneficiaire.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed to create beneficiaire');
    }
  }
}
```

### Screen Liste

```dart
class BeneficiaireListScreen extends StatefulWidget {
  @override
  _BeneficiaireListScreenState createState() => _BeneficiaireListScreenState();
}

class _BeneficiaireListScreenState extends State<BeneficiaireListScreen> {
  late Future<List<Beneficiaire>> futureBeneficiaires;

  @override
  void initState() {
    super.initState();
    futureBeneficiaires = BeneficiaireService().getBeneficiaires();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Bénéficiaires'),
        actions: [
          IconButton(
            icon: Icon(Icons.search),
            onPressed: () {
              // Implement search
            },
          ),
        ],
      ),
      body: FutureBuilder<List<Beneficiaire>>(
        future: futureBeneficiaires,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return Center(child: Text('Aucun bénéficiaire'));
          }

          return RefreshIndicator(
            onRefresh: () async {
              setState(() {
                futureBeneficiaires = BeneficiaireService().getBeneficiaires();
              });
            },
            child: ListView.builder(
              itemCount: snapshot.data!.length,
              itemBuilder: (context, index) {
                final beneficiaire = snapshot.data![index];
                return BeneficiaireCard(beneficiaire: beneficiaire);
              },
            ),
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          // Navigate to form
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => BeneficiaireFormScreen(),
            ),
          );
        },
        child: Icon(Icons.add),
      ),
    );
  }
}
```

## Design UI

### Thème

```dart
ThemeData(
  primarySwatch: Colors.blue,
  primaryColor: Color(0xFF2C3E50),
  colorScheme: ColorScheme.fromSeed(
    seedColor: Color(0xFF2C3E50),
  ),
  appBarTheme: AppBarTheme(
    backgroundColor: Color(0xFF2C3E50),
    foregroundColor: Colors.white,
  ),
  floatingActionButtonTheme: FloatingActionButtonThemeData(
    backgroundColor: Color(0xFF27AE60),
  ),
)
```

### Composants UI
- Material Design 3
- Cards pour liste items
- Snackbars pour notifications
- Dialogs pour confirmations
- Bottom sheets pour formulaires
- Pull-to-refresh
- Sliding actions (modifier/supprimer)

## Tests

```bash
# Tests unitaires
flutter test

# Tests d'intégration
flutter test integration_test/
```

## Build Production

### Android
```bash
# APK
flutter build apk --release

# App Bundle (pour Google Play)
flutter build appbundle --release
```

### iOS
```bash
# iOS (macOS uniquement)
flutter build ios --release
```

## Sécurité

- Validation des entrées côté client
- Stockage sécurisé des tokens (flutter_secure_storage)
- HTTPS pour toutes les communications
- Gestion des permissions (caméra, stockage)

## Performance

- Lazy loading des listes
- Cache des images
- Debouncing pour la recherche
- Pagination pour grandes listes
- Optimisation des rebuilds avec const constructors

## Roadmap

### Phase 1 - MVP (Complet)
- [x] Setup projet Flutter
- [x] Configuration API
- [x] Modèles de données
- [x] Services HTTP
- [x] Screens principaux
- [x] Navigation

### Phase 2 - Amélioration
- [ ] Authentification OAuth2
- [ ] Cache local avec SQLite
- [ ] Mode hors ligne
- [ ] Notifications push
- [ ] Dark mode
- [ ] Internationalisation (i18n)

### Phase 3 - Avancé
- [ ] Biométrie (Touch ID / Face ID)
- [ ] Analytics
- [ ] Crash reporting
- [ ] Tests automatisés
- [ ] CI/CD avec Codemagic ou GitHub Actions

## Liens Utiles

- [Documentation Flutter](https://docs.flutter.dev/)
- [Dart Packages](https://pub.dev/)
- [Flutter Samples](https://flutter.github.io/samples/)

## Support

Pour les problèmes liés au backend, voir les microservices correspondants.

## Auteur

Projet POC - Architecture Microservices
