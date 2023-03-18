package document;

import abonnee.Abonne;

public class DVD implements Document {
    private int numDoc;
    private boolean estAdulte;

    private boolean estReserve;
    private boolean estEmprunte;
    private boolean estRetourne;
    private String titre;
    private Abonne abonne;

    public DVD(int numDoc, boolean estEmprunte, boolean estRetourne, String titre, boolean estAdulte, Abonne abonne) {
        this.numDoc = numDoc;
        this.estAdulte = estAdulte;
        this.estEmprunte = estEmprunte;
        this.estRetourne = estRetourne;
        this.estReserve = false;
        this.titre  = titre;
        this.abonne = abonne;
    }

    @Override
    public int numero() {
        return this.numDoc;
    }

    @Override
    public Abonne emprunteur() {
        return this.estEmprunte || this.estReserve ? this.abonne:  null;
    }

    @Override
    public Abonne reserveur() {
        return estReserve ? this.abonne : null;
    }

    @Override
    public void reservationPour(Abonne ab) {
        if (!estReserve && !estEmprunte) {
            this.abonne = ab;
            this.estReserve = true;
            this.estRetourne = false;
        }
    }

    @Override
    public void empruntPar(Abonne ab) {
        if (this.reserveur() == ab | !estEmprunte) {
            this.abonne = ab;
            this.estEmprunte = true;
            this.estRetourne = false;
        }
    }

    @Override
    public void retour() {
        this.estReserve = false;
        this.estEmprunte = false;
        this.estRetourne = true;
    }
}