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

		for (int y = 0; y < plateau.length; ++y) {
			for (int x = 0; x < plateau.length; ++x) {
				if (plateau[x][y] == joueur) 
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
			int current = plateau[conversion1DColonne(numeros[i])][conversion1DLigne(numeros[i])];
			
			if (current == 1)
				plateau[conversion1DColonne(numeros[i])][conversion1DLigne(numeros[i])] = 2;

			if (current == 2)
				plateau[conversion1DColonne(numeros[i])][conversion1DLigne(numeros[i])] = 1;
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
		for (int y = 0; y < taille; ++y) {
			for (int x = 0; x < taille; ++x) {
				plateau[x][y] = 0;
				// La partie haute du milieu du plateau
				if (x == (milieu - 1) && y == (milieu - 1))
					plateau[x][y] = 1; // On met "X"
				if (x == milieu && y == (milieu - 1))
					plateau[x][y] = 2; // On met "O"

				// La partie basse du milieu du plateau
				if (x == (milieu - 1) && y == milieu)
					plateau[x][y] = regle ? 1 : 2; // Si regle est "true" on met "X" sinon on met "O"
				if (x == milieu && y == milieu) 
					plateau[x][y] = regle ? 2 : 1; // Si regle est "true" on met "O" sinon on met "X"
			}
		}

		// Notre jeu a bien été initialisé
		return true;
	}

	/**
	 * Vérifie que les corrdonnées appartiennent au plateaux.
	 * 
	 * @param i correspond au x sur le plateau
	 * @param j correspond au y sur le plateau
	 * @return true si les coordonnées sont sur le plateau, retourn false sinon
	 */
	public static boolean caseCorrecte(int i, int j) {
		return (i >= 0 && i < taille) && (j >= 0 && j < taille);
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
	 * @param ligne coordonée x
	 * @param colonne coordonée y
	 * @return le numéro de case
	 */
	public static int conversion2D1D(int ligne, int colonne) {
		return colonne * taille + ligne;
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
		for (int x = 0; x < taille; ++x) {
			System.out.print((char)(65 + x) + "|");
		}
		System.out.println();

		// On dessine les lignes
		for (int y = 0; y < taille; ++y) {
			// D'abord le numéro de ligne
			System.out.print(String.format("%03d", y) + "|");
			for (int x = 0; x < taille; ++x) {
				if (plateau[x][y] == 1) 
					System.out.print("X");
				else if (plateau[x][y] == 2)
					System.out.print("O");
				else if (tableauContient(casesSurlignees, conversion2D1D(x, y)))
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

		for (int y = 0; y < taille; ++y) {
			for (int x = 0; x < taille; ++x) {
				sauvegardePlateau[x][y] = plateau[x][y];
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
		int valeurCase = plateau[conversion1DColonne(numero)][conversion1DLigne(numero)];

		return valeurCase == 1 || valeurCase == 2;
	}

	/**
	 * Echange la valeur d'une piece vers l'oposé
	 * 
	 * @param numero de la pièce à retourner
	 */
	public static void retournePiece(int numero) {
		if (contientPiece(numero)) {
			switch (plateau[conversion1DColonne(numero)][conversion1DLigne(numero)]) {
			case 1:
				plateau[conversion1DColonne(numero)][conversion1DLigne(numero)] = 2;
				break;
			case 2:
				plateau[conversion1DColonne(numero)][conversion1DLigne(numero)] = 1;
				break;
			default:
				break;
			}
		}
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
		int[] coupsPossibles = new int[taille];
		int nombreDeCoups = 0;
		int joueurOppose = (joueur == 1) ? 2 : 1;

		int i = 0;
		int y = 0;
		int x = 0;

		// En direction du nord
		if (direction == "N" && conversion1DLigne(numero) > 0) {
			x = conversion1DColonne(numero);
			y = conversion1DLigne(numero) - 1;

			while (plateau[x][y] == joueurOppose && y >= 0) {
				coupsPossibles[i] = conversion2D1D(x, y);
				++nombreDeCoups;
				++i;
				--y;
			}
		}
		
		// En direction de l'est
		if (direction == "E" && conversion1DColonne(numero) < taille - 1) {
			x = conversion1DColonne(numero) + 1;
			y = conversion1DLigne(numero);

			while (plateau[x][y] == joueurOppose && x < taille) {
				coupsPossibles[i] = conversion2D1D(x, y);
				++nombreDeCoups;
				++i;
				++x;
			}
		}

		// En direction du sud
		if (direction == "S" && conversion1DLigne(numero) < taille - 1) {
			x = conversion1DColonne(numero);
			y = conversion1DLigne(numero) + 1;

			while (plateau[x][y] == joueurOppose && y < taille) {
				coupsPossibles[i] = conversion2D1D(x, y);
				++nombreDeCoups;
				++i;
				++y;
			}
		}

		// En direction de l'ouest
		if (direction == "O" && conversion1DColonne(numero) > 0) {
			x = conversion1DColonne(numero) - 1;
			y = conversion1DLigne(numero);

			while (plateau[x][y] == joueurOppose && x >= 0) {
				coupsPossibles[i] = conversion2D1D(x, y);
				++nombreDeCoups;
				++i;
				--x;
			}
		}

		// En direction du nord ouest
		if (direction == "NO" && conversion1DColonne(numero) > 0 && conversion1DLigne(numero) > 0) {
			x = conversion1DColonne(numero) - 1;
			y = conversion1DLigne(numero) - 1;

			while (plateau[x][y] == joueurOppose && x >= 0 && y >= 0) {
				coupsPossibles[i] = conversion2D1D(x, y);
				++nombreDeCoups;
				++i;
				--x;
				--y;
			}
		}

		// En direction du nord est
		if (direction == "NE" && conversion1DColonne(numero) < taille - 1 && conversion1DLigne(numero) > 0) {
			x = conversion1DColonne(numero) + 1;
			y = conversion1DLigne(numero) - 1;

			while (plateau[x][y] == joueurOppose && x < taille && y >= 0) {
				coupsPossibles[i] = conversion2D1D(x, y);
				++nombreDeCoups;
				++i;
				++x;
				--y;
			}
		}

		// En direction du sud est
		if (direction == "SE" && conversion1DColonne(numero) < taille - 1 && conversion1DLigne(numero) < taille - 1) {
			x = conversion1DColonne(numero) + 1;
			y = conversion1DLigne(numero) + 1;

			while (plateau[x][y] == joueurOppose && x < taille && y < taille) {
				coupsPossibles[i] = conversion2D1D(x, y);
				++nombreDeCoups;
				++i;
				++x;
				++y;
			}
		}

		// En direction du sud est
		if (direction == "SO" && conversion1DColonne(numero) > 0 && conversion1DLigne(numero) < taille - 1) {
			x = conversion1DColonne(numero) - 1;
			y = conversion1DLigne(numero) + 1;

			while (plateau[x][y] == joueurOppose && x >= 0 && y < taille) {
				coupsPossibles[i] = conversion2D1D(x, y);
				++nombreDeCoups;
				++i;
				--x;
				++y;
			}
		}

		int[] result = new int[nombreDeCoups];
		for (int j = 0; j < nombreDeCoups; ++j) {
			result[j] = coupsPossibles[j];
		}

		return result;
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
		for (coupPossible = depart + (tailleTest + offset); (conversion1DColonne(coupPossible) >= 0) || (conversion1DLigne(coupPossible) >= 0); coupPossible += (tailleTest + offset)) {
			if (plateau[conversion1DColonne(coupPossible)][conversion1DLigne(coupPossible)] == joueur)
				break;

			if (plateau[conversion1DColonne(coupPossible)][conversion1DLigne(coupPossible)] == joueurOppose) {
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
			if (plateau[conversion1DColonne(i)][conversion1DLigne(i)] == joueur) {
				int checkX = conversion1DColonne(i);
				int checkY = conversion1DLigne(i);

				// Nord Ouest
				if (checkX > 0 && checkY > 0) {
					int[] result = coupsPossibleDansDir(i, joueurOppose, -taille, -1);

					if (result[0] == 1) {
						coups[nbreCoupsPossible] = result[1];
						++nbreCoupsPossible;
					}
				}

				// Nord
				if (checkX > 0) {
					int[] result = coupsPossibleDansDir(i, joueurOppose, -taille, 0);

					if (result[0] == 1) {
						coups[nbreCoupsPossible] = result[1];
						++nbreCoupsPossible;
					}
				}

				// Nord Est
				if (checkX < taille - 1 && checkY > 0) {
					int[] result = coupsPossibleDansDir(i, joueurOppose, -taille, 1);

					if (result[0] == 1) {
						coups[nbreCoupsPossible] = result[1];
						++nbreCoupsPossible;
					}
				}

				// Ouest
				if (checkY > 0) {
					int[] result = coupsPossibleDansDir(i, joueurOppose, 0, -1);

					if (result[0] == 1) {
						coups[nbreCoupsPossible] = result[1];
						++nbreCoupsPossible;
					}
				}

				// Est
				if (checkY < taille - 1) {
					int[] result = coupsPossibleDansDir(i, joueurOppose, 0, 1);

					if (result[0] == 1) {
						coups[nbreCoupsPossible] = result[1];
						++nbreCoupsPossible;
					}
				}

				// Sud Ouest
				if (checkX < taille - 1 && checkY > 0) {
					int[] result = coupsPossibleDansDir(i, joueurOppose, taille, -1);

					if (result[0] == 1) {
						coups[nbreCoupsPossible] = result[1];
						++nbreCoupsPossible;
					}
				}

				// Sud
				if (checkX < taille - 1) {
					int[] result = coupsPossibleDansDir(i, joueurOppose, taille, 0);

					if (result[0] == 1) {
						coups[nbreCoupsPossible] = result[1];
						++nbreCoupsPossible;
					}
				}

				// Sud Est
				if (checkX < taille - 1 && checkY < taille - 1) {
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

		plateau[conversion1DColonne(numero)][conversion1DLigne(numero)] = joueur;

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
	


	
	/************************ Partie 4 ************************/
	


	
	/*************************** main ***************************/
	public static void main(String[] args) {
		init(6, false);

		// 0 0 0 0 0 0 
		// 0 0 - X 0 0   
		// X X X X 0 0   
		// 0 0 - X 0 0 
		// 0 0 0 0 0 0 
		// 0 0 0 0 0 0 
		plateau = new int[][] { 
			{0, 0, 1, 0, 0, 0 },
			{0, 0, 1, 0, 0, 0 },
			{0, 2, 1, 2, 0, 0 },
			{0, 1, 1, 1, 0, 0 },
			{0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0 },
		};

		joueur = 1;

		int[] caseSurLigne = {0,3,13};
		affiche();
		score();
		aide(1);
		joueCoup(2);
		affiche();
		// System.out.println(lettreVersNombre('A'));
	}

}
