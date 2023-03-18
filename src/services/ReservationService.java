//package services;
//
//import abonnee.Abonne;
//import document.*;
//
//import dataBase.Documents;
//import server.Service;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class ReservationService extends Service {
//    private Documents docs;
//    private Timer timer;
//    private static final long TPS_VALI = 7200000;//2h->ms
//
//    private class ReservationInvalide extends TimerTask {
//        private Document dvd;
//        private PrintWriter out;
//
//        public ReservationInvalide(Document d,PrintWriter out) {
//            this.dvd = d;
//            this.out=out;
//        }
//        @Override
//        public void run() {
//            this.dvd.retour();
//            System.out.println(">2h, la réservation est invalide");
//            out.println(">2h, la réservation est invalide");
//        }
//    }
//
//    public ReservationService(Socket socketServer, Documents d) {
//        super(socketServer);
//        this.docs = d;
//        this.timer=new Timer();
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
//            Abonne abo= docs.getAbonne(numAbonee);
//
//            if (doc != null) {
//                if (doc.reserveur()==null) {
//                    if (doc.emprunteur()==null) {
//                        synchronized (doc) {
//                            doc.reservationPour(abo);
//                            reponse = "Reservation réussie,veuillez emprunter à la médiathèque dans 2 heures, sinon la réservation sera invalide";
//                            this.timer.schedule(new ReservationInvalide(doc,out), TPS_VALI);
//                        }
//                    } else {
//                        reponse = "ce DVD est déjà emprunté";
//                    }
//                } else {
//                    reponse = "ce DVD est reservé";//pas defini jusqu’à 12h25 »
//                }
//            }
//            else
//                reponse ="Aucun document ne porte ce numéro";
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
