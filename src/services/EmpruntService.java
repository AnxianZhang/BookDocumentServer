package services;

import dataBase.Documents;
import server.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EmpruntService extends Service {
    private Documents docs;

    public EmpruntService(Socket socketServer, Documents d) {
        super(socketServer);
        this.docs = d;
    }

    @Override
    public void run() {
        String reponse = null;
        Socket client = this.getSocketClient();
        try {
            System.out.println("client connecté: " + getSocketClient().getInetAddress());
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            out.println("Tapez le numero DVD que vous voulez emprunter:");
            int numDVD = Integer.parseInt(in.readLine());
            out.println("Tapez votre numero Abonee:");
            int numAbonee = Integer.parseInt(in.readLine());

            System.out.println("Requète de " + client.getInetAddress()
                    + "numero DVD" + numDVD + " pour numero Abonee "
                    + numAbonee);

//            Cours cours = getCours(noCours);
            if (!docs.getDocuments(numDVD).estReserve
                    || (docs.getDocuments(numDVD).estReserve
                    && docs.getDocuments(numDVD).reserveur() == docs.getAbonne(numAbonee))) {
                if (!docs.getDocuments(numDVD).estEmprunte) {
                    synchronized (docs.getDocuments(numDVD)) {
                        docs.getDocuments(numDVD).empruntPar(docs.getAbonne(numAbonee));
                        reponse = "l'emprunte réussie";
                    }
                } else {
                    reponse = "ce DVD est déjà emprunté";
                }
            } else {
                reponse = "ce DVD est déjà reservé";
            }
            System.out.println(reponse);
            out.println(reponse);

        } catch (IOException e) {
            System.out.println("pb service");
        }
        try {
            client.close();
            System.out.println("getSocketClient() déconnecté: " + getSocketClient().getInetAddress());
        } catch (IOException e2) {
            System.out.println("pb service");
        }
    }

}
