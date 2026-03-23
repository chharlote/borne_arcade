import MG2D.*;
import MG2D.geometrie.*;
import java.awt.Font;

public class Menu {

    private FenetrePleinEcran fenetre;
    private ClavierBorneArcade clavier;
    private Bouton[] boutons;
    private int selection; 

    public Menu() {
        fenetre = new FenetrePleinEcran("Menu 2048");
        clavier = new ClavierBorneArcade();
        fenetre.addKeyListener(clavier);

        // Créer  boutons
        boutons = new Bouton[2];

        boutons[0] = new Bouton(
            new Texte(Couleur.NOIR, "Lancer le jeu", new Font("Arial", Font.BOLD, 40), new Point(400, 300)),
            new Texture("assets/img/bouton2.png", new Point(300, 280), 400, 65),
            "Lancer le jeu"
        );

        boutons[1] = new Bouton(
            new Texte(Couleur.NOIR, "Quitter", new Font("Arial", Font.BOLD, 40), new Point(400, 180)),
            new Texture("assets/img/bouton2.png", new Point(300, 160), 400, 65),
            "Quitter"
        );

        selection = 0;

        boucleMenu();
    }

    private void boucleMenu() {
        while(true) {
            if(clavier.getJoyJ1HautTape()) {
                selection--;
                if(selection < 0) selection = boutons.length - 1;
            }
            if(clavier.getJoyJ1BasTape()) {
                selection++;
                if(selection >= boutons.length) selection = 0;
            }

            if(clavier.getBoutonJ1ATape()) {
                if(selection == 0) { 
                    fenetre.fermer();
                    new Jeu2048(); 
                    return;
                } else if(selection == 1) { 
                    System.exit(0);
                }
            }

            fenetre.effacer();

            for(int i = 0; i < boutons.length; i++) {
                // Changer la couleur du texte selon la sélection
                if(i == selection) {
                    boutons[i].getTexte().setCouleur(Couleur.ROUGE);
                } else {
                    boutons[i].getTexte().setCouleur(Couleur.NOIR);
                }
                fenetre.ajouter(boutons[i].getTexture());
                fenetre.ajouter(boutons[i].getTexte());
            }

            fenetre.rafraichir();

            try {
                Thread.sleep(100);
            } catch(Exception e) {}
        }
    }

    public static void main(String[] args) {
        new Menu();
    }
}