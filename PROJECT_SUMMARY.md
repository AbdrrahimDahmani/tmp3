# Projet Microservices Bancaires - Documentation Complète

## Vue d'Ensemble

Ce projet implémente une architecture microservices complète pour une application bancaire, incluant les services backend, frontend web, et mobile.

## Architecture Technique

### Backend - Microservices Spring Boot

#### Services Techniques

1. **Discovery Service (Eureka)** - Port 8761
   - Registre de services pour la découverte dynamique
   - Permet aux services de s'enregistrer et de se découvrir
   - Dashboard accessible sur http://localhost:8761

2. **Config Service** - Port 8888
   - Configuration centralisée avec Spring Cloud Config
   - Gestion des configurations par environnement
   - Support de Git comme backend de configuration

3. **Gateway Service** - Port 8080
   - Point d'entrée unique pour toutes les requêtes
   - Routage statique et dynamique configuré
   - Load balancing automatique
   - Permet CORS pour le frontend

#### Services Métier

4. **Beneficiaire Service** - Port 8081
   - Gestion des bénéficiaires (personnes physiques et morales)
   - Base de données H2 en mémoire
   - API REST complète (CRUD)
   - Documentation OpenAPI/Swagger
   - Console H2: http://localhost:8081/h2-console

5. **Virement Service** - Port 8082
   - Gestion des virements bancaires
   - Communication avec Beneficiaire Service via OpenFeign
   - Support des virements normaux et instantanés
   - Base de données H2 en mémoire
   - Documentation OpenAPI/Swagger
   - Console H2: http://localhost:8082/h2-console

### Frontend - Application Angular

**Banking Frontend** - Port 4200
- Application Angular 17 moderne avec Standalone Components
- Interface responsive et intuitive
- Gestion complète des bénéficiaires (CRUD + recherche)
- Gestion complète des virements (CRUD)
- Communication avec le backend via HttpClient
- Proxy configuré pour développement
- Styles CSS personnalisés

### Mobile - Application Flutter

**Banking Mobile Flutter**
- Application cross-platform (iOS & Android)
- Architecture Flutter complète documentée
- Modèles et services préparés
- Configuration API pour différents environnements
- Design Material basé sur le thème de l'application

## Stack Technologique

### Backend
- **Java 17**
- **Spring Boot 3.1.5**
- **Spring Cloud 2022.0.4**
  - Netflix Eureka
  - Spring Cloud Gateway
  - Spring Cloud Config
  - OpenFeign
- **Spring Data JPA**
- **H2 Database**
- **Lombok**
- **OpenAPI 3 / Swagger**

### Frontend Web
- **Angular 17**
- **TypeScript**
- **RxJS**
- **Angular Router**
- **Angular Forms**
- **HttpClient**

### Mobile
- **Flutter 3.x**
- **Dart 3.x**
- **Provider** (State Management)
- **HTTP** package
- **Material Design**

### DevOps
- **Docker** & **Docker Compose**
- **Kubernetes**
- **Jenkins** (Pipeline CI/CD)
- **Maven**

## Fonctionnalités Implémentées

### Questions du Projet

✅ **Question 1**: Création du projet Maven parent avec tous les modules
✅ **Question 2**: Développement et test des services techniques (discovery, config, gateway)
✅ **Question 3**: Développement et test du micro-service Bénéficiaire
✅ **Question 4**: Configuration statique du routage dans le Gateway
✅ **Question 5**: Intégration avec Eureka Discovery Service
✅ **Question 6**: Configuration dynamique des routes du Gateway
✅ **Question 7**: Développement du service Virement avec OpenFeign
✅ **Question 8**: Proposition complète de sécurité pour le système distribué
✅ **Question 9**: Développement d'un client Angular

### Fonctionnalités Additionnelles

✅ DevOps complet (Docker, Docker Compose, Kubernetes, Jenkins)
✅ Documentation OpenAPI/Swagger pour tous les services
✅ Base Flutter mobile avec structure complète
✅ README complets pour chaque composant
✅ Proposition de sécurité détaillée

## Installation et Démarrage

### Prérequis
- JDK 17+
- Maven 3.6+
- Node.js 18+ et npm 9+ (pour Angular)
- Docker & Docker Compose (optionnel)
- Kubernetes/Minikube (optionnel)
- Flutter SDK 3.x+ (optionnel, pour mobile)

### Démarrage Rapide avec Docker Compose

```bash
# Build tous les services
mvn clean package -DskipTests

# Démarrer avec Docker Compose
docker-compose up -d

# Vérifier les services
docker-compose ps

# Voir les logs
docker-compose logs -f
```

### Démarrage Manuel

1. **Démarrer Discovery Service**
   ```bash
   cd discovery-service
   mvn spring-boot:run
   ```
   Dashboard: http://localhost:8761

2. **Démarrer Config Service**
   ```bash
   cd config-service
   mvn spring-boot:run
   ```

3. **Démarrer Gateway Service**
   ```bash
   cd gateway-service
   mvn spring-boot:run
   ```
   Gateway: http://localhost:8080

4. **Démarrer Beneficiaire Service**
   ```bash
   cd beneficiaire-service
   mvn spring-boot:run
   ```
   - API: http://localhost:8081/api/beneficiaires
   - Swagger: http://localhost:8081/swagger-ui.html

5. **Démarrer Virement Service**
   ```bash
   cd virement-service
   mvn spring-boot:run
   ```
   - API: http://localhost:8082/api/virements
   - Swagger: http://localhost:8082/swagger-ui.html

6. **Démarrer Frontend Angular**
   ```bash
   cd banking-frontend
   npm install
   npm start
   ```
   Application: http://localhost:4200

### Démarrage avec Kubernetes

```bash
# Créer le namespace
kubectl apply -f kubernetes/namespace.yaml

# Déployer tous les services
kubectl apply -f kubernetes/

# Vérifier les déploiements
kubectl get all -n banking-microservices

# Accéder au Gateway
kubectl port-forward -n banking-microservices service/gateway-service 8080:8080
```

## Endpoints Principaux

### Via Gateway (http://localhost:8080)

#### Bénéficiaires
- `GET /api/beneficiaires` - Liste tous les bénéficiaires
- `GET /api/beneficiaires/{id}` - Obtenir un bénéficiaire
- `POST /api/beneficiaires` - Créer un bénéficiaire
- `PUT /api/beneficiaires/{id}` - Modifier un bénéficiaire
- `DELETE /api/beneficiaires/{id}` - Supprimer un bénéficiaire
- `GET /api/beneficiaires/search?nom={nom}` - Rechercher par nom

#### Virements
- `GET /api/virements` - Liste tous les virements
- `GET /api/virements/{id}` - Obtenir un virement
- `POST /api/virements` - Créer un virement
- `PUT /api/virements/{id}` - Modifier un virement
- `DELETE /api/virements/{id}` - Supprimer un virement
- `GET /api/virements/beneficiaire/{id}` - Virements d'un bénéficiaire

## Tests

### Backend
```bash
# Tous les tests
mvn test

# Tests d'un service spécifique
cd beneficiaire-service
mvn test
```

### Frontend
```bash
cd banking-frontend
npm test
```

## Sécurité

### Proposition Implémentée (voir security-docs/SECURITY_PROPOSAL.md)

1. **Authentification et Autorisation**
   - OAuth 2.0 + OpenID Connect avec Keycloak
   - JWT pour les tokens
   - Roles: ADMIN, BANKER, USER, SYSTEM

2. **Sécurité Gateway**
   - Validation JWT centralisée
   - Rate limiting
   - CORS configuré

3. **Sécurité Inter-Services**
   - Option A: Service Mesh (Istio) avec mTLS
   - Option B: JWT Propagation via OpenFeign

4. **Protection des Données**
   - Encryption at rest (TDE)
   - Encryption in transit (TLS/mTLS)
   - Masquage des données sensibles

5. **Monitoring et Audit**
   - ELK Stack pour logs
   - Prometheus + Grafana pour métriques
   - Audit trail complet

## CI/CD

### Jenkins Pipeline

Un Jenkinsfile complet est fourni avec:
- Build Maven
- Tests automatiques
- Analyse de code (SonarQube ready)
- Build Docker images
- Push vers registre Docker
- Déploiement automatique

### GitHub Actions (à venir)

Configuration pour automatisation avec GitHub Actions.

## Structure du Projet

```
.
├── discovery-service/          # Eureka Server
├── config-service/             # Spring Cloud Config
├── gateway-service/            # API Gateway
├── beneficiaire-service/       # Service Bénéficiaire
├── virement-service/           # Service Virement
├── banking-frontend/           # Application Angular
├── banking-mobile-flutter/     # Application Flutter
├── kubernetes/                 # Manifests K8s
├── security-docs/             # Documentation sécurité
├── docker-compose.yml         # Orchestration Docker
├── Jenkinsfile               # Pipeline CI/CD
├── build.sh                  # Script de build
├── pom.xml                   # Maven parent POM
└── README.md                 # Documentation principale
```

## Monitoring et Observabilité

### Health Checks
- Spring Boot Actuator activé sur tous les services
- Endpoints: `/actuator/health`, `/actuator/info`

### Logs
- Logs centralisés via Docker Compose
- Format JSON pour parsing facile

### Métriques
- Métriques Prometheus prêtes
- Dashboards Grafana configurables

## Performance

### Optimisations Implémentées
- Connection pooling pour les bases de données
- Cache au niveau du Gateway
- Load balancing automatique via Eureka
- Circuit breakers (Resilience4j ready)

### Capacités
- Services stateless pour scalabilité horizontale
- Base de données H2 (dev) - migratable vers PostgreSQL/MySQL
- Réplication possible via Kubernetes

## Troubleshooting

### Services ne démarrent pas
1. Vérifier que les ports ne sont pas déjà utilisés
2. S'assurer que Java 17 est installé
3. Vérifier les logs: `docker-compose logs [service-name]`

### Frontend ne se connecte pas au backend
1. Vérifier que le Gateway est démarré (port 8080)
2. Vérifier la configuration proxy (proxy.conf.json)
3. Vérifier les CORS dans le Gateway

### Problèmes Docker
1. Rebuild les images: `docker-compose build --no-cache`
2. Nettoyer: `docker-compose down -v`
3. Vérifier les ressources Docker disponibles

## Roadmap

### Court Terme
- [ ] Implémenter la sécurité OAuth2/JWT
- [ ] Ajouter chat-bot service avec RAG
- [ ] Tests d'intégration complets
- [ ] Finaliser l'application Flutter mobile

### Moyen Terme
- [ ] Migration vers PostgreSQL
- [ ] Implémentation Service Mesh (Istio)
- [ ] ELK Stack pour monitoring
- [ ] Cache distribué (Redis)

### Long Terme
- [ ] Multi-tenancy
- [ ] Event-driven architecture (Kafka)
- [ ] GraphQL API
- [ ] Internationalisation complète

## Contribution

### Standards de Code
- Java: Google Java Style Guide
- Angular: Angular Style Guide
- Flutter: Effective Dart

### Git Workflow
- Branches par feature
- Pull requests avec review
- Commits descriptifs en français

## Licence

Projet académique - POC Architecture Microservices

## Auteurs

Développé dans le cadre d'un projet d'architecture microservices

## Support

Pour toute question ou problème:
1. Consulter les README spécifiques à chaque service
2. Vérifier les logs des services
3. Consulter la documentation OpenAPI/Swagger

## Ressources Additionnelles

- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)
- [Angular Documentation](https://angular.io/docs)
- [Flutter Documentation](https://docs.flutter.dev/)
- [Kubernetes Documentation](https://kubernetes.io/docs/)
- [Docker Documentation](https://docs.docker.com/)

---

**Date de dernière mise à jour**: Novembre 2024
**Version**: 1.0.0
