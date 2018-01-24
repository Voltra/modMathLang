GUERIN Ludwig

JAOUEN Romain



# Compte Rendu - Modélisations Mathématiques : Modèles de Langages

## Avancement des projets

### Modèles de language

//

### Reconnaissance d'auteurs

Les classes de reconnaissance d'auteurs sont implémentées (`AuthorRecognizer1` et `UnknownRecognizer1`) et une classe additionnelle (`UnknownRecognizer2`).

## Algorithmes de Reconnaissance

### AuthorRecognizer1

L'algorithme implémenté est composé de deux étapes :

- Calcul des meilleures probabilités par auteur
- Calul de la meilleur probabilité parmi ces dernières

Pour chaque auteur défini dans le fichier de configuration,  nous obtenons le chemin vers le fichier contenant les `NgramCounts` associés, nous créons une instance de `NgramCounts`, créons un modèle de langage (avec lissage de Laplace) en utilisant ce `NgramCounts` et le vocabulaire associé.

Nous calculons ensuite la probabilité de la phrase pour chaque modèle de langage et nous ne conservons que la probabilité la plus élevée.

Nous obtenons ainsi un tableau associatif associant à chaque auteur la meilleur probabilité obtenue.

Ensuite, nous réduisons ce tableau associatif au seul tuple présentant la probabilité la plus élevée et déterminons que l'auteur faisant office de clé est l'auteur potentiel de la phrase.

### UnknownRecognizer1

Cet algorithme est composé de deux étapes :

* Calcul du tableau associatif de meilleurs probabilités par auteur
* Détermine si l'auteur est inconnu ou non

Nous utilisons le même algorithme pour générer le tableau associatif. Afin de déterminer si l'auteur de la phrase est inconnu (au modèle), nous avons utilisé le procédé suivant :

* Nous déterminons un `Delta` (fixé ici à 10^-6)

* Nous prenons la première probabilité `p`

* Nous déterminons pour chaque autre probabilité si cette dernière appartient à l'intervalle

   `[ p-Delta ; p+Delta ]`

* Si tel est le cas, alors nous déterminons que l'auteur de la phrase est inconnu



### UnknownRecognizer2

Cet algorithme est encore une fois, composé de deux étapes:

* Calcul de la probabilité selon `AuthorRecognizer1`
* Si la probabilité est inférieure à une valeur fixe (`Min`) alors l'auteur est considéré comme inconnu, sinon l'auteur correspondant est désigné comme en étant l'auteur.