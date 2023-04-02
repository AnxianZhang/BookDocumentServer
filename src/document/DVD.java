package document;

import abonnee.Abonne;
import database.Document;
import database.RestrictionException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class DVD implements Document {
    private final int numDoc;
    private final boolean estAdulte;

    private boolean estReserve;
    private boolean estEmprunte;
    private boolean estRetourne;
    private final String titre;
    private Abonne abonne;
    private static final Timer timer;
    private ReservationInvalide myTimerTask;

    private int bookHour;
    private int bookMinutes;

    private static final long VALID_TIME;

    static {
        timer = new Timer();
        VALID_TIME = 1000L * 60 * 60 * 2;//2h->ms
    }

    private static class ReservationInvalide extends TimerTask {
        private final Document dvd;
        private final Abonne abonne;

        public ReservationInvalide(Document d, Abonne a) {
            this.dvd = d;
            this.abonne = a;
        }

        @Override
        public void run() {
            this.dvd.retour();
            System.out.println(
                    "Time elapsed, reservation of " + dvd.numero() +
                            " by " + this.abonne.getNom() + " cancelled."
            );
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
        this.myTimerTask = null;
        this.bookHour = 0;
        this.bookMinutes = 0;
    }

    public static void closeTimers() {
        timer.cancel();
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
            if (this.estAdulte && !ab.estAdulte()) {
                throw new RestrictionException("You are not old enough to borrow the DVD: " + this.titre + "##");
            }
            this.abonne = ab;
            this.estReserve = true;
            this.estRetourne = true;

            this.myTimerTask = new ReservationInvalide(this, ab);
            timer.schedule(this.myTimerTask, VALID_TIME);
            initCurrentTimeForBook();
            return;
        }
        throw new RestrictionException(
                "The DVD " + this.titre + " is already "
                        + (this.estReserve ? "booked until " + this.bookHour + ":" + this.bookMinutes : "borrowed")
                        + ", impossible to reserve. ##"
        );
    }

    private void initCurrentTimeForBook(){
        if (this.bookMinutes == 0 && this.bookHour == 0){
            LocalTime currentTime = LocalTime.now();
            this.bookHour = currentTime.getHour() + 2;
            this.bookMinutes = currentTime.getMinute();
        }
    }

    @Override
    public void empruntPar(Abonne ab) throws RestrictionException {
        if (!this.estEmprunte) {
            if (this.estAdulte && !ab.estAdulte()) {
                throw new RestrictionException("You are not old enough to borrow the DVD: " + this.titre + "##");
            }

            if (this.estReserve) {
                if (this.reserveur() == ab) {
                    this.myTimerTask.cancel();
                } else {
                    throw new RestrictionException("The DVD " + this.titre + " is already booked until " + this.bookHour + ":" + this.bookMinutes+", impossible to borrow. ##");
                }
            }

            this.abonne = ab;
            this.estEmprunte = true;
            this.estRetourne = false;
            this.estReserve = false;
            this.bookHour = 0;
            this.bookMinutes = 0;
            return;
        }
        throw new RestrictionException("The DVD " + this.titre + " is already borrowed, impossible to borrow. ##");
    }

    @Override
    public void retour() {
        this.estReserve = false;
        this.estEmprunte = false;
        this.estRetourne = true;
        this.bookHour = 0;
        this.bookMinutes = 0;
        this.abonne = null;
    }
}