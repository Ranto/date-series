## Socles techniques

- JDK 1.8+
- Maven 3.9.2
- JUnit 4

## Description

Génération d'une série de dates à partir de données de récurrence.

### Récurrence

- `frequency` `(requis)` : DAILY, WEEKLY, MONTHLY
- `startDate` `(requis)` : Date de début.
- `endDate` : Date de fin. La génération de la série de dates s'arrête quand la date de fin est atteinte ou le nombre des occurrences est atteint.
- `days` : tableaux de jours (`"MONDAY"`, `"TUESDAY"`, `"WEDNESDAY"`, `"THURSDAY"`, `"FRIDAY"`, `"SATURDAY"`, `"SUNDAY"`). Liste des jours dans lesquels les dates doivent être générées. Pour la fréquence WEEKLY, ces jours doivent être spécifiés. Pour les autres fréquences, ces données seront ignorées.
- `occurrences` : Le nombre maximal de dates à générer.
- `interval` : `(Défaut : 1)` Un nombre supérieur ou égal à 1 qui donne l'intervalle entre deux dates. Exemples :
    - Si `interval=2` et `frequency=WEEKLY` signifie toutes les 2 semaines.
    - Si `interval=3` et `frequency=DAILY` signifie tous les 3 jours.
    - Si `interval=1` et `frequency=MONTHLY` signifie tous les mois.
