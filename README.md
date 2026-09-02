# E-commerce Platform

Plateforme e-commerce backend développée dans le cadre d'un PFE (ESPRIT).

## Stack technique

- **Backend** : Spring Boot 3.3.4, Java 21
- **Base de données** : PostgreSQL
- **Sécurité** : Spring Security, JWT
- **Paiement** : Stripe API
- **Frontend** : Angular 

## Architecture

Monolithe modulaire — un seul projet Spring Boot organisé par module métier :
com.ecommerce/
├── auth/          → utilisateurs, rôles (CLIENT, ADMIN, SUPER_ADMIN), JWT
├── catalog/       → produits, catégories
├── cart/          → panier
├── order/         → commandes
├── payment/       → paiement (Stripe)
├── notification/  → emails
└── config/        → sécurité, JWT, configuration générale

## Prérequis

- Java 21
- Maven
- PostgreSQL

## Installation et lancement

1. Cloner le projet
```bash
git clone https://github.com/TON_USERNAME/ecommerce-platform.git
cd ecommerce-platform
```

2. Créer la base de données PostgreSQL
```sql
CREATE DATABASE ecommerce_db;
```

3. Créer un fichier `src/main/resources/application-local.yml` avec tes valeurs réelles :
```yaml
spring:
  datasource:
    password: TON_MOT_DE_PASSE_POSTGRES
jwt:
  secret: TA_CLE_SECRETE
stripe:
  secret-key: TA_CLE_STRIPE
```

4. Lancer l'application avec le profil `local` :
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

L'application démarre sur `http://localhost:8081`.

## Rôles utilisateurs

| Rôle | Description |
|---|---|
| CLIENT | Inscription publique, gère son panier/commandes |
| ADMIN | Créé uniquement par un SUPER_ADMIN, gère le catalogue et les commandes |
| SUPER_ADMIN | Compte racine créé automatiquement au démarrage, gère les ADMIN |

Un compte SUPER_ADMIN est créé automatiquement au premier démarrage :
- Email : `superadmin@ecommerce.com`
- Mot de passe : `ChangeMe123!` *(à changer en production)*

## Endpoints disponibles

### Auth
| Méthode | Endpoint | Accès |
|---|---|---|
| POST | `/api/auth/register` | Public |
| POST | `/api/auth/login` | Public |
| POST | `/api/auth/create-admin` | SUPER_ADMIN uniquement |

## Statut du projet

🚧 En développement — module `auth` terminé, modules `catalog`, `cart`, `order`, `payment` en cours.