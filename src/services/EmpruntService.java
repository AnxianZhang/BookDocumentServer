package services;

import dataBase.Data;
import document.*;
import abonnee.*;

import server.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
        System.out.println("========== Connexion du client " + super.getSocketClient().getInetAddress() + " ==========");

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

            if (chosenDocument.reserveur() == null || chosenDocument.reserveur() == currentAbonne) {
                chosenDocument.empruntPar(currentAbonne);
                reponse = "Emprunt du DVD confirme";
                System.out.println(reponse);
                out.println(reponse);
            }
            /*
            }

            if (doc != null) {
                if (doc.reserveur() == null || doc.reserveur() == abo) {
                    if (doc.emprunteur() == null) {
                        //pas defini « vous n’avez pas l’âge pour emprunter ce DVD »
                        synchronized (doc) {
                            doc.empruntPar(abo);
                            reponse = "l'emprunte réussie";
                        }
                    } else {
                        reponse = "ce DVD est déjà emprunté"; // traiter par l'exeption
                    }
                } else {
                    reponse = "ce DVD est déjà reservé"; // traiter par l'exeption
                }
            } else
                reponse = "Aucun document ne porte ce numéro";
             */

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