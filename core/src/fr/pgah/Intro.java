package fr.pgah;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Intro extends ApplicationAdapter {

  // Déclaration de toutes les variables du programme

  SpriteBatch batch; // pour pouvoir dessiner à l'écran
  Texture[] imgs; // contient les images de chaque sprite
  int[] coordonneesX; // les coordonnées en X de chaque sprite
  int[] coordonneesY; // les coordonnées en Y de chaque sprite
  int[] hauteursImgs; // la hauteur de chaque sprite
  int[] largeursImgs; // la largeur de chaque sprite
  boolean[] versLeHaut; // indique, pour chaque sprite, s'il va vers le haut ou pas
  boolean[] versLaDroite; // indique, pour chaque sprite, s'il va vers la droite ou pas
  int hauteurFenetre; // la hauteur de la fenêtre (unique, pas un tableau)
  int largeurFenetre; // la largeur de la fenêtre

  // Définition de la méthode create
  // C'est là qu'on initialise toutes les variables (on leur donne une valeur).
  // Exécutée une seule fois au lancement du programme.
  // Attention : c'est juste quelque chose de décidé par la bibliothèque libgdx,
  // pas quelque chose d'usuel en Java
  public void create() {

    batch = new SpriteBatch(); // type objet => instanciation (new)

    imgs = new Texture[2]; // tableau = type objet aussi ; ici : 2 Textures

    // Chaque élément du tableau est lui-même un objet (Texture)
    // Il faut donc instancier une Texture pour chaque élément

    // Accès au 1er élément (notation indexée[])
    // Les tableaux sont indexés à partir de 0
    // On instancie (new) une nouvelle texture à chaque fois
    imgs[0] = new Texture("eddie.png"); // premier élément
    imgs[1] = new Texture("bomb.png"); // deuxième élément

    // Tableau de coordonnées en X pour chaque sprite
    // int n'est pas un type objet, pas besoin de "new" pour chaque élément
    coordonneesX = new int[2];
    coordonneesX[0] = 0;
    coordonneesX[1] = 100;

    // Idem pour les Y
    coordonneesY = new int[2];
    coordonneesY[0] = 0;
    coordonneesY[1] = 200;

    // Idem pour les hauteurs de chaque sprite
    hauteursImgs = new int[2];
    // Appels de la méthode getHeight définie sur les objets de type Texture.
    // C'est sur une autre classe (Texture) donc il faut utiliser la notation pointée.
    hauteursImgs[0] = imgs[0].getHeight();
    hauteursImgs[1] = imgs[1].getHeight();

    // Les largeurs pour chaque objet
    largeursImgs = new int[2];
    largeursImgs[0] = imgs[0].getWidth();
    largeursImgs[1] = imgs[1].getWidth();

    // Tableau indiquant, pour chaque sprite, s'il va vers le haut (case à vrai)
    // ou vers le bas (case à faux)
    versLeHaut = new boolean[2];
    versLeHaut[0] = true; // au début, le 1er spite va monter
    versLeHaut[1] = false; // et le 2ème va descendre

    // Tableau indiquant, pour chaque sprite, s'il va vers la droite (case à vrai)
    // ou vers la gauche (case à faux)
    versLaDroite = new boolean[2];
    versLaDroite[0] = true; // au début, le 1er spite va vers la droite
    versLaDroite[1] = false; // et le 2eme va vers la gauche

    // On demande à la bibliothèque la hauteur et la largeur de la fenêtre
    hauteurFenetre = Gdx.graphics.getHeight();
    largeurFenetre = Gdx.graphics.getWidth();

    // Quand une méthode est terminée (accolade fermante), Java retourne
    // dans le code appelant la méthode pour continuer l'exécution.
    // Ici comme "create" est appelé par la bibliothèque, c'est elle qui va
    // continuer l'exécution (elle va commencer à appeler "render")

  }

  // Définition de la méthode render
  // Exécutée par la bibliothèque 60 fois par secondes (pour avoir un rendu fluide)
  // Attention : c'est juste quelque chose de décidé par la bibliothèque libgdx,
  // pas quelque chose d'usuel en Java
  public void render() {

    // Chacune de ces 4 lignes est un "appel de méthode"
    // Lors d'un appel de méthode, le flux d'exécution se déplace dans la méthode
    // correspondante, l'exécute complètement, puis revient à l'appelant qui continue
    // son traitement
    // Ici on a donc 4 "sauts" dans les méthodes respectives

    // Ces appels n'utilisent pas la notation pointée car les méthodes se trouvent
    // dans cette classe

    reinitialiserArrierePlan();
    testerBordures();
    majCoordonnees();
    dessiner();

    // Notez comme le fait d'avoir décomposé la méthode en plusieurs autres méthodes
    // améliore la clarté et la concision de cette méhode

  }

  // Repeint l'arrière-plan
  public void reinitialiserArrierePlan() {

    // Un autre appel de méthode, cette fois vers la classe ScreenUtils,
    // code provenant de la bibliothèque libgdx qu'on n'a pas écrit.
    // Utilise la notation pointée.
    // Les paramètres de la méthode (entre parenthèses) précisent la couleur
    // souhaitée pour l'arrière-plan en RGBA (Red, Green, Blue, Alpha)
    ScreenUtils.clear(1, 0, 0, 1);
  }

  // Définition de la méthode testerBordures.
  // En vérifiant les coordonnées de chaque sprite,
  // s'assure qu'ils restent dans la fenêtre en modifiant
  // les tableaux de direction si besoin
  public void testerBordures() {

    // Pour tous les indices i de 0 à 1, faire...
    for (int i = 0; i <= 1; i++) {

      // Si le sprite tape en haut...
      if (coordonneesY[i] + hauteursImgs[i] == hauteurFenetre) {
        // on retient qu'il doit maintenant aller vers le bas
        versLeHaut[i] = false;
      }

      // Si le sprite tape en bas...
      if (coordonneesY[i] == 0) {
        // on retient qu'il doit maintenant aller vers le haut
        versLeHaut[i] = true;
      }

      // Si le sprite tape à droite...
      if (coordonneesX[i] + largeursImgs[i] == largeurFenetre) {
        // on retient qu'il doit maintenant aller vers la gauche
        versLaDroite[i] = false;
      }

      // Si le sprite tape à gauche...
      if (coordonneesX[i] == 0) {
        // on retient qu'il doit maintenant aller vers la droite
        versLaDroite[i] = true;
      }
    }
  }

  // Définition de la méthode majCoordonnees.
  // Utilise les tableaux de direction pour modifier
  // les coordonnées de chaque sprite
  private void majCoordonnees() {

    // MAJ coordonnées en X

    // Pour tous les indices de 0 à 1, faire...
    for (int i = 0; i <= 1; i++) {
      // Si le sprite i va vers la droite
      if (versLaDroite[i]) {
        // on augmente X
        coordonneesX[i] = coordonneesX[i] + 1; // incrémentation
      } else { // sinon
        // on diminue X (il va vers la gauche)
        coordonneesX[i] = coordonneesX[i] - 1; // décrémentation
      }
    }

    // MAJ coordonnées en Y

    // Pour tous les indices de 0 à 1, faire...
    for (int i = 0; i <= 1; i++) {
      // Si le sprite i va vers le haut
      if (versLeHaut[i]) {
        // on augmente Y
        coordonneesY[i] = coordonneesY[i] + 1; // incrémentation
      } else { // sinon
        // on dinminue Y (il va vers la gauche)
        coordonneesY[i] = coordonneesY[i] - 1; // décrémentation
      }
    }
  }

  // Définition de la méthode dessiner.
  // Dessine chaque sprite à l'écran au bonne coordonnées.
  private void dessiner() {
    // On indique au SpriteBatch qu'on va dessiner
    batch.begin();

    // Pour tous les indices i de 0 à 1, faire...
    for (int i = 0; i <= 1; i++) {
      // Dessiner le sprite i avec la bonne texture et les bonnes coordonnées
      batch.draw(imgs[i], coordonneesX[i], coordonneesY[i]);
    }

    // On indique au SpriteBatch qu'on a fini de dessiner
    batch.end();
  }
}
