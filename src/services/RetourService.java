//package services;
//
//import abonnee.Abonne;
//import dataBase.Documents;
//import document.Document;
//import server.Service;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//
//public class RetourService extends Service {
//    private Documents docs;
//
//    public RetourService(Socket socketServer, Documents d) {
//        super(socketServer);
//        this.docs = d;
//    }
//
//    @Override
//    public void run() {
//        String reponse = null;
//        Socket client = this.getSocketClient();
//        try {
//            System.out.println("client connecté: " + getSocketClient().getInetAddress());
//            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
//
//            out.println("Tapez le numero DVD que vous voulez rendre:");
//            int numDVD = Integer.parseInt(in.readLine());
//
//            System.out.println("Requète de " + client.getInetAddress()
//                    + "numero DVD" + numDVD);
//
//            Document doc = docs.getDocuments(numDVD);
//
//            if (doc != null) {
//                synchronized (doc) {
//                    doc.retour();
//                    reponse = "retour réussie";
//                }
//            } else
//                reponse = "Aucun document ne porte ce numéro";
//            System.out.println(reponse);
//            out.println(reponse);
//
//        } catch (IOException e) {
//            System.out.println("pb service");
//        }
//        try {
//            client.close();
//            System.out.println("getSocketClient() déconnecté: " + getSocketClient().getInetAddress());
//        } catch (IOException e2) {
//            System.out.println("pb service");
//        }
//    }
//}
