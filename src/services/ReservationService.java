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
        System.out.println("========== Client connection " + super.getSocketClient().getInetAddress() + " ==========");

        String reponse = "";
        Document chosenDocument = null;
        Abonne currentAbonne = null;

        Socket client = super.getSocketClient();
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);

            out.println("++++++++++ Welcome to the booking service ++++++++++");

            while (chosenDocument == null) {
                out.println("Please enter a (valid) number of DVD that you wish to borrow: ");
                int numDVD = Integer.parseInt(in.readLine());
                chosenDocument = data.getDocument(numDVD);
            }

            while (currentAbonne == null) {
                out.println("Please enter your customer number: ");
                int numAbonee = Integer.parseInt(in.readLine());
                currentAbonne = data.getAbonee(numAbonee);
            }

            System.out.println(
                    "Request of " + client.getInetAddress()
                            + "for DVD (num: " + chosenDocument.numero() + ") booked by "
                            + currentAbonne.getNom() + " ("+ currentAbonne.getNumAbonee() + ")"
            );

            chosenDocument.reservationPour(currentAbonne);
            reponse = "Reservation of the DVD confirmed, you have 2 hours to come and pick it up.\n" +
                    "Otherwise, we will be forced to cancel it.";
            System.out.println("Booking DVD (num: "+ chosenDocument.numero() + ") confirmed");
            out.println(reponse.replace("\n", "##"));

        } catch (IOException e) {
            System.out.println("pb service");
        } catch (RestrictionException e) {
            out.println(e);
        } finally {
            try {
                client.close();
                System.out.println("========== Client disconnection " + super.getSocketClient().getInetAddress() + " deconnectee ==========");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}