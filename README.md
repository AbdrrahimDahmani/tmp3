# Micro-services Billing Platform

Ce dépôt propose un squelette complet pour une plateforme de gestion de factures 
reposant sur une architecture micro-services Spring Boot inspirée des ressources 
de Mohamed Youssfi. L'objectif est de disposer d'une base de travail prête à être
étoffée pour l'évaluation d'architecture micro-services.

## Panorama des modules

| Service              | Port par défaut | Rôle principal |
|----------------------|-----------------|----------------|
| `discovery-service`  | `8761`          | Annuaire Eureka pour l'enregistrement et la découverte des services. |
| `config-service`     | `8889`          | Fournit la configuration centralisée via Spring Cloud Config (profil `native`). |
| `gateway-service`    | `8888`          | Passerelle Spring Cloud Gateway avec routage statique et découverte dynamique. |
| `customer-service`   | `8081`          | Gestion CRUD des clients avec base mémoire H2. |
| `inventory-service`  | `8082`          | Gestion CRUD des produits avec base mémoire H2. |
| `billing-service`    | `8083`          | Génération et agrégation des factures via OpenFeign. |
| `angular-client`     | -               | Espace réservé pour le front-end Angular consommant la passerelle. |

## Prérequis

- Java 17+
- Maven 3.9+
- Node.js 18+ (pour le client Angular)

## Démarrage rapide

### 1. Lancer l'annuaire et le service de configuration

```bash
mvn -pl discovery-service spring-boot:run
mvn -pl config-service spring-boot:run
```

### 2. Démarrer les micro-services métiers

Dans des terminaux séparés :

```bash
mvn -pl customer-service spring-boot:run
mvn -pl inventory-service spring-boot:run
mvn -pl billing-service spring-boot:run
```

Le service de facturation contacte `customer-service` et `inventory-service` via OpenFeign 
pour enrichir les données de factures.

### 3. Lancer la passerelle

```bash
mvn -pl gateway-service spring-boot:run
```

Les routes statiques `/api/customers/**`, `/api/products/**` et `/api/bills/**` sont
déclarées pour le développement local, tandis que l'option `discovery.locator` permet
l'enregistrement dynamique auprès d'Eureka.

### 4. Client Angular (optionnel)

Le dossier `angular-client` contient un guide de génération rapide. Une fois le projet
créé, configurez les appels HTTP sur `http://localhost:8888`.

## Données d'exemple

Chaque service persiste un jeu de données en mémoire via H2 :

- `customer-service` crée trois clients fictifs.
- `inventory-service` expose trois produits.
- `billing-service` génère une facture de démonstration liée aux identifiants
  ci-dessus (1 pour le client, 1 et 2 pour les produits).

## Pistes d'évolution

- Externaliser la configuration vers un dépôt Git pour le config-server.
- Mettre en place Keycloak ou Spring Authorization Server pour sécuriser la passerelle.
- Ajouter des tests d'intégration (Testcontainers) et un pipeline CI/CD.
- Finaliser le client Angular avec une UI Material Design.

## Ressources complémentaires

- [Playlist YouTube de Mohamed Youssfi](https://www.youtube.com/watch?v=fvEg8bOhpo8&list=PLK8cqdr55Tsv-D2HMdrnD32oOVBNvmxjr)
- [Repo de référence `micro-services-app`](https://github.com/mohamedYoussfi/micro-services-app)
