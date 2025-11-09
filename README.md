# Microservices Architecture - Banking Application

## Description
Application bancaire basée sur une architecture microservices pour la gestion des bénéficiaires et des virements bancaires.

## Architecture

### Microservices Techniques
- **discovery-service** (Port 8761): Eureka Discovery Service - Registre des services
- **config-service** (Port 8888): Spring Cloud Config Server - Service de configuration centralisée
- **gateway-service** (Port 8080): Spring Cloud Gateway - Point d'entrée unique pour l'API

### Microservices Métier
- **beneficiaire-service** (Port 8081): Gestion des bénéficiaires (Physiques et Morales)
- **virement-service** (Port 8082): Gestion des virements bancaires (Normaux et Instantanés)

## Technologies Utilisées
- Java 17
- Spring Boot 3.1.5
- Spring Cloud 2022.0.4
- Spring Cloud Netflix Eureka
- Spring Cloud Gateway
- Spring Cloud Config
- Spring Cloud OpenFeign
- Spring Data JPA
- H2 Database
- Lombok
- OpenAPI 3 / Swagger

## Prérequis
- JDK 17 ou supérieur
- Maven 3.6+
- Docker et Docker Compose (pour le déploiement conteneurisé)
- Kubernetes/Minikube (optionnel, pour le déploiement K8s)

## Compilation

```bash
mvn clean install
```

## Démarrage des Services

### Ordre de démarrage recommandé:

1. **Discovery Service** (Eureka Server)
```bash
cd discovery-service
mvn spring-boot:run
```
Accès: http://localhost:8761

2. **Config Service**
```bash
cd config-service
mvn spring-boot:run
```
Accès: http://localhost:8888

3. **Gateway Service**
```bash
cd gateway-service
mvn spring-boot:run
```
Accès: http://localhost:8080

4. **Beneficiaire Service**
```bash
cd beneficiaire-service
mvn spring-boot:run
```
- API: http://localhost:8081/api/beneficiaires
- Swagger UI: http://localhost:8081/swagger-ui.html
- H2 Console: http://localhost:8081/h2-console

5. **Virement Service**
```bash
cd virement-service
mvn spring-boot:run
```
- API: http://localhost:8082/api/virements
- Swagger UI: http://localhost:8082/swagger-ui.html
- H2 Console: http://localhost:8082/h2-console

## Accès aux API via Gateway

Une fois tous les services démarrés, vous pouvez accéder aux APIs via le Gateway:

### Configuration Statique (Question 4)
- Bénéficiaires: http://localhost:8080/api/beneficiaires
- Virements: http://localhost:8080/api/virements

### Configuration Dynamique (Question 6)
- Bénéficiaires: http://localhost:8080/beneficiaire-service/api/beneficiaires
- Virements: http://localhost:8080/virement-service/api/virements

## API REST

### Beneficiaire Service

#### GET /api/beneficiaires
Récupérer tous les bénéficiaires

#### GET /api/beneficiaires/{id}
Récupérer un bénéficiaire par ID

#### POST /api/beneficiaires
Créer un nouveau bénéficiaire
```json
{
  "nom": "Alami",
  "prenom": "Ahmed",
  "rib": "RIB123456789",
  "type": "PHYSIQUE"
}
```

#### PUT /api/beneficiaires/{id}
Mettre à jour un bénéficiaire

#### DELETE /api/beneficiaires/{id}
Supprimer un bénéficiaire

#### GET /api/beneficiaires/search?nom=xxx
Rechercher des bénéficiaires par nom

### Virement Service

#### GET /api/virements
Récupérer tous les virements (avec informations du bénéficiaire via OpenFeign)

#### GET /api/virements/{id}
Récupérer un virement par ID

#### POST /api/virements
Créer un nouveau virement
```json
{
  "beneficiaireId": 1,
  "ribSource": "RIB111222333",
  "montant": 1000.0,
  "description": "Paiement loyer",
  "dateVirement": "2024-11-09T22:00:00",
  "type": "NORMAL"
}
```

#### PUT /api/virements/{id}
Mettre à jour un virement

#### DELETE /api/virements/{id}
Supprimer un virement

#### GET /api/virements/beneficiaire/{beneficiaireId}
Récupérer les virements par bénéficiaire

## Documentation OpenAPI

Chaque microservice expose sa documentation OpenAPI (Swagger):
- Beneficiaire Service: http://localhost:8081/swagger-ui.html
- Virement Service: http://localhost:8082/swagger-ui.html

## Base de Données H2

Chaque service utilise une base de données H2 en mémoire:
- Beneficiaire DB: jdbc:h2:mem:beneficiaire-db
- Virement DB: jdbc:h2:mem:virement-db

Accès H2 Console:
- URL: jdbc:h2:mem:beneficiaire-db (ou virement-db)
- Username: sa
- Password: (vide)

## Communication Inter-Services

Le virement-service communique avec le beneficiaire-service via **OpenFeign** pour récupérer les informations des bénéficiaires.

## Déploiement avec Docker

### Option 1: Déploiement avec Docker Compose

1. **Build et déploiement automatique:**
```bash
./build.sh
```

2. **Ou manuellement:**
```bash
# Build Maven
mvn clean package -DskipTests

# Build Docker images et démarrer les services
docker-compose up -d
```

3. **Vérifier les services:**
```bash
docker-compose ps
```

4. **Voir les logs:**
```bash
docker-compose logs -f [service-name]
```

5. **Arrêter les services:**
```bash
docker-compose down
```

### Option 2: Déploiement avec Kubernetes

1. **Créer le namespace:**
```bash
kubectl apply -f kubernetes/namespace.yaml
```

2. **Déployer tous les services:**
```bash
kubectl apply -f kubernetes/
```

3. **Vérifier les déploiements:**
```bash
kubectl get all -n banking-microservices
```

4. **Accéder aux services:**
```bash
# Forward gateway port
kubectl port-forward -n banking-microservices service/gateway-service 8080:8080

# Forward discovery port
kubectl port-forward -n banking-microservices service/discovery-service 8761:8761
```

5. **Supprimer les déploiements:**
```bash
kubectl delete namespace banking-microservices
```

## Pipeline CI/CD avec Jenkins

Un fichier `Jenkinsfile` est fourni pour automatiser:
- Le build Maven
- Les tests
- L'analyse de code (SonarQube)
- La création d'images Docker
- Le déploiement

Pour utiliser le pipeline:
1. Créer un nouveau pipeline dans Jenkins
2. Pointer vers le Jenkinsfile dans le repository
3. Configurer les credentials Docker si nécessaire
4. Lancer le build

## Fonctionnalités Implémentées

✅ Question 1: Création du projet Maven parent avec modules
✅ Question 2: Développement de discovery-service (Eureka)
✅ Question 2: Développement de config-service
✅ Question 2: Développement de gateway-service
✅ Question 3: Développement de beneficiaire-service
✅ Question 4: Configuration statique du routage dans le Gateway
✅ Question 5: Intégration avec Eureka Discovery
✅ Question 6: Configuration dynamique des routes du Gateway
✅ Question 7: Développement de virement-service avec OpenFeign
✅ Documentation OpenAPI/Swagger pour les APIs REST
✅ Question 8 (DevOps): Docker, Docker Compose, Kubernetes, Jenkins Pipeline

## Structure du Projet

```
banking-microservices/
├── pom.xml (parent)
├── discovery-service/
│   ├── pom.xml
│   └── src/main/java/ma/enset/discoveryservice/
├── config-service/
│   ├── pom.xml
│   └── src/main/java/ma/enset/configservice/
├── gateway-service/
│   ├── pom.xml
│   └── src/main/java/ma/enset/gatewayservice/
├── beneficiaire-service/
│   ├── pom.xml
│   └── src/main/java/ma/enset/beneficiaireservice/
│       ├── entities/
│       ├── repositories/
│       └── controllers/
└── virement-service/
    ├── pom.xml
    └── src/main/java/ma/enset/virementservice/
        ├── entities/
        ├── repositories/
        ├── controllers/
        ├── clients/ (OpenFeign)
        └── models/
```

## À Venir

- Chat-bot Service avec IA générative (RAG) basé sur Spring AI ou Python/Langchain
- Frontend Angular pour l'interface web
- Client Mobile Flutter
- Sécurité avec Spring Security (OAuth2, JWT, Keycloak)

## Architecture DevOps Implémentée

### Docker
- Dockerfiles pour chaque microservice
- Docker Compose pour orchestration locale
- Images basées sur OpenJDK 17 slim

### Kubernetes
- Déploiements et Services K8s pour chaque microservice
- Namespace dédié
- Configuration de load balancing pour le Gateway
- Réplication des services métier (2 replicas)

### CI/CD
- Pipeline Jenkins automatisé
- Build Maven
- Tests automatiques
- Build et push d'images Docker
- Déploiement automatique

## Auteur
Projet de POC - Architecture Microservices
