# Banking Frontend - Angular Application

Application Angular pour la gestion des bénéficiaires et virements bancaires.

## Description

Interface web moderne développée avec Angular 17 pour interagir avec les microservices bancaires.

## Fonctionnalités

- **Gestion des Bénéficiaires**
  - Liste des bénéficiaires
  - Création de nouveaux bénéficiaires
  - Modification des bénéficiaires existants
  - Suppression de bénéficiaires
  - Recherche par nom

- **Gestion des Virements**
  - Liste des virements avec informations du bénéficiaire
  - Création de nouveaux virements
  - Modification des virements
  - Suppression de virements
  - Support des types Normal et Instantané

## Prérequis

- Node.js 18+
- npm 9+
- Les microservices backend doivent être démarrés

## Installation

```bash
npm install
```

## Démarrage

```bash
npm start
```

L'application sera accessible sur http://localhost:4200

Le proxy est configuré pour rediriger les appels API vers http://localhost:8080 (Gateway)

## Build pour Production

```bash
npm run build
```

Les fichiers de production seront générés dans le dossier `dist/`

## Architecture

```
src/
├── app/
│   ├── components/
│   │   ├── beneficiaire-list/    # Composant liste bénéficiaires
│   │   └── virement-list/        # Composant liste virements
│   ├── models/
│   │   ├── beneficiaire.model.ts # Modèle Beneficiaire
│   │   └── virement.model.ts     # Modèle Virement
│   ├── services/
│   │   ├── beneficiaire.service.ts # Service HTTP Beneficiaire
│   │   └── virement.service.ts     # Service HTTP Virement
│   ├── app.component.*           # Composant principal avec navigation
│   ├── app.config.ts             # Configuration (HttpClient, Router)
│   └── app.routes.ts             # Définition des routes
├── styles.css                     # Styles globaux
└── proxy.conf.json               # Configuration proxy pour dev
```

## Routes

- `/` - Redirige vers /beneficiaires
- `/beneficiaires` - Gestion des bénéficiaires
- `/virements` - Gestion des virements

## Configuration API

Les appels API sont configurés pour passer par le proxy Angular qui redirige vers le Gateway (port 8080):
- `/api/beneficiaires` → `http://localhost:8080/api/beneficiaires`
- `/api/virements` → `http://localhost:8080/api/virements`

## Stack Technique

- Angular 17 (Standalone Components)
- TypeScript
- RxJS
- Angular Router
- Angular Forms
- Angular HttpClient

## Développement

### Génération de composants
```bash
npx ng generate component components/nom-composant
```

### Génération de services
```bash
npx ng generate service services/nom-service
```

## Tests

```bash
npm test
```

## Déploiement avec Docker

Un Dockerfile sera ajouté pour containeriser l'application frontend.

## Auteur

Projet POC - Architecture Microservices
