package services;

import abonnee.Abonne;
import dataBase.Data;
import document.*;

import server.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ReservationService extends Service {
    private static Data data;

//    private Timer timer;
//    private static final long VALID_TIME = 1000L * 60 * 60 * 2;//2h->ms

//    private class ReservationInvalide extends TimerTask {
//        private Document dvd;
//        private PrintWriter out;
//
//        public ReservationInvalide(Document d, PrintWriter out) {
//            this.dvd = d;
//            this.out = out;
//        }
//
//        @Override
//        public void run() {
//            this.dvd.retour();
//            out.println("Time elapsed, reservation of "  + dvd.numero() +  " cancelled.");
//        }
//    }

    public ReservationService(Socket socketServer) {
        super(socketServer);
//        this.timer = new Timer();
    }

    public static void setData(Data d) {
        data = d;
    }

    @Override
    public void run() {
        System.out.println("========== Connexion du client " + super.getSocketClient().getInetAddress() + " ==========");

        String reponse = "";
        Document chosenDocument = null;
        Abonne currentAbonne = null;

        Socket client = super.getSocketClient();
        BufferedReader in = null;
        PrintWriter out = null;

//        String reponse = null;
//        Socket client = this.getSocketClient();
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);

            out.println("++++++++++ Welcome to the booking service ++++++++++");

            while (chosenDocument == null) {
                System.out.println("doc");
                out.println("Please enter a (valid) number of DVD that you wish to borrow: ");
                int numDVD = Integer.parseInt(in.readLine());
                chosenDocument = data.getDocument(numDVD);
            }
            System.out.println("ok le num doc est ok");
            out.println("ok");


            while (currentAbonne == null) {
                System.out.println("cli");
                out.println("Please enter your customer number: ");
                int numAbonee = Integer.parseInt(in.readLine());
                currentAbonne = data.getAbonee(numAbonee);
            }
            System.out.println("ok client ok");
            out.println("ok");

            System.out.println(
                    "Requete de " + client.getInetAddress()
                            + "numero DVD" + chosenDocument.numero() + " pour numero Abonee "
                            + currentAbonne.getNumAbonee()
            );
//            System.out.println("client connecté: " + getSocketClient().getInetAddress());
//            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
//
//            out.println("Tapez le numero DVD que vous voulez reserver:");
//            int numDVD = Integer.parseInt(in.readLine());
//            out.println("Tapez votre numero Abonee:");
//            int numAbonee = Integer.parseInt(in.readLine());
//
//            System.out.println("Requète de " + client.getInetAddress()
//                    + "numero DVD" + numDVD + " pour numero Abonee "
//                    + numAbonee);
//
//            Document doc = docs.getDocuments(numDVD);
//            Abonne abo = docs.getAbonne(numAbonee);
            chosenDocument.reservationPour(currentAbonne);
            reponse = "Reservation of the DVD confirmed, you have 2 hours to come and pick it up.\n" +
                    "Otherwise, we will be forced to cancel it.";
//            System.out.println(reponse);
            out.println(reponse.replace("\n", "##"));

//            if (doc != null) {
//                if (doc.reserveur() == null) {
//                    if (doc.emprunteur() == null) {
//                        synchronized (doc) {
//                            doc.reservationPour(abo);
//                            reponse = "Reservation réussie,veuillez emprunter à la médiathèque dans 2 heures, sinon la réservation sera invalide";
//                            this.timer.schedule(new ReservationInvalide(doc, out), VALID_TIME);
//                        }
//                    } else {
//                        reponse = "ce DVD est déjà emprunté";
//                    }
//                } else {
//                    reponse = "ce DVD est reservé";//pas defini jusqu’à 12h25 »
//                }
//            } else
//                reponse = "Aucun document ne porte ce numéro";

        } catch (IOException e) {
            System.out.println("pb service");
        } catch (RestrictionException e) {
            System.out.println(e);
            out.println(e);
        } finally {
            try {
                client.close();
                System.out.println("========== Client " + super.getSocketClient().getInetAddress() + " deconnectee ==========");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}