# Note de l'Atelier 5 - Spring MVC, Postman et Swagger

## A. Endpoints de l'API REST

| Contrôleur | Verbe et Route | Service appelé | Code Succès | Codes d'Erreur |
| :--- | :--- | :--- | :--- | :--- |
| `ContratController` | `GET /api/contrats` | `findAll()` | 200 OK | - |
| `ContratController` | `GET /api/contrats/{id}` | `findById(id)` | 200 OK | 404 Not Found |
| `ContratController` | `POST /api/contrats` | `create(c)` | 201 Created | 400 Bad Request |
| `ContratController` | `PUT /api/contrats/{id}` | `update(id, c)` | 200 OK | 400, 404 |
| `ContratController` | `DELETE /api/contrats/{id}` | `deleteById(id)` | 204 No Content | 404 Not Found |
| `PaiementController` | `GET /api/paiements` | `findAll()` | 200 OK | - |
| `PaiementController` | `GET /api/paiements/{id}` | `findById(id)` | 200 OK | 404 Not Found |
| `AgenceController` | `GET/POST/PUT/DELETE /api/agences` | CRUD agence | 200/201/204 | 400, 404 |
| `ClientController` | `GET/POST/PUT/DELETE /api/clients` | CRUD client | 200/201/204 | 400, 404 |
| `VehiculeController` | `GET/POST/PUT/DELETE /api/vehicules` | CRUD véhicule | 200/201/204 | 400, 404 |
| `EquipementController` | `GET/POST/PUT/DELETE /api/equipements` | CRUD équipement | 200/201/204 | 400, 404 |
| `EmployeController` | `GET/POST/PUT/DELETE /api/employes` | CRUD employé | 200/201/204 | 400, 404 |
| `ReservationController` | `GET/POST/PUT/DELETE /api/reservations` | CRUD réservation | 200/201/204 | 400, 404 |
| `MaintenanceController` | `GET/POST/PUT/DELETE /api/maintenances` | CRUD maintenance | 200/201/204 | 400, 404 |

---

## B. Annotations Jackson et Sérialisation

* **`@JsonManagedReference` / `@JsonBackReference`** : Utilisées sur l'association bidirectionnelle **Contrat ↔ Paiement** (qui est une véritable composition avec `cascade = ALL`). Permet d'éviter les boucles infinies de sérialisation JSON (`StackOverflowError`) en gérant la sérialisation directe du parent et en ignorant le retour vers l'enfant.
* **Différence avec `@JsonIgnore`** : `@JsonIgnore` coupe totalement la sérialisation dans les deux sens, tandis que la paire `Managed/Back` gère proprement l'arborescence parent-enfant.

---

## C. Postman et Swagger

* **Postman** : Permet de concevoir, rejouer et automatiser des requêtes HTTP (tests unitaires d'API, validations des statuts 200, 201, 400, 404).
* **Swagger UI** (`/api/swagger-ui.html`) : Offre une interface graphique interactive générée automatiquement par SpringDoc OpenAPI pour explorer, documenter et tester les endpoints en direct depuis le navigateur.

---

## D. Anomalies SonarQube

1. **Imports inutilisés** : Nettoyage des imports de packages superflus dans les classes Controllers.
2. **Injection par attribut** : Remplacement des `@Autowired` sur les attributs par l'injection par constructeur via `@RequiredArgsConstructor` et champs `final`.

---

## E. Questions de compréhension

1. **Quel composant reçoit toutes les requêtes, et comment retrouve-t-il la méthode du contrôleur à appeler ?**
    * C'est le `DispatcherServlet` qui intercepte la requête, et c'est le `HandlerMapping` qui analyse l'URL et le verbe HTTP pour identifier la méthode exacte du contrôleur.
2. **Pourquoi `ContratController` dépend-il de `IContratService` et non du Repository ni de l'Implémentation ?**
    * Pour respecter le principe de l'inversion de dépendances et le découplage en s'appuyant sur l'abstraction (l'interface) plutôt que sur les détails d'implémentation.
3. **Pourquoi un POST renvoie 201 et un DELETE 204 ? Pourquoi une ressource absente donne 404 ?**
    * `201 Created` indique qu'une ressource a été créée avec succès. `204 No Content` indique une suppression réussie sans corps de réponse. Le `404` est levé suite à la traduction de la `ResourceNotFoundException` par le gestionnaire d'exceptions.
4. **Pourquoi `PaiementController` n'expose-t-il que des GET ?**
    * Car la création et la modification des paiements se font par cascade directement à travers le cycle de vie du Contrat. Un POST direct renverrait une erreur 405 (Method Not Allowed).
5. **Quels risques présente l'exposition directe des entités JPA ?**
    * Un couplage fort entre la base de données et l'API, l'exposition non contrôlée de données sensibles ou internes, et des problèmes de performance liés aux charges paresseuses (LAZY).