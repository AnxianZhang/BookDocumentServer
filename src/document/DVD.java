package document;

import abonnee.Abonne;

import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

public class DVD implements Document {
    private int numDoc;
    private boolean estAdulte;

    private boolean estReserve;
    private boolean estEmprunte;
    private boolean estRetourne;
    private String titre;
    private Abonne abonne;
    private Timer timer;

//    private static final long VALID_TIME = 1000L * 60 * 60 * 2;//2h->ms
    private static final long VALID_TIME = 15000L;//2h->ms



    private class ReservationInvalide extends TimerTask {
        private Document dvd;

        public ReservationInvalide(Document d) {
            this.dvd = d;
        }

        @Override
        public void run() {
            this.dvd.retour();
            System.out.println("Time elapsed, reservation of "  + dvd.numero() +  " cancelled.");
        }
    }

    public DVD(int numDoc, boolean estEmprunte, boolean estRetourne, String titre, boolean estAdulte, Abonne abonne) {
        this.numDoc = numDoc;
        this.estAdulte = estAdulte;
        this.estEmprunte = estEmprunte;
        this.estRetourne = estRetourne;
        this.estReserve = false;
        this.titre = titre;
        this.abonne = abonne;

        this.timer = new Timer();
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
        return this.estReserve ? this.abonne : null;
    }

    @Override
    public void reservationPour(Abonne ab) throws RestrictionException {
        if (!this.estReserve && !this.estEmprunte) {
            this.abonne = ab;
            this.estReserve = true;
            this.estRetourne = true;

            this.timer.schedule(new ReservationInvalide(this), VALID_TIME);
            return;
        }
        throw new RestrictionException(
                "Le DVD " + this.titre + " est deja "
                        + (this.estReserve ? "reserver" : "emprunter")
                        + " impossible de reserver"
        );
    }

    @Override
    public void empruntPar(Abonne ab) throws RestrictionException {
        System.out.println(this.estEmprunte);
        if (!this.estEmprunte) {
            if (this.estAdulte && !ab.estAdulte()) {
                throw new RestrictionException("Vous n'avaz pas l'age requis pour "+ this.titre);
            }

            if (this.estReserve) {
                if (this.reserveur() == ab) {
                    this.timer.cancel();
                } else {
                    throw new RestrictionException("Le DVD " + this.titre + " est deja reserver impossible d'emprunter");
                }
            }

            this.abonne = ab;
            this.estEmprunte = true;
            this.estRetourne = false;
            this.estReserve = false;
            return;
        }
        throw new RestrictionException("Le DVD "+ this.titre +" est deja emprunter impossible d'emprunter");
    }

    @Override
    public void retour() {
        this.estReserve = false;
        this.estEmprunte = false;
        this.estRetourne = true;
        this.abonne = null;
    }
}