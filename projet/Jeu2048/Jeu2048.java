import MG2D.*;
import MG2D.geometrie.*;
import java.awt.Font;
import java.util.Random;

public class Jeu2048 {

    private int[][] grille = new int[4][4];
    private Random random = new Random();
    private int score = 0;

    private int choixMenu = 0; 

    public Jeu2048() {

        FenetrePleinEcran fenetre = new FenetrePleinEcran("2048");

        ClavierBorneArcade clavier = new ClavierBorneArcade();
        fenetre.addKeyListener(clavier);

        restart();

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

            if(estVictoire()) {
                afficherMenu(fenetre, clavier, true);
                restart();
            }

            if(estPartieFinie()) {
                afficherMenu(fenetre, clavier, false);
                restart();
            }

            fenetre.effacer();
            dessinerGrille(fenetre);
            fenetre.rafraichir();

            try { Thread.sleep(80); } catch(Exception e) {}
        }
    }

    private void afficherMenu(FenetrePleinEcran fenetre, ClavierBorneArcade clavier, boolean victoire) {

        choixMenu = 0;

        while(true) {

            fenetre.effacer();

            int cx = fenetre.getWidth() / 2;
            int cy = fenetre.getHeight() / 2;

            Texte titre = new Texte(
                victoire ? new Couleur(0, 150, 0) : Couleur.ROUGE,
                victoire ? "VICTOIRE !" : "GAME OVER",
                new Font("Arial", Font.BOLD, 80),
                new Point(cx, cy + 200) 
            );

            Texte scoreTxt = new Texte(
                Couleur.NOIR, 
                "Score : " + score,
                new Font("Arial", Font.BOLD, 50),
                new Point(cx, cy + 100) 
            );

            int rectWidth = 300;
            int rectHeight = 80;
            int bx = cx - rectWidth / 2;
            int by1 = cy - 30;  
            int by2 = cy - 130; 

            Rectangle b1 = new Rectangle(
                choixMenu == 0 ? new Couleur(238,228,218) : new Couleur(200,200,200),
                new Point(bx, by1),
                rectWidth, rectHeight,
                true
            );
            Texte t1 = new Texte(
                Couleur.NOIR,
                "RESTART",
                new Font("Arial", Font.BOLD, 30),
                new Point(cx, by1 + 25)
            );

            Rectangle b2 = new Rectangle(
                choixMenu == 1 ? new Couleur(238,228,218) : new Couleur(200,200,200),
                new Point(bx, by2),
                rectWidth, rectHeight,
                true
            );
            Texte t2 = new Texte(
                Couleur.NOIR,
                "QUITTER",
                new Font("Arial", Font.BOLD, 30),
                new Point(cx, by2 + 25)
            );

            fenetre.ajouter(titre);
            fenetre.ajouter(scoreTxt);
            fenetre.ajouter(b1);
            fenetre.ajouter(t1);
            fenetre.ajouter(b2);
            fenetre.ajouter(t2);

            fenetre.rafraichir();

            if(clavier.getJoyJ1HautTape() || clavier.getJoyJ1BasTape()) {
                choixMenu = 1 - choixMenu; 
            }

            if(clavier.getBoutonJ1ZTape() || clavier.getBoutonJ1ATape()) {
                if(choixMenu == 0) {
                    return; 
                } else {
                    System.exit(0);
                }
            }

            try { Thread.sleep(120); } catch(Exception e) {}
        }
    }

    private void restart() {
        grille = new int[4][4];
        score = 0;
        ajouterCase();
        ajouterCase();
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
                    newRow[index++] = grille[i][j];;
                }
            }

            for(int j = 0; j < 3; j++) {
                if(newRow[j] == newRow[j+1] && newRow[j] != 0) {
                    newRow[j] *= 2;
                    score += newRow[j];
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

    private boolean estVictoire() {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(grille[i][j] == 2048) return true;
            }
        }
        return false;
    }

    private boolean estPartieFinie() {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(grille[i][j] == 0) return false;
            }
        }

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(i < 3 && grille[i][j] == grille[i+1][j]) return false;
                if(j < 3 && grille[i][j] == grille[i][j+1]) return false;
            }
        }

        return true;
    }

    public Couleur chooseCouleur(int nombre) {
        switch (nombre) {
            case 2: return new Couleur(238,228,218);
            case 4: return new Couleur(237,224,200);
            case 8: return new Couleur(242,177,121);
            case 16: return new Couleur(245,149,99);
            case 32: return new Couleur(246,124,95);
            case 64: return new Couleur(246,94,59);
            case 128: return new Couleur(237,207,114);
            case 256: return new Couleur(237,204,97);
            case 512: return new Couleur(237,200,80);
            case 1024: return new Couleur(237,197,63);
            case 2048: return new Couleur(237,194,46);
            default: return Couleur.BLANC;
        }
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

        Texte scoreText = new Texte(
            Couleur.NOIR,
            "Score : " + score,
            new Font("Arial", Font.BOLD, 40),
            new Point(1800, 1180)
        );
        f.ajouter(scoreText);
    }

    public static void main(String[] args) {
        new Jeu2048();
    }
}
