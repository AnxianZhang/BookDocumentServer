package abonnee;

import document.Document;

import java.util.List;

public class Abonne {
    private int numAbonee;
    private int anneeNaissance;
    private String nom;

    public Abonne(int numAbonee, int anneeNaissance, String nom){
        this.numAbonee = numAbonee;
        this.anneeNaissance = anneeNaissance;
        this.nom = nom;
    }

    public int getNumAbonee() {
        return numAbonee;
    }

    public int getDateNaissance() {
        return this.anneeNaissance;
    }

    public String getNom() {
        return nom;
    }
}
