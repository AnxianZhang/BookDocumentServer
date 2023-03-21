package services;

import dataBase.Data;
import document.*;
import abonnee.*;

import server.Service;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class EmpruntService extends ServiveMediateque {
//    private static Data data;

    public EmpruntService(Socket socketServer) throws IOException {
        super(socketServer);
    }

//    public static void setData(Data d) {
//        data = d;
//    } // ok

    @Override
    protected String welcomeMsg() {
        return "++++++++++ Welcome to the borrowing service ++++++++++";
    }

    @Override
    protected void theSpecificService() {
        try {
            super.emprunterDocument();

            System.out.println("Borrowing DVD (num: " + super.getNumDocument() + ") confirmed");
            super.println("Borrowing DVD (num: " + super.getNumDocument() + ") confirms.##" +
                    "You can leave by entering 'quit'.##");
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
        return "borrow";
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
//            super.println("++++++++++ Welcome to the borrowing service ++++++++++");
//            String customerResponse = "";

        try {
//            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            out = new PrintWriter(client.getOutputStream(), true);

//            while (currentAbonne == null) {
//                super.println("Please enter your customer number (a valid one): ||");
//
//                customerResponse =  super.readLine();
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
//                        super.println("Please enter a (valid) number of DVD that you wish to borrow: ");
//
//                        customerResponse =  super.readLine();
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
//                                        + " for DVD (num: " + chosenDocument.numero() + ") borrowed by "
//                                        + currentAbonne.getNom() + " (" + currentAbonne.getNumAbonee() + ")"
//                        );
//
//                        try {
//                            chosenDocument.empruntPar(currentAbonne);
//                            reponse = "Borrowing DVD (num: " + chosenDocument.numero() + ") confirms.##" +
//                                    "You can leave by entering 'quit'.##";
//                            System.out.println("Borrowing DVD (num: " + chosenDocument.numero() + ") confirmed");
//                            super.println(reponse);
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
        } catch (IOException e) {
            System.out.println("pb service");
        } finally {
            super.closeSocketClient();
        }
    }
}