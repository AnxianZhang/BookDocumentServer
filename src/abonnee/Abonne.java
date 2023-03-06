package abonnee;

import document.Document;

import java.util.List;

public class Abonne {
    private int numAbonee;
    private String dateNaissance; //car format aaaa-mm-jj
    private String nom;
    private List<Document> listReserve;
    private List<Document> listEmprunte;

    public int getNumAbonee() {
        return numAbonee;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public String getNom() {
        return nom;
    }
}
