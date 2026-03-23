import MG2D.*;
import MG2D.geometrie.*;
import java.awt.Font;
import java.util.Random;

public class Jeu2048 {

    private int[][] grille = new int[4][4];
    private Random random = new Random();

    public Jeu2048() {

        FenetrePleinEcran fenetre = new FenetrePleinEcran("2048");

        ClavierBorneArcade clavier = new ClavierBorneArcade();
        fenetre.addKeyListener(clavier);

        ajouterCase();
        ajouterCase();

        while(true) {

            if(clavier.getJoyJ1GaucheTape()) {
                if(moveLeft()) ajouterCase();
            }

            if(clavier.getJoyJ1DroiteTape()) {
                if(moveRight()) ajouterCase();
            }

            if(clavier.getJoyJ1HautTape()) {
                if(moveUp()) ajouterCase();
            }

            if(clavier.getJoyJ1BasTape()) {
                if(moveDown()) ajouterCase();
            }

            if(clavier.getBoutonJ1ZTape()) {
                System.exit(0);
            }

            fenetre.effacer();
            dessinerGrille(fenetre);
            fenetre.rafraichir();

            try {
                Thread.sleep(80);
            } catch(Exception e) {}
        }
    }


    private void ajouterCase() {
        while(true) {
            int i = random.nextInt(4);
            int j = random.nextInt(4);

            if(grille[i][j] == 0) {
                grille[i][j] = (random.nextInt(10) < 9) ? 2 : 4;
                return;
            }
        }
    }


    private boolean moveLeft() {
        boolean moved = false;

        for(int i = 0; i < 4; i++) {

            int[] old = grille[i].clone();

            int[] newRow = new int[4];
            int index = 0;

            for(int j = 0; j < 4; j++) {
                if(grille[i][j] != 0) {
                    newRow[index++] = grille[i][j];
                }
            }

            for(int j = 0; j < 3; j++) {
                if(newRow[j] == newRow[j+1] && newRow[j] != 0) {
                    newRow[j] *= 2;
                    newRow[j+1] = 0;
                }
            }

            int[] finalRow = new int[4];
            index = 0;

            for(int j = 0; j < 4; j++) {
                if(newRow[j] != 0) {
                    finalRow[index++] = newRow[j];
                }
            }

            grille[i] = finalRow;

            if(!java.util.Arrays.equals(old, finalRow)) {
                moved = true;
            }
        }

        return moved;
    }


    private boolean moveRight() {
        reverse();
        boolean moved = moveLeft();
        reverse();
        return moved;
    }


    private boolean moveUp() {
        transpose();
        boolean moved = moveRight();
        transpose();
        return moved;
    }

    private boolean moveDown() {
        transpose();
        boolean moved = moveLeft();
        transpose();
        return moved;
    }

    private void reverse() {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 2; j++) {
                int temp = grille[i][j];
                grille[i][j] = grille[i][3-j];
                grille[i][3-j] = temp;
            }
        }
    }

    private void transpose() {
        for(int i = 0; i < 4; i++) {
            for(int j = i; j < 4; j++) {
                int temp = grille[i][j];
                grille[i][j] = grille[j][i];
                grille[j][i] = temp;
            }
        }
    }

    public Couleur chooseCouleur(int nombre) {
        Couleur couleur;
        switch (nombre) {
            case 2:
                couleur = new Couleur(238, 228, 218); 
                break;
            case 4:
                couleur = new Couleur(237, 224, 200); 
                break;
            case 8:
                couleur = new Couleur(242, 177, 121); 
                break;
            case 16:
                couleur = new Couleur(245, 149, 99); 
                break;
            case 32:
                couleur = new Couleur(246, 124, 95); 
                break;
            case 64:
                couleur = new Couleur(246, 94, 59); 
                break;
            case 128:
                couleur = new Couleur(237, 207, 114); 
                break;
            case 256:
                couleur = new Couleur(237, 204, 97); 
                break;
            case 512:
                couleur = new Couleur(237, 200, 80); 
                break;
            case 1024:
                couleur = new Couleur(237, 197, 63); 
                break;
            case 2048:
                couleur = new Couleur(237, 194, 46); 
                break;
            default:
                couleur = Couleur.BLANC; 
                break;
        }
        return couleur;
    }

    private void dessinerGrille(FenetrePleinEcran f) {

        int largeur = f.getWidth();
        int hauteur = f.getHeight();
    
        int tailleGrille = Math.min(largeur, hauteur) - 100;
        int tailleCase = tailleGrille / 4;
    
        int offsetX = (largeur - tailleGrille) / 2;
        int offsetY = (hauteur - tailleGrille) / 2;
    
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
    
                int x = offsetX + j * tailleCase;
                int y = offsetY + i * tailleCase;
    
                Rectangle r = new Rectangle(
                    chooseCouleur(grille[i][j]), 
                    new Point(x, y),
                    tailleCase,
                    tailleCase,
                    true 
                );
                f.ajouter(r);
    
                Rectangle contour = new Rectangle(
                    Couleur.NOIR, 
                    new Point(x, y),
                    tailleCase,
                    tailleCase,
                    false 
                );
                f.ajouter(contour);
    
                if(grille[i][j] != 0) {
                    Texte t = new Texte(
                        Couleur.NOIR,
                        String.valueOf(grille[i][j]),
                        new Font("Arial", Font.BOLD, 30),
                        new Point(x + tailleCase/2 - 15, y + tailleCase/2 + 10)
                    );
                    f.ajouter(t);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Jeu2048();
    }
}