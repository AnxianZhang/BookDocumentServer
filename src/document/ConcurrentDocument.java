package document;

import abonnee.Abonne;

public class ConcurrentDocument implements Document {
    private final Document d;

    public ConcurrentDocument(Document d) {
        this.d = d;
    }

    @Override
    public int numero() {
        return this.d.numero();
    }

    @Override
    public Abonne emprunteur() {
        synchronized (this.d) {
            return this.d.emprunteur();
        }
    }

    @Override
    public Abonne reserveur() {
        synchronized (this.d) {
            return this.d.reserveur();
        }
    }

    @Override
    public void reservationPour(Abonne ab) throws RestrictionException {
        synchronized (this.d) {
            this.d.reservationPour(ab);
        }
    }

    @Override
    public void empruntPar(Abonne ab) throws RestrictionException {
        synchronized (this.d) {
            this.d.empruntPar(ab);
        }
    }

    @Override
    public void retour() {
        synchronized (this.d) {
            this.d.retour();
        }
    }
}