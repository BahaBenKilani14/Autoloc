# Atelier 3 : Spring Data JPA, interfaces repository et opérations CRUD

Projet : AutoLoc (`autoloc_api`), package racine `tn.esprit.autoloc.autoloc_api`.

Au démarrage, les logs indiquent `Found 9 JPA repository interfaces` : Spring Data a détecté les neuf interfaces et génère leur implémentation (proxy) à l'exécution. Aucune classe d'implémentation n'a été écrite.

---

## A. Choix de l'interface

Les neuf repositories étendent `JpaRepository<Entité, Long>`. C'est l'interface retenue pour AutoLoc car elle réunit :

- le CRUD complet (`save`, `findById`, `existsById`, `deleteById`...),
- des collections renvoyées en `List` (au lieu d'`Iterable`),
- le tri et la pagination,
- les méthodes propres à JPA (`flush`, `saveAndFlush`, `getReferenceById`...).

| Interface | Étend | Justification |
| :--- | :--- | :--- |
| `IAgenceRepository` | `JpaRepository<Agence, Long>` | CRUD complet, `findAll` renvoie une `List`, tri et pagination disponibles. |
| `IClientRepository` | `JpaRepository<Client, Long>` | CRUD complet, `findAll` renvoie une `List`, tri et pagination disponibles. |
| `IContratRepository` | `JpaRepository<Contrat, Long>` | CRUD complet, `findAll` renvoie une `List`, `saveAndFlush` disponible. |
| `IEmployeRepository` | `JpaRepository<Employe, Long>` | CRUD complet, `findAll` renvoie une `List`, tri et pagination disponibles. |
| `IEquipementRepository` | `JpaRepository<Equipement, Long>` | CRUD complet, `findAll` renvoie une `List`, tri et pagination disponibles. |
| `IMaintenanceRepository` | `JpaRepository<Maintenance, Long>` | CRUD complet, `findAll` renvoie une `List`, tri et pagination disponibles. |
| `IPaiementRepository` | `JpaRepository<Paiement, Long>` | Créé pour pouvoir lire les paiements ; la création et le retrait passent par le Contrat (composition). |
| `IReservationRepository` | `JpaRepository<Reservation, Long>` | CRUD complet, `findAll` renvoie une `List`, tri et pagination disponibles. |
| `IVehiculeRepository` | `JpaRepository<Vehicule, Long>` | CRUD complet, `findAll` renvoie une `List`, tri et pagination disponibles. |

### Points à retenir

- `save` avec un identifiant `null` déclenche un `persist` (INSERT) ; avec un identifiant renseigné, un `merge` (SELECT puis UPDATE si besoin). Il faut utiliser l'objet retourné.
- `deleteById` ne lève aucune exception si l'identifiant n'existe pas.
- Les suppressions de `CrudRepository` passent par le contexte de persistance : la cascade et l'`orphanRemoval` s'appliquent.
- Les suppressions en lot (`deleteAllInBatch`, `deleteAllByIdInBatch`) contournent le contexte de persistance : la cascade et l'`orphanRemoval` ne s'appliquent pas. Sur des contrats, elles laisseraient des paiements orphelins ou échoueraient sur la clé étrangère.
- `@Repository` n'est pas nécessaire sur une interface Spring Data.

---

## B. Anomalies SonarQube for IDE

| Anomalie | Règle / explication | Correction apportée |
| :--- | :--- | :--- |
| Import inutilisé | Un import n'est utilisé nulle part : code mort qui alourdit la lecture. | Suppression de l'import. |
| `@Data` sur une entité | `@Data` génère `equals`/`hashCode`/`toString` sur tous les champs, ce qui pose problème avec les associations JPA (boucles, chargement paresseux). | Lombok ciblé : `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`. |
| Nom non conforme / code commenté | Un identifiant ne respecte pas la convention Java, ou du code commenté reste dans le fichier. | Renommage selon la convention, suppression du code commenté. |