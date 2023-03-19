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

public class ReservationService extends Service {
    private static Data data;

    public ReservationService(Socket socketServer) {
        super(socketServer);
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
            out.println("ok");

            while (currentAbonne == null) {
                out.println("Please enter your customer number: ");
                int numAbonee = Integer.parseInt(in.readLine());
                currentAbonne = data.getAbonee(numAbonee);
            }
            out.println("ok");

            System.out.println(
                    "Request of " + client.getInetAddress()
                            + "for DVD (num: " + chosenDocument.numero() + ") booked by "
                            + currentAbonne.getNom() + " (" + currentAbonne.getNumAbonee() + ")"
            );

            chosenDocument.reservationPour(currentAbonne);
            reponse = "Reservation of the DVD confirmed, you have 2 hours to come and pick it up. \n" +
                    "Otherwise, we will be forced to cancel it.";
            out.println(reponse.replace("\n", "##"));
            System.out.println("Booking DVD (num: " + chosenDocument.numero() + ") confirmed");

        } catch (IOException e) {
            System.out.println("pb service");
        } catch (RestrictionException e) {
            out.println(e);
        } finally {
            try {
                client.close();
                System.out.println("========== Client disconnection " + super.getSocketClient().getInetAddress() + " deconnectee ==========");
                System.out.println();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}