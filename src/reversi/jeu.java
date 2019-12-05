package reversi;

import java.util.*;

///////////////////////////////////////////////
//////////// NOTE DE MODELISATION ////////////
///////////////////////////////////////////////
//
//
// Modélisation de plateau 6 x 6
//
// 0 0 0 0 0 0  
// 0 0 0 0 0 0  
// 0 0 X - 0 0  
// 0 0 - X 0 0  
// 0 0 0 0 0 0  
// 0 0 0 0 0 0  
//
//
//
// 0 0 0 0 0 0  
// 0 0 0 0 0 0  
// 0 0 X - 0 0  
// 0 0 - X 0 0  
// 0 0 0 0 0 0  
// 0 0 0 0 0 0  
//
//
// Modélisation de la linéarisation
//
// 00 01 02 03 04 05
// 06 07 08 09 10 11
// 12 13 14 15 16 17
// 18 19 20 21 22 23
// 24 25 26 27 28 29
// 30 31 32 33 34 35
//				   
// Linéarisation de 09 >>> 9 = (1, 3) : 1 * 6 + 3
// Formule générale    >>> n = (x, y) : x * taille + y

public class jeu {
	
	//variables globales
	public static Scanner sc; // Le scanner utilisé pour récupérer les entrés joueurs

	public static int joueur; // Correspond au numéro du joueur jouant le tour
	/**
	 * Correspond à la taille du plateau (qui est un carré de taille x taille)
	 * 0 : case vide
	 * 1 : Joueur 1 (correspond au pion "X")
	 * 2 : Joueur 2 (correspond au pion "O")
	 */
	public static int taille;
	public static int[][] plateau; // Contient les pions
	public static boolean passe; // Indique si le joueur précédent à passé son tour.


	/**
	 * Vérifie qu'un tableau d'entier contient un numéro
	 * 
	 * @param tab le tableau dans lequelle on fait la recherche
	 * @param num le numéro recherché dans le tableau
	 * @return true s'il le contient, false sinon
	 */
	public static boolean tableauContient(int[] tab, int num) {
		for (int i = 0; i < tab.length; ++i) {
			if (tab[i] == num)
				return true;
		}

		return false;
	}

	/**
	 * Retourne le score d'un joueur, c'est à dire le nombre de ses pièces présentes sur le plateau
	 * 
	 * @param joueur
	 * @return le score du joueur
	 */
	public static int scoreJoueur(int joueur) {
		int score = 0;

		for (int ligne = 0; ligne < plateau.length; ++ligne) {
			for (int colonne = 0; colonne < plateau.length; ++colonne) {
				if (plateau[ligne][colonne] == joueur) 
					++score;
			}
		}

		return score;
	}

	public static int lettreVersNombre(char lettre) {
		return (int)(lettre) - 65;
	}
	
	/**
	 * Permet d'inverser des cases
	 * 
	 * @param numeros les numéros des cases à inverser
	 */
	public static void inverseCases(int[] numeros) {
		for (int i = 0; i < numeros.length; ++i) {
			int current = plateau[conversion1DLigne(numeros[i])][conversion1DColonne(numeros[i])];
			
			if (current == 1)
				plateau[conversion1DLigne(numeros[i])][conversion1DColonne(numeros[i])] = 2;

			if (current == 2)
				plateau[conversion1DLigne(numeros[i])][conversion1DColonne(numeros[i])] = 1;
		}
	}

	/************************ Partie 1 ************************/
	/**
	 * Cette fonction initialise le jeu
	 * @param taille 
	 * @param regle
	 * @return retourn false si la taille en sortie n'est pas un nombre paire (rendant le jeu impossible)
	 */
	public static boolean init(int taillePlateau, boolean regle)  {
		// Si la taille est impaire on retourne false
		if (taillePlateau % 2 != 0)
			return false;

		// Initialisation des variables simples
		taille = taillePlateau;
		plateau = new int[taille][taille];
		joueur = 1;
		passe = false;

		// On commence par calculer le millieu pour savoir ou placer les premiers pions
		int milieu = taille / 2;
		// Initialisation du plateau
		for (int ligne = 0; ligne < taille; ++ligne) {
			for (int colonne = 0; colonne < taille; ++colonne) {
				plateau[ligne][colonne] = 0;
				// La partie haute du milieu du plateau
				if (ligne == milieu - 1 && colonne == milieu - 1)
					plateau[ligne][colonne] = 1; // On met "X"
				if (ligne == milieu - 1 && colonne == milieu)
					plateau[ligne][colonne] = 2; // On met "O"

				// La partie basse du milieu du plateau
				if (ligne == milieu && colonne == milieu - 1)
					plateau[ligne][colonne] = regle ? 1 : 2; // Si regle est "true" on met "X" sinon on met "O"
				if (ligne == milieu && colonne == milieu) 
					plateau[ligne][colonne] = regle ? 2 : 1; // Si regle est "true" on met "O" sinon on met "X"
			}
		}

		// Notre jeu a bien été initialisé
		return true;
	}

	/**
	 * Vérifie que les corrdonnées appartiennent au plateaux.
	 * 
	 * @param ligne correspond au y sur le plateau
	 * @param colonne correspond au x sur le plateau
	 * @return true si les coordonnées sont sur le plateau, retourn false sinon
	 */
	public static boolean caseCorrecte(int ligne, int colonne) {
		return (ligne >= 0 && ligne < taille) && (colonne >= 0 && colonne < taille);
	}

	/**
	 * Vérifie que le numéro de case est valide
	 * @param i numéro de la case
	 * @return true sur le numéro est sur le plateau, false si il ne l'est pas
	 */	
	public static boolean caseCorrecte(int i){
		int nbreCase = taille * taille;

		if (i >= nbreCase)
			return false;
		
		return true;
	}

	/**
	 * Conversion de coordonnées en numéro de case
	 * 00 01 02 03 04 05
	 * 06 07 08 09 10 11
	 * 12 13 14 15 16 17
	 * 18 19 20 21 22 23
	 * 24 25 26 27 28 29
	 * 30 31 32 33 34 35
	 * 				
	 * Linéarisation de 09 >>> 9 = (1, 3) : 1 * 6 + 3
	 * Formule générale    >>> n = (x, y) : x * taille + y
	 * 
	 * @param ligne coordonée y
	 * @param colonne coordonée x
	 * @return le numéro de case
	 */
	public static int conversion2D1D(int ligne, int colonne) {
		return ligne * taille + colonne;
	}

	/**
	 * Retourne la ligne correspondant à un numéro de case
	 * @param numero 
	 * @return le numéro de la ligne
	 */
	public static int conversion1DLigne(int numero) {
		return numero / taille;
	}

	/**
	 * Forumule pour trouver la colonne sachant qu'on connait n, x (numéro / taille) et t
	 * n = x * t + y
	 * n - x * t = y
	 * 
	 * Récupère le numéro de colonne d'un numéro de case
	 * @param numero
	 * @return le numéro de la colonne
	 */
	public static int conversion1DColonne(int numero) {
		int ligne = numero / taille;

		return numero - ligne * taille;
	}

	/**
	 * Affiche le plateau en surlignant des cases données dans un tableau
	 * @param casesSurlignees les numéros des cases à surligner
	 */
	public static void affiche(int[] casesSurlignees) {
		System.out.println();

		// On dessine les lettres
		System.out.print("   |");
		for (int colonne = 0; colonne < taille; ++colonne) {
			System.out.print((char)(65 + colonne) + "|");
		}
		System.out.println();

		// On dessine les lignes
		for (int ligne = 0; ligne < taille; ++ligne) {
			// D'abord le numéro de ligne
			System.out.print(String.format("%03d", ligne) + "|");
			for (int colonne = 0; colonne < taille; ++colonne) {
				if (plateau[ligne][colonne] == 1) 
					System.out.print("X");
				else if (plateau[ligne][colonne] == 2)
					System.out.print("O");
				else if (tableauContient(casesSurlignees, conversion2D1D(ligne, colonne)))
					System.out.print("#");
				else 
					System.out.print(" ");

				System.out.print("|");
			}
			System.out.println();
		}

		System.out.println();

	}

	/**
	 * Affiche le plateau sans surligner de  case
	 */
	public static void affiche() {
		int[] surligne = new int[0];

		affiche(surligne);
	}

	/**
	 * Affiche les scores des joueurs
	 * 
	 * @return 0 si les deux joueurs sont à égalité, 1 si le joueur 1 domine, 2 si le joueur 2 domine
	 */
	public static int score() {
		// On calcule le score
		int scoreJoueur1 = scoreJoueur(1);
		int scoreJoueur2 = scoreJoueur(2);

		// On affiche le score
		System.out.println("====== SCORES ======");
		System.out.println("Joueur 1 : " + scoreJoueur1 + " pion" + (scoreJoueur1 > 1 ? "s" : ""));
		System.out.println("Joueur 2 : " + scoreJoueur2 + " pion" + (scoreJoueur2 > 1 ? "s" : ""));
		System.out.println();

		if (scoreJoueur1 > scoreJoueur2) {
			System.out.println("Le joueur 1 à l'avantage");
			System.out.println("====================");
			return 1;
		}
		
		if (scoreJoueur2 > scoreJoueur1) {
			System.out.println("Le joueur 2 à l'avantage");
			System.out.println("====================");
			return 2;
		}

		System.out.println("Les deux joueurs sont à égalité.");
		System.out.println("====================");
		return 0;
	}

	/**
	 * Créer une copie du plateau
	 * 
	 * @return une copie du plateau
	 */
	public static int[][] sauvegarde() {
		int[][] sauvegardePlateau = new int[taille][taille];

		for (int ligne = 0; ligne < taille; ++ligne) {
			for (int colonne = 0; colonne < taille; ++colonne) {
				sauvegardePlateau[ligne][colonne] = plateau[ligne][colonne];
			}
		}

		return sauvegardePlateau;
	}

	/**
	 * Replace les variables globales par celle-ci
	 */
	public static void charge(int[][] nouvPlateau, int nouvJoueur, boolean nouvPasse) {
		plateau = nouvPlateau;
		taille = nouvPlateau[0].length;
		joueur = nouvJoueur;
		passe = nouvPasse;
	}

	/************************ Partie 2 ************************/

	/**
	 * Vérifie que le format d'un string est valide, c'est à dire qu'il correspond à une case du plateau.
	 * Par exemple, 25A peut correspondre à une case, mais pas B2T
	 * 
	 * @param input
	 * @return true si valide, false si invalide
	 */
	public static boolean verifierFormat(String input) {
		int nombre = -1;
		String lettre = "";
		
		// On extrait le nombre et la lettre
		int i = 0;
		do {
			// On vérifie que tout le mot est un nombre. Sinon, on essai sur le mot moins 
			// le dernier caractère, sinon on enlève encore le caractère d'avant, etc.
			try {
				nombre = Integer.parseInt(input.substring(0, input.length() - i)); 
				lettre = input.substring(input.length() - i, input.length()); // Un fois qu'on à le nombre, on peut déduire la suite
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			++i;
		} while (nombre == -1 && i < input.length());

		// Si on a pas trouvé de nombre ou que la lettre n'est pas juste une lettre, c'est d'office invalide.
		if (nombre < 0 || lettre.length() > 1)
			return false;

		if (caseCorrecte(nombre, lettreVersNombre(lettre.charAt(0))))
			return true;

		return false;
	}

	/**
	 * Convertie une chaine de caractère en un numéro de case
	 * 
	 * @param input la chaine à convertir
	 * @return le numéro de case
	 */
	public static int conversionChaineNumero(String input) {
		int nombre = -1;
		String lettre = "";
		
		// On extrait le nombre et la lettre
		int i = 0;
		do {
			// On vérifie que tout le mot est un nombre. Sinon, on essai sur le mot moins 
			// le dernier caractère, sinon on enlève encore le caractère d'avant, etc.
			try {
				nombre = Integer.parseInt(input.substring(0, input.length() - i)); 
				lettre = input.substring(input.length() - i, input.length()); // Un fois qu'on à le nombre, on peut déduire la suite
			}
			catch (Exception e) {
				// e.printStackTrace();
			}

			++i;
		} while (nombre == -1 && i < input.length());

		return conversion2D1D(nombre, lettreVersNombre(lettre.charAt(0)));
	}

	/**
	 * Vérifie qu'une case contient une pièce
	 */
	public static boolean contientPiece(int numero) {
		int valeurCase = plateau[conversion1DLigne(numero)][conversion1DColonne(numero)];

		return valeurCase == 1 || valeurCase == 2;
	}

	/**
	 * Echange la valeur d'une piece vers l'oposé
	 * 
	 * @param numero de la pièce à retourner
	 */
	public static void retournePiece(int numero) {
		if (contientPiece(numero)) {
			switch (plateau[conversion1DLigne(numero)][conversion1DColonne(numero)]) {
			case 1:
				plateau[conversion1DLigne(numero)][conversion1DColonne(numero)] = 2;
				break;
			case 2:
				plateau[conversion1DLigne(numero)][conversion1DColonne(numero)] = 1;
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Donne tous les pions retournés dans une direction indiqué par les différents offset
	 * 
	 * @param numero numéro de la case testé
	 * @param joueurOppose numéro du joueur opposé 
	 * @param verticalOffset indique la direction verticale
	 * @param horizontalOffset indique la direction horizontale
	 * 
	 * @return un tableau avec tous les coups possibles
	 */
	public static int[] pionsRetournes(int numero, int joueurOppose, int verticalOffset, int horizontalOffset) {
		int i = 0;
		int nbreCoupsPossible = 0;
		int[] coupsPossibles = new int[taille];

		int ligne = conversion1DLigne(numero) + verticalOffset;
		int colonne = conversion1DColonne(numero) + horizontalOffset;

		while (ligne >= 0 && ligne < taille && colonne >= 0 && colonne < taille && plateau[ligne][colonne] == joueurOppose) { // Temps que la case est sur le plateay 
			coupsPossibles[i] = conversion2D1D(ligne, colonne);
			ligne += verticalOffset;
			colonne += horizontalOffset;
			++nbreCoupsPossible;
			++i;
		}

		// Si on à retourné une direction qui n'est pas un coup valide, on ne retourne rien
		if (ligne < 0 || ligne >= taille || colonne < 0 || colonne >= taille || plateau[ligne][colonne] != joueur) {
			coupsPossibles = new int[taille];
			nbreCoupsPossible = 0;
		}

		// On retourne un tableau contenant uniquement les coups possibles et pas de case "vide"
		int[] result = new int[nbreCoupsPossible];
		for (int j = 0; j < nbreCoupsPossible; ++j) {
			result[j] = coupsPossibles[j];
		}

		return result;
	}

	/**
	 * Permet de savoir tous les coups possibles dans une direction
	 * 
	 * @param joueur qui joue (on cherche donc à retourner les opposés)
	 * @param numero case de départ
	 * @param direction direction de jeu
	 * @return toutes les pièces pouvant être retournées
	 */
	public static int[] retourneDir(int joueur, int numero, String direction) {
		int[] coupsPossibles = {};
		int joueurOppose = (joueur == 1) ? 2 : 1;

		// On utilise les offset :
		// - verticalement : -1 va vers le nord et 1 vers le sud
		// - horizontalement : -1 va vers l'ouest et 1 vers l'est
		
		// En direction du nord
		if (direction == "N" && conversion1DLigne(numero) >= 0) 
			coupsPossibles = pionsRetournes(numero, joueurOppose, -1, 0);
		// En direction de l'est
		if (direction == "E" && conversion1DColonne(numero) < taille) 
			coupsPossibles = pionsRetournes(numero, joueurOppose, 0, 1);
		// En direction du sud
		if (direction == "S" && conversion1DLigne(numero) < taille) 
			coupsPossibles = pionsRetournes(numero, joueurOppose, 1, 0);
		// En direction de l'ouest
		if (direction == "O" && conversion1DColonne(numero) >= 0) 
			coupsPossibles = pionsRetournes(numero, joueurOppose, 0, -1);
		// En direction du nord ouest
		if (direction == "NO" && conversion1DLigne(numero) >= 0 && conversion1DColonne(numero) >= 0) 
			coupsPossibles = pionsRetournes(numero, joueurOppose, -1, -1);
		// En direction du nord est
		if (direction == "NE" && conversion1DLigne(numero) >= 0 && conversion1DColonne(numero) < taille) 
			coupsPossibles = pionsRetournes(numero, joueurOppose, -1, 1);
		// En direction du sud est
		if (direction == "SE" && conversion1DLigne(numero) < taille && conversion1DColonne(numero) < taille)
			coupsPossibles = pionsRetournes(numero, joueurOppose, 1, 1);
		// En direction du sud est
		if (direction == "SO" && conversion1DLigne(numero) < taille && conversion1DColonne(numero) >= 0) 
			coupsPossibles = pionsRetournes(numero, joueurOppose, 1, -1);

		return coupsPossibles;
	}

	/**
	 * 0 0 0 0 0 0 
	 * 0 0 - X 0 0    7 
	 * X X X X 0 0   14 | 14 - (taille + 1)
	 * 0 0 - X 0 0 
	 * 0 0 0 0 0 0 
	 * 0 0 0 0 0 0 
	 * 
	 * Cette fonction permet de verifier ET obtenir le coup possible dans une direction donnée
	 * 
	 * @param depart la case pour laquelle on veut verifier le coup possible
	 * @param joueurOppose le joueur oppose à celui qui joue
	 * @param tailleTest permet de savoir si on cherche sur la même ligne (taill = 0), la ligne au dessus (taille négative) ou en dessous (taille positive)
	 * @param offset permet de savoir si on cherche à gauche (-1), au même niveau (0) ou a droite (1)
	 *  
	 * @return un tableau de deux int : le premier indiquant un coup possible (0 ou 1) et le second indiquand le numéro du coup possible s'il y en a un
	 */
	public static int[] coupsPossibleDansDir(int depart, int joueurOppose, int tailleTest, int offset) {
		boolean joueurPresent = false;
		boolean videPresent = false;
		int coupPossible = 0;
		for (coupPossible = depart + (tailleTest + offset); (conversion1DLigne(coupPossible) >= 0) && (conversion1DColonne(coupPossible) >= 0) && (conversion1DLigne(coupPossible) < taille) && (conversion1DColonne(coupPossible) < taille); coupPossible += (tailleTest + offset)) {
			if ((offset == -1 && conversion1DColonne(coupPossible) >= conversion1DColonne(depart)) || (offset == 1 && conversion1DColonne(coupPossible) <= conversion1DColonne(depart))) // Lorsqu'on va sur le côté ET haut/bas
				break;

			if (plateau[conversion1DLigne(coupPossible)][conversion1DColonne(coupPossible)] == joueur)
				break;

			if (plateau[conversion1DLigne(coupPossible)][conversion1DColonne(coupPossible)] == joueurOppose) {
				joueurPresent = true;
				continue;
			}

			if (!contientPiece(coupPossible)) {
				videPresent = true;
				break;
			}
		}

		int[] result = new int[2];

		if (joueurPresent && videPresent) {
			result[0] = 1;
			result[1] = coupPossible;
		}
		else {
			result[0] = 0;
			result[1] = 0;
		}

		return result;
	}

	/**
	 * Cette fonction permet d'obtenir tous les coup possibles pour le joueur actif
	 * 
	 * @return le numéro des coups possible
	 */
	public static int[]  possibleCoups() {
		int joueurOppose = (joueur == 1) ? 2 : 1;

		int[] coups = new int[taille * taille];
		int nbreCoupsPossible = 0;

		for (int i = 0; i < taille * taille; ++i) {
			if (plateau[conversion1DLigne(i)][conversion1DColonne(i)] == joueur) {
				int checkLigne = conversion1DLigne(i);
				int checkColonne = conversion1DColonne(i);

				// Nord Ouest
				if (checkLigne > 0 && checkColonne > 0) {
					int[] result = coupsPossibleDansDir(i, joueurOppose, -taille, -1);

					if (result[0] == 1) {
						coups[nbreCoupsPossible] = result[1];
						++nbreCoupsPossible;
					}
				}

				// Nord
				if (checkLigne > 0) {
					int[] result = coupsPossibleDansDir(i, joueurOppose, -taille, 0);

					if (result[0] == 1) {
						coups[nbreCoupsPossible] = result[1];
						++nbreCoupsPossible;
					}
				}

				// Nord Est
				if (checkLigne > 0 && checkColonne < taille - 1) {
					int[] result = coupsPossibleDansDir(i, joueurOppose, -taille, 1);

					if (result[0] == 1) {
						coups[nbreCoupsPossible] = result[1];
						++nbreCoupsPossible;
					}
				}

				// Ouest
				if (checkColonne > 0) {
					int[] result = coupsPossibleDansDir(i, joueurOppose, 0, -1);

					if (result[0] == 1) {
						coups[nbreCoupsPossible] = result[1];
						++nbreCoupsPossible;
					}
				}

				// Est
				if (checkColonne < taille - 1) {
					int[] result = coupsPossibleDansDir(i, joueurOppose, 0, 1);

					if (result[0] == 1) {
						coups[nbreCoupsPossible] = result[1];
						++nbreCoupsPossible;
					}
				}

				// Sud Ouest
				if (checkLigne < taille - 1 && checkColonne > 0) {
					int[] result = coupsPossibleDansDir(i, joueurOppose, taille, -1);

					if (result[0] == 1) {
						coups[nbreCoupsPossible] = result[1];
						++nbreCoupsPossible;
					}
				}

				// Sud
				if (checkLigne < taille - 1) {
					int[] result = coupsPossibleDansDir(i, joueurOppose, taille, 0);

					if (result[0] == 1) {
						coups[nbreCoupsPossible] = result[1];
						++nbreCoupsPossible;
					}
				}

				// Sud Est
				if (checkLigne < taille - 1 && checkColonne < taille - 1) {
					int[] result = coupsPossibleDansDir(i, joueurOppose, taille, 1);

					if (result[0] == 1) {
						coups[nbreCoupsPossible] = result[1];
						++nbreCoupsPossible;
					}
				}

			}
		}

		int[] result = new int[nbreCoupsPossible];
		for (int i = 0; i < nbreCoupsPossible; ++i) {
			result[i] = coups[i];
		}

		return result;
	}

	/**
	 * Joue le coup
	 * 
	 * @param numero la case ou le pion est placé
	 * @return true si le coup est valide, false sinon
	 */
	public static boolean joueCoup(int numero) {
		if (!tableauContient(possibleCoups(), numero))
			return false;

		int[] coupsNordsOuest = retourneDir(joueur, numero, "NO");
		int[] coupsNords = retourneDir(joueur, numero, "N");
		int[] coupsNordsEst = retourneDir(joueur, numero, "NE");
		int[] coupsOuest = retourneDir(joueur, numero, "O");
		int[] coupsEst = retourneDir(joueur, numero, "E");
		int[] coupsSudOuest = retourneDir(joueur, numero, "SO");
		int[] coupsSud = retourneDir(joueur, numero, "S");
		int[] coupsSudEst = retourneDir(joueur, numero, "SE");

		inverseCases(coupsNordsOuest);
		inverseCases(coupsNords);
		inverseCases(coupsNordsEst);
		inverseCases(coupsOuest);
		inverseCases(coupsEst);
		inverseCases(coupsSudOuest);
		inverseCases(coupsSud);
		inverseCases(coupsSudEst);

		plateau[conversion1DLigne(numero)][conversion1DColonne(numero)] = joueur;

		return true;

	}

	/**
	 * Permet de voir les coups possibles pour un joueur
	 * 
	 * @param version ? 
	 */
	public static void aide(int version) {
		affiche(possibleCoups());
	}

	/************************ Partie 3 ************************/
	/**
	 * Permet d'attendre que le joueur appuis sur une touche du clavier pour continuer
	 */
	public static void attendre() {
		System.out.println("Appuyer sur entré pour continuer...");
		sc.nextLine();
	}

	/**
	 * Dessine de nombreuses ligne pour donner l'impression qu'on passe à un nouvel écran de console
	 */
	public static void ecranSuivant() {
		for (int i = 0; i < 25; ++i) 
			System.out.println();
	}

	public static String menuEnJeu() {
		System.out.println("Que voulez vous faire avant votre tour ?");
		System.out.println("(save, help, exit ou none)");
		
		return sc.nextLine();
	}

	/**
	 * Boule du jeu
	 * 
	 * @param regle la règle avec laquelle on joue
	 */
	public static void jeuBoucle(boolean regle) {
		boolean joueur1PeutJouer = true;
		boolean joueur2PeutJouer = true;

		while (joueur1PeutJouer && joueur2PeutJouer) {
			ecranSuivant();

			int[] coupsPossible = possibleCoups();

			// On affiche ce qu'il y a à afficher
			System.out.println("====================");
			System.out.println();
			affiche();
			System.out.println("Le joueur " + (joueur == 1 ? "1" : "2") + " à la main.");
			System.out.println();
			score();
			
			// On affiche le menu de sauvegarde
			String menuEnJeu = menuEnJeu();
			// sc.nextLine(); // Fix pour ne pas avoir de nextLine ignoré

			switch (menuEnJeu) {
			case "save":
				int[][] plateauASauvegarder = sauvegarde();
				break;
			case "help":
				aide(1);
				break;
			case "exit":
				return;
			case "none":
				break; 
			default:
				System.out.println("Cette option n'existe pas. Le jeu continue.");
			}
			
			// On vérifie que le joueur peut jouer et on met à jour les variables en fonctions
			if (coupsPossible.length < 1) {
				if (joueur == 1) 
					joueur1PeutJouer = false;
				else 
					joueur2PeutJouer = false;

				continue;
			}
			else {
				if (joueur == 1) 
					joueur1PeutJouer = true;
				else 
					joueur2PeutJouer = true;
			}

			// La, on est sur que le joueur peut jouer
			if (regle) {
				System.out.println("Voulez vous passer votre tour (Y/n) ?");
				String reponse = sc.nextLine();
				
				if (reponse.toLowerCase() == "Y") {
					if (joueur == 1) 
						joueur1PeutJouer = false;
					else 
						joueur2PeutJouer = false;
					
					continue;
				}
			}

			System.out.println("Veuillez indiquer la case que vous voulez jouer");
			boolean coupValide = false;

			while (!coupValide) {
				System.out.println("Choisissez le coup que vous voulez faire.");
				System.out.println("Vérifiez bien le format et sa possibilité, le coup vous sera demandé tant qu'il n'est pas valide");
				
				String coup = sc.nextLine();

				coupValide = verifierFormat(coup) && joueCoup(conversionChaineNumero(coup));
			}

			joueur = (joueur == 1) ? 2 : 1;
		}
	}


	/************************ Partie 4 ************************/
	


	
	/*************************** main ***************************/
	public static void main(String[] args) {
		sc = new Scanner(System.in);

		init(14, false);

		jeuBoucle(false);

		// // 0 0 0 0 0 0 
		// // 0 0 - X 0 0   
		// // X X X X 0 0   
		// // 0 0 - X 0 0 
		// // 0 0 0 0 0 0 
		// // 0 0 0 0 0 0 
		// plateau = new int[][] { 
		// 	{0, 0, 1, 0, 0, 0 },
		// 	{0, 0, 1, 0, 0, 0 },
		// 	{0, 2, 1, 2, 0, 0 },
		// 	{0, 1, 1, 1, 0, 0 },
		// 	{0, 0, 0, 0, 0, 0 },
		// 	{0, 0, 0, 0, 0, 0 },
		// };

		// joueur = 1;

		// int[] caseSurLigne = {0,3,13};
		// affiche(caseSurLigne);
		// score();
		// aide(1);
		// joueCoup(2);
		// affiche();
		// System.out.println(lettreVersNombre('A'));
	}

}
