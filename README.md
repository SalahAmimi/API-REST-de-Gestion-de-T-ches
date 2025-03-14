Solution pour l'API REST de Gestion de Tâches

1. Explication détaillée du code

L'API REST de gestion de tâches permet d'ajouter, de modifier, de supprimer et de récupérer des tâches via plusieurs endpoints. Elle est développée avec Spring Boot et utilise une base de données relationnelle via JPA.

Les principaux composants sont :

- **TaskController** : Gère les requêtes HTTP.
- **TaskService** : Contient la logique métier.
- **TaskRepository** : Interface JPA pour accéder à la base de données.
- **TaskDTO** : Objet de transfert des données (Data Transfer Object).
- **TaskDoesNotExist** : Exception personnalisée pour les tâches inexistantes.
- **Estatut** : Enum pour définir les différents statuts des tâches.

2. Structure du projet

Le projet suit une architecture MVC.
\`\`\`
|-- src/main/java/com/example/demo
    |-- controller/TaskController.java
    |-- service/TaskService.java
    |-- service/TaskServiceInterface.java
    |-- repository/TaskRepository.java
    |-- dto/TaskDTO.java
    |-- bo/Task.java
    |-- exception/TaskDoesNotExist.java
    |-- ApiRestApplication.java
\`\`\`

3. Implémentation des endpoints

### 1️⃣ Création d'une tâche
\`\`\`
POST /api/tasks/create
\`\`\`
- Reçoit un objet TaskDTO en JSON.
- Initialise \`dateCreation\` à l'instant actuel et \`statut\` à PENDING.
- Sauvegarde dans la base de données.

### 2️⃣ Récupération de toutes les tâches
\`\`\`
GET /api/tasks
\`\`\`
- Retourne la liste de toutes les tâches enregistrées.

### 3️⃣ Suppression d'une tâche
\`\`\`
DELETE /api/tasks/deleteById/{id}
\`\`\`
- Supprime une tâche si elle existe.
- Retourne une exception **TaskDoesNotExist** si l'ID est inexistant.

### 4️⃣ Modification du statut d'une tâche
\`\`\`
PUT /api/tasks/{id}?statut=IN_PROGRESS
\`\`\`
- Change le statut d'une tâche en **PENDING, IN_PROGRESS ou COMPLETED**.
- Retourne une erreur si le statut est invalide.

### 5️⃣ Filtrage par statut
\`\`\`
GET /api/tasks/ByStatut?statut=COMPLETED
\`\`\`
- Filtre les tâches en fonction de leur statut.

4. Logique métier

- **Création** : Une nouvelle tâche est créée avec un statut initial PENDING.
- **Récupération** : Retourne la liste complète des tâches.
- **Suppression** : Vérifie si la tâche existe avant de la supprimer.
- **Mise à jour du statut** : Modifie uniquement si la tâche existe.
- **Filtrage** : Retourne les tâches correspondant au statut demandé.

5. Choix techniques

✅ **Spring Boot** : Framework rapide et efficace.  
✅ **JPA/Hibernate** : Interface simplifiée pour la gestion des données.  
✅ **DTO** : Évite d'exposer directement les entités.  
✅ **Gestion d'exceptions** : \`TaskDoesNotExist\` permet une meilleure gestion des erreurs.

6. Tests effectués

✔ **Tests unitaires** : Création, suppression, mise à jour de statut.  
✔ **Tests via Postman** : Vérification des endpoints et gestion des erreurs.

L'API est donc fonctionnelle avec une gestion des erreurs robuste et une structure claire. 🚀
