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
        this.titre = titre;
        this.abonne = abonne;
    }

    @Override
    public int numero() {
        return this.numDoc;
    }

    @Override
    public Abonne emprunteur() {
        return this.estEmprunte || this.estReserve ? this.abonne : null;
    }

    @Override
    public Abonne reserveur() {
        return estReserve ? this.abonne : null;
    }

    @Override
    public void reservationPour(Abonne ab) throws RestrictionException {
        if (!estReserve && !estEmprunte) {
            this.abonne = ab;
            this.estReserve = true;
            this.estRetourne = false;
            return;
        }
        throw new RestrictionException("Le DVD "+ this.titre +" est deja " + (estReserve ? "reserver" : "emprunter"));
    }

    @Override
    public void empruntPar(Abonne ab) throws RestrictionException {
        System.out.println(estEmprunte);
        if (this.reserveur() == ab || !estEmprunte) {
            if (this.estAdulte && !ab.estAdulte()) {
                throw new RestrictionException("Vous n'avaz pas l'age requis pour "+ this.titre);
            }
            this.abonne = ab;
            this.estEmprunte = true;
            this.estRetourne = false;
            this.estReserve = false;
            return;
        }
        throw new RestrictionException("Le DVD "+ this.titre +" est deja " + (estReserve ? "reserver" : "emprunter"));
    }

    @Override
    public void retour() {
        this.estReserve = false;
        this.estEmprunte = false;
        this.estRetourne = true;
        this.abonne = null;
    }
}