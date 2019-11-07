package reversi;

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
	 * 1 : Joueur 1 (correspond au pion "O")
	 * 2 : Joueur 2 (correspond au pion "X")
	 */
	public static int taille;
	public static int[][] plateau; // Contient les pions
	public static boolean passe; // Indique si le joueur précédent à passé son tour.


	
	/************************ Partie 1 ************************/
	/**
	 * Cette fonction initialise le jeu
	 * @param taille 
	 * @param regle
	 * @return retourn false si la taille en sortie n'est pas un nombre paire (rendant le jeu impossible)
	 */
	public static boolean init(int taille, boolean regle)  {
		// Si la taille est impaire on retourne false
		if (taille % 2 != 0)
			return false;

		// Initialisation des variables simples
		joueur = 1;
		passe = false;

		// On commence par calculer le millieu pour savoir ou placer les premiers pions
		int milieu = taille / 2;
		// Initialisation du plateau
		for (int y = 0; y < taille; ++y) {
			for (int x = 0; x < taille; ++x) {
				// La partie haute du milieu du plateau
				if (x == (milieu - 1) && y == (milieu - 1))
					plateau[x][y] = 2; // On met "X"
				if (x == milieu && y == (milieu - 1))
					plateau[x][y] = 1; // On met "O"

				// La partie basse du milieu du plateau
				if (x == (milieu - 1) && y == milieu)
					plateau[x][y] = regle ? 2 : 1; // Si regle est "true" on met "X" sinon on met "O"
				if (x == milieu && y == milieu) 
					plateau[x][y] = regle ? 1 : 2; // Si regle est "true" on met "O" sinon on met "X"
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
		return (i >= 0 && i < taille) || (j >= 0 && j < taille);
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


	/************************ Partie 2 ************************/
	



	/************************ Partie 3 ************************/
	


	
	/************************ Partie 4 ************************/
	


	
	/*************************** main ***************************/
	public static void main(String[] args) {
	
	}

}
