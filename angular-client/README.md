# Angular Client

Ce dossier est un emplacement réservé pour le client Angular décrit dans l'énoncé. 

## Création rapide

```bash
npm install -g @angular/cli
ng new billing-client --routing --style=scss
```

## Intégration avec les micro-services

1. Configurer un service Angular dédié à chaque API (`/api/customers`, `/api/products`, `/api/bills`).
2. Utiliser `HttpClient` pour interroger la passerelle (`gateway-service`) plutôt que les services internes.
3. Implémenter des composants permettant :
   - l'affichage de la liste des clients,
   - la consultation du catalogue produits,
   - la visualisation détaillée d'une facture.
4. Prévoir un service de configuration (`environment.ts`) pointant sur `http://localhost:8888`.
