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
import java.util.Objects;

public class ReservationService extends ServiveMediateque {
//    private static Data data;

    public ReservationService(Socket socketServer) throws IOException {
        super(socketServer);
    }

//    public static void setData(Data d) {
//        setData(d);
//    }// ok

    @Override
    protected String welcomeMsg() {
        return "++++++++++ Welcome to the booking service ++++++++++";
    }

    @Override
    protected void theSpecificService() {
        try {
            super.reserverDocument();
            super.println("Reservation of the DVD confirmed, you have 2 hours to come and pick it up. ##" +
                    "Otherwise, we will be forced to cancel it.##You can leave by entering 'quit'.##");
            System.out.println("Booking DVD (num: " + super.getNumDocument() + ") confirmed");
        } catch (RestrictionException e) {
            System.out.println(e.toString()
                    .replace("##", "\n")
                    .replace("You", super.getCurrentAbonneName())
            );
            super.println(e.toString());
        }
    }

    @Override
    protected String serviceName() {
        return "book";
    }

    @Override
    public void run() {
//        System.out.println("========== Client connection " + super.getSocketClient().getInetAddress() + " ==========");

//        String reponse = "";
//        Document chosenDocument = null;
//        Abonne currentAbonne = null;

//        Socket client = super.getSocketClient();
//        BufferedReader in = null;
//        PrintWriter out = null;
//            super.println("++++++++++ Welcome to the booking service ++++++++++");
//            String customerResponse = "";

        try {
//            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            out = new PrintWriter(client.getOutputStream(), true);


//            while (currentAbonne == null) {
//                super.println("Please enter your customer number (a valid one): ||");
//
//                customerResponse = super.readLine();
//                if (Objects.equals(customerResponse, "quit")) {
//                    break;
//                }
//                int numAbonee = Integer.parseInt(customerResponse);
//                currentAbonne = data.getAbonee(numAbonee);
//            }
            super.searchAbonne();
            super.requestDocument();
//            while (true) {
//                if (!customerResponse.equals("quit")) {
//                    while (chosenDocument == null) {
//                        super.println("Please enter a (valid) number of DVD that you wish to book: ");
//
//                        customerResponse = super.readLine();
//                        if (Objects.equals(customerResponse, "quit")) {
////                    System.out.println(customerResponse);
//                            break;
//                        }
//                        int numDVD = Integer.parseInt(customerResponse);
//                        chosenDocument = data.getDocument(numDVD);
//                    }
//                    if (!customerResponse.equals("quit")) {
//                        super.println("ok");
//                        System.out.println(
//                                "Request of " + client.getInetAddress()
//                                        + " for DVD (num: " + chosenDocument.numero() + ") booked by "
//                                        + currentAbonne.getNom() + " (" + currentAbonne.getNumAbonee() + ")"
//                        );
//
//                        try {
//                            chosenDocument.reservationPour(currentAbonne);
//                            reponse = "Reservation of the DVD confirmed, you have 2 hours to come and pick it up. ##" +
//                                    "Otherwise, we will be forced to cancel it.##You can leave by entering 'quit'.##" ;
//                            super.println(reponse);
//                            System.out.println("Booking DVD (num: " + chosenDocument.numero() + ") confirmed");
//                        } catch (RestrictionException e) {
//                            System.out.println(e.toString()
//                                    .replace("##", "\n")
//                                    .replace("You", currentAbonne.getNom())
//                            );
//                            super.println(e.toString());
//                        }
//
//                        // init for next request
//                        customerResponse = "";
//                        chosenDocument = null;
////                        currentAbonne = null;
//                    }
//                } else {
//                    break;
//                }
//            }
//            out.println("++++++++++ Welcome to the booking service ++++++++++");
//
//            while (chosenDocument == null) {
//                out.println("Please enter a (valid) number of DVD that you wish to borrow: ");
//                int numDVD = Integer.parseInt(in.readLine());
//                chosenDocument = data.getDocument(numDVD);
//            }
//            out.println("ok");
//
//            while (currentAbonne == null) {
//                out.println("Please enter your customer number: ");
//                int numAbonee = Integer.parseInt(in.readLine());
//                currentAbonne = data.getAbonee(numAbonee);
//            }
//            out.println("ok");
//
//            System.out.println(
//                    "Request of " + client.getInetAddress()
//                            + "for DVD (num: " + chosenDocument.numero() + ") booked by "
//                            + currentAbonne.getNom() + " (" + currentAbonne.getNumAbonee() + ")"
//            );
//
//            chosenDocument.reservationPour(currentAbonne);
//            reponse = "Reservation of the DVD confirmed, you have 2 hours to come and pick it up. \n" +
//                    "Otherwise, we will be forced to cancel it.";
//            out.println(reponse.replace("\n", "##"));
//            System.out.println("Booking DVD (num: " + chosenDocument.numero() + ") confirmed");

        } catch (IOException e) {
            System.out.println("pb service");
        } finally {
            super.closeSocketClient();
        }
    }
}