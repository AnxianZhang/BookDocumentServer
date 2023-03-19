package abonnee;

import document.Document;

import java.time.Year;
import java.util.List;

public class Abonne {
    private int numAbonee;
    private int anneeNaissance;
    private String nom;

    public Abonne(int numAbonee, int anneeNaissance, String nom) {
        this.numAbonee = numAbonee;
        this.anneeNaissance = anneeNaissance;
        this.nom = nom;
    }

    public int getNumAbonee() {
        return numAbonee;
    }

    public boolean estAdulte() {
        return Year.now().getValue() - this.anneeNaissance >= 18;
    }

    public String getNom() {
        return nom;
    }
}