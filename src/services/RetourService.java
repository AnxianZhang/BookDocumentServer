package services;

import abonnee.Abonne;
import dataBase.Data;
import document.Document;
import server.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RetourService extends Service {
    //    private Documents docs;
    private static Data data;

    public RetourService(Socket socketServer) {
        super(socketServer);
//        this.docs = d;
    }

    public static void setData(Data d) {
        data = d;
    }

    @Override
    public void run() {
//        String reponse = null;
//        Socket client = this.getSocketClient();
        System.out.println("========== Connexion du client " + super.getSocketClient().getInetAddress() + " ==========");

        String reponse = "";
        Document chosenDocument = null;
        Abonne currentAbonne = null;

        Socket client = super.getSocketClient();
        BufferedReader in = null;
        PrintWriter out = null;

        try {
//            System.out.println("client connecté: " + getSocketClient().getInetAddress());
//            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);

            out.println("++++++++++ Welcome to the return service ++++++++++");


//            out.println("Tapez le numero DVD que vous voulez rendre:");
//            int numDVD = Integer.parseInt(in.readLine());

            while (chosenDocument == null) {
                System.out.println("doc");
                out.println("Please enter a (valid) number of DVD that you wish to borrow: ");
                int numDVD = Integer.parseInt(in.readLine());
                chosenDocument = data.getDocument(numDVD);
            }
            System.out.println("ok le num doc est ok");
            out.println("ok");
            out.println("ok");

            System.out.println("Requète de " + client.getInetAddress()
                    + "numero DVD" + chosenDocument.numero());

            chosenDocument.retour();
            reponse = "Return of the DVD " + chosenDocument.numero() + " successful";
            System.out.println(reponse);
            out.println(reponse);

//            Document doc = docs.getDocuments(numDVD);

//            if (doc != null) {
//                synchronized (doc) {
//                    doc.retour();
//                    reponse = "retour réussie";
//                }
//            } else
//                reponse = "Aucun document ne porte ce numéro";


        } catch (IOException e) {
            System.out.println("pb service");
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
