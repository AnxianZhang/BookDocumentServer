package services;

import dataBase.Data;
import document.*;
import abonnee.*;

import server.Service;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class EmpruntService extends Service {
    private static Data data;

    public EmpruntService(Socket socketServer) {
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

            out.println("++++++++++ Welcome to the borrowing service ++++++++++");
            String customerResponse = "";
            while (currentAbonne == null) {
                out.println("Please enter your customer number (a valid one): ||");

                customerResponse = in.readLine();
                if (Objects.equals(customerResponse, "quit")) {
                    break;
                }
                int numAbonee = Integer.parseInt(customerResponse);
                currentAbonne = data.getAbonee(numAbonee);
            }
            while (true) {
                if (!customerResponse.equals("quit")) {
                    while (chosenDocument == null) {
                        out.println("Please enter a (valid) number of DVD that you wish to borrow: ");

                        customerResponse = in.readLine();
                        if (Objects.equals(customerResponse, "quit")) {
//                    System.out.println(customerResponse);
                            break;
                        }
                        int numDVD = Integer.parseInt(customerResponse);
                        chosenDocument = data.getDocument(numDVD);
                    }
                    if (!customerResponse.equals("quit")) {
                        out.println("ok");
                        System.out.println(
                                "Request of " + client.getInetAddress()
                                        + "for DVD (num: " + chosenDocument.numero() + ") borrowed by "
                                        + currentAbonne.getNom() + " (" + currentAbonne.getNumAbonee() + ")"
                        );

                        try {
                            chosenDocument.empruntPar(currentAbonne);
                            reponse = "Borrowing DVD (num: " + chosenDocument.numero() + ") confirms.##" +
                                    "You can leave by entering 'quit'.##";
                            System.out.println("Borrowing DVD (num: " + chosenDocument.numero() + ") confirmed");
                            out.println(reponse);
                        } catch (RestrictionException e) {
                            System.out.println(e + "##");
                            out.println(e);
                        }

                        // init for next request
                        customerResponse = "";
                        chosenDocument = null;
//                        currentAbonne = null;
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("pb service");
        } finally {
            try {
                client.close();
                System.out.println("========== Client disconnection " + super.getSocketClient().getInetAddress() + " deconnectee ==========");
                System.out.println();
            } catch (IOException e) {
                System.out.println("Lors de la ferme de la socket client EMPRUNT");
            }
        }
    }
}