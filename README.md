# ✈️ Flighty – Application de Réservation de Vols

Application complète (backend Spring Boot + frontend Angular avec Nx) pour gérer la réservation de vols avec gestion de la concurrence, cache et tests automatisés.

---

## 🚀 Prérequis

Avant de commencer, assurez-vous d’avoir installé :

- **Java 17+**
- **Maven 3.9+**
- **Node.js 18+** et **npm**
- **Angular CLI** et **Nx CLI**

  ```bash
  npm install -g @angular/cli nx

## ⚙️ Installation
### 1. Cloner le projet
```bash
git clone https://github.com/takwaghrissi20/flight-app.git
cd flight-app 

```
### 2. Backend (Spring Boot) (utiliser STS)
```bash
cd springbootBackend1

```

### 3. Frontend (Angular + Nx)
```bash
cd frontend
npm install

```

## ▶️ Exécution
### 1. Lancer le backend
ouvrir le dossier depuis sts 
Le backend démarre sur :
👉 http://localhost:9090
ou bien :
```bash
mvn spring-boot:run
 
 ```

### 2. Lancer le frontend
Depuis le dossier frontend :

```bash
npx nx serve flight-app

```

Le frontend démarre sur :
👉 http://localhost:4200


## 📌 Fonctionnalités
###  Backend (Spring Boot)
* API Vols

GET /api/vols → récupérer la liste des vols

POST /api/vols → créer un vol

* API Réservations

POST /api/reservations → réserver des places

Vérifie la disponibilité des places

Retourne une erreur 400 BAD REQUEST si pas assez de places

* Gestion de concurrence

Protection contre les sur-réservations (synchro et transactions)

* Événements

ReservationEvent publié à chaque réservation

* Cache

Mise en cache des vols avec Caffeine

* Tests automatisés

Unitaires avec Mockito

Intégration avec @SpringBootTest

Concurrence simulée avec CompletableFuture et @RepeatedTest

### Frontend (Angular + Nx)

* Authentification basique

Login (nom + email)

Affichage du composant dans le header : "👋 Bienvenue, {nom}"

Déconnexion

* Recherche de vols

Sélection ville départ, ville arrivée et date

* Réservation

Injection automatique de nom et email dans le modal de réservation

Sélection du nombre de places

Gestion d’erreurs : toast si pas assez de places
Gestion d’erreurs : toast si meme user réserve le meme vol 

toute tentative réusite ou échouée de réservation est journalisé dans la table audit_reservation

* UI

Header avec logo, slogan, login/user info

Formulaires réactifs

Toasts d’erreurs (ex: "Pas assez de places disponibles")

## 🧪 Lancer les tests
* Backend

Exécuter tous les tests (unitaires + intégration) directement sur STS:
Tests inclus :

✅ Unit test : service de réservation (mock repository)

✅ Integration test : cycle complet réservation + sur-réservation

✅  test de concurrence (10 appels simultanés)

ou bien 

```bash
mvn test
```



## 📂 Architecture du projet
### 1. Frontend (flight-app)
```text
flight-app/
├── src/
│   └── app/
│       ├── components/
│       │   ├── flight-header/
│       │   ├── flight-search/
│       │   ├── loading/
│       │   ├── not-found/
│       │   ├── reservation-button/
│       │   └── reservation-modal/
│       ├── models/
│       │   └── vol.model.ts
│           constants/
│       │      └── urls.ts
│       └── services/
│           ├── flight.service.ts
│           ├── user.service.ts
│           └── reservation.service.ts
├── assets/
├── environments/
├── index.html
├── main.ts
└── angular.json

```

components/ : tous les composants UI réutilisables.

models/ : structure des données (ex: Vol).

constants/ : valeurs constantes, URLs API.

services/ : services pour la communication avec le backend (HTTP) (utilisation de observable et subscribe pattern de RxJS). 

### 2. Backend (Spring Boot - springbootBackend1)
```text
springbootBackend1/
├── src/
│   ├── main/
│   │   └── java/com/example/springboot_backend/
│   │       ├── controllers/
│   │       │   └── ReservationController.java
│   │       ├── services/
│   │       │   └── ReservationService.java
│   │       ├── servicesimpl/
│   │       │   └── ReservationServiceImpl.java
│   │       ├── models/
│   │       │   └── Reservation.java
│   │       ├── repositories/
│   │       │   └── ReservationRepository.java
│   │       ├── dto/
│   │       │   └── ReservationDTO.java
│   │       ├── exception/
│   │       │   └── ResourceNotFoundException.java
│   │       └── listeners/
│   │           └── ReservationListener.java
│   └── test/
│       └── java/com/example/springboot_backend/
│           ├── ReservationControllerTest.java
│           └── ReservationServiceImplTest.java
├── pom.xml
└── application.properties

```

controllers/ : endpoints REST.

services/ & servicesimpl/ : logique métier (interface + impl).

models/ : entités JPA.

repositories/ : accès aux données (Spring Data JPA).

dto/ : objets de transfert (DTO) pour l'exemple de json pour reserver un vol.

exception/ : gestion des exceptions personnalisées.

listeners/ : événements ou hooks pour le  logique de l'audit.

test/ : tests unitaires et d’intégration.

