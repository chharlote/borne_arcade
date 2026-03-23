import MG2D.geometrie.Point;
import MG2D.geometrie.Texture;
import MG2D.geometrie.Texte;
import java.awt.Font;

public class Bouton {

    private Texte texte;
    private Texture texture;
    private String nom;

    public Bouton(Texte texte, Texture texture, String nom) {
        this.texte = texte;
        this.texture = texture;
        this.nom = nom;
    }

    public Texte getTexte() {
        return texte;
    }

    public Texture getTexture() {
        return texture;
    }

    public String getNom() {
        return nom;
    }

    public boolean estClique(int sourisX, int sourisY) {
		Point pos = texture.getA();
        int largeur = texture.getLargeur();
        int hauteur = texture.getHauteur();
        return (sourisX >= pos.getX() && sourisX <= pos.getX() + largeur &&
                sourisY >= pos.getY() && sourisY <= pos.getY() + hauteur);
    }
}