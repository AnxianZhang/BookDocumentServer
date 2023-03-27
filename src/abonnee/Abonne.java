package abonnee;

import java.time.Year;

public class Abonne {
    private final int numAbonee;
    private final int anneeNaissance;
    private final String nom;

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