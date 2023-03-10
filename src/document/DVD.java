package document;

import abonnee.Abonne;

public class DVD implements Document {
    private int numDVD;
    private boolean estAdulte;

//    private int numDoc;
    private boolean estReserve;
    private boolean estEmprunte;
    private boolean estRetourne;
    private String titre;
    private int numAbonee;
    private Abonne abonne;

    public DVD() {
        this.estReserve = false;
        this.estEmprunte = false;
        this.estRetourne = true;
    }

    @Override
    public int numero() {
        return this.numDVD;//j'ai pas sur c quel num
    }

    @Override
    public Abonne emprunteur() {
        if (estEmprunte)
            return this.abonne;
        return null;
    }

    @Override
    public Abonne reserveur() {
        if (estReserve)
            return this.abonne;
        return null;
    }

    @Override
    public void reservationPour(Abonne ab) {
        if (!estReserve & !estEmprunte) {
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