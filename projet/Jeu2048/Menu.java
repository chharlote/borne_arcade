import MG2D.*;
import MG2D.geometrie.*;
import java.awt.Font;

public class Menu {

    private FenetrePleinEcran fenetre;
    private ClavierBorneArcade clavier;
    private int choixMenu; 

    public Menu() {
        fenetre = new FenetrePleinEcran("2048 - Menu");
        clavier = new ClavierBorneArcade();
        fenetre.addKeyListener(clavier);

        choixMenu = 0; 
        boucleMenu();
    }

    private void boucleMenu() {
        while(true) {
            int largeurFen = fenetre.getWidth();
            int hauteurFen = fenetre.getHeight();

            int rectWidth = 400;
            int rectHeight = 80;
            int cx = largeurFen / 2;
            int cy = hauteurFen / 2;
            
            int bx = cx - rectWidth / 2;
            
            int titreY = cy + 200; 
            int by1 = cy + 30;     
            int by2 = cy - 90;     

            fenetre.effacer();

            Texte titre = new Texte(
                Couleur.NOIR,
                "2048",
                new Font("Arial", Font.BOLD, 100),
                new Point(cx, titreY) 
            );

            Rectangle b1 = new Rectangle(choixMenu == 0 ? new Couleur(238,228,218) : new Couleur(200,200,200), new Point(bx, by1), rectWidth, rectHeight, true);
            Texte t1 = new Texte(
                Couleur.NOIR,
                "LANCER LE JEU",
                new Font("Arial", Font.BOLD, 40),
                new Point(cx, by1 + 25) 
            );
            Rectangle b2 = new Rectangle(choixMenu == 1 ? new Couleur(238,228,218) : new Couleur(200,200,200), new Point(bx, by2), rectWidth, rectHeight, true);
            Texte t2 = new Texte(
                Couleur.NOIR,
                "QUITTER",
                new Font("Arial", Font.BOLD, 40),
                new Point(cx , by2 + 25) 
            );
            fenetre.ajouter(titre);
            fenetre.ajouter(b1);
            fenetre.ajouter(t1); 
            fenetre.ajouter(b2);
            fenetre.ajouter(t2);

            fenetre.rafraichir();

            if(clavier.getJoyJ1HautTape() || clavier.getJoyJ1BasTape()) {
                choixMenu = 1 - choixMenu; 
            }

            if(clavier.getBoutonJ1ATape()) {
                if(choixMenu == 0) { 
                    fenetre.fermer();
                    new Jeu2048();
                    System.out.println("Lancement du jeu...");
                    return;
                } else { 
                    System.exit(0);
                }
            }

            try { Thread.sleep(100); } catch(Exception e) {}
        }
    }

    public static void main(String[] args) {
        new Menu();
    }
}