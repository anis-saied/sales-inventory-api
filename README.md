# 📊 Sales-Inventory-API

> API REST robuste pour la gestion commerciale intégrée : facturation, stocks, catalogue produits et paiements.

## 📝 Description

**sales-inventory-api** est le moteur backend d'un système ERP conçu pour automatiser les processus de vente et de logistique. Développée avec **Spring Boot**, cette API assure la fiabilité des données et la fluidité des transactions financières.

### 🔑 Fonctionnalités clés :

- **Gestion des Stocks** : Suivi des entrées/sorties et alertes de seuil critique.
- **Facturation Automatisée** : Génération de factures et gestion des statuts de paiement.
- **Catalogue Produits** : Organisation des références et suivi des prix.
- **Architecture Sécurisée** : Gestion des accès et intégrité des données via JPA/Hibernate.

## 🛠️ Stack Technique

- **Framework** : Spring Boot 3+
- **Langage** : Java
- **Base de données** : MySQL
- **ORM** : Hibernate / Spring Data JPA

---

## 🏗️ Installation (Local)

Vous aurez besoin de **Java JDK 17+** et de **Maven** installés sur votre machine.

1. **Cloner le projet**

   ```bash
   git clone [https://github.com/anis-saied/sales-inventory-api.git](https://github.com/anis-saied/sales-inventory-api.git)
   cd sales-inventory-api
   ```

2. Configuration de la base de données

- Créez une base de données MySQL nommée erp_db.
- Modifiez le fichier src/main/resources/application.properties avec vos identifiants MySQL :

```Properties
spring.datasource.url=jdbc:mysql://localhost:3306/erp_db
spring.datasource.username=VOTRE_USER
spring.datasource.password=VOTRE_PASSWORD
```

- Lancer l'application

```bash
mvn spring-boot:run
```

## 💻 Utilisation

**Endpoints principaux**

Une fois lancée, l'API est accessible sur `http://localhost:8080/api/v1`.

- `GET /products` : Liste des produits en stock.
- `POST /invoices` : Création d'une nouvelle facture.
- `GET /payments` : Suivi des règlements clients.

## 📁 Structure du projet

- `src/main/java/.../controller/` : Points d'accès de l'API (Endpoints).
- `src/main/java/.../service/` : Logique métier (calculs de factures, gestion de stock).
- `src/main/java/.../model/` : Entités de la base de données.
- `src/main/java/.../repository/` : Interfaces pour les requêtes SQL.

## 🔒 Confidentialité

Ce projet est une solution propriétaire. Le code source peut être soumis à des restrictions d'accès pour les déploiements en production.

## 🧑‍💻 Auteur

Développé avec rigueur par [Anis Saied](https://anis-saied.com).
