package reversi;

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
	 * 
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
	


	/************************ Partie 2 ************************/
	



	/************************ Partie 3 ************************/
	


	
	/************************ Partie 4 ************************/
	


	
	/*************************** main ***************************/
	public static void main(String[] args) {
	
	}

}
