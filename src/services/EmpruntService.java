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
        this.docs=d;
    }

    @Override
    public void run() {
        try {
            System.out.println("client connecté: " + getSocketClient().getInetAddress());

            InputStreamReader isReader = new InputStreamReader(getSocketClient().getInputStream());
            BufferedReader reader = new BufferedReader(isReader);
            PrintWriter writer = new PrintWriter(getSocketClient().getOutputStream(), true);
//			System.out.println(reader.readLine());
            while (true) {
                int numDVD=0;
                int numAbonee=0;
                String nextLine = reader.readLine();
                System.out.println("entrez le numDVD que vous voulez emprunter, format: DVD+numDVD");
                System.out.println("entrez votre numAbonee, format: ABO+numAbonee");
                if (nextLine != null&& nextLine.contains("DVD")) {
                    synchronized (this) {
                    numDVD=Integer.parseInt(nextLine.substring(4));}
                    System.out.println("numDVD:"+numDVD+"est enregistree");
                }
                if (nextLine != null&& nextLine.contains("ABO")) {
                    synchronized (this) {
                    numAbonee=Integer.parseInt(nextLine.substring(4));}
                    System.out.println("numAbonee:"+numAbonee+"est enregistree"+"\n"+"waiting..");
                }
                if (numDVD != 0&& numAbonee != 0) {
                    if(!docs.getDocuments(numDVD).estReserve||(docs.getDocuments(numDVD).estReserve&&docs.getDocuments(numDVD).reserveur()==docs.getAbonne(numAbonee))){
                        if(!docs.getDocuments(numDVD).estEmprunte){
                            synchronized(docs){
                                docs.getDocuments(numDVD).empruntPar(docs.getAbonne(numAbonee));
                            }
                            System.out.println("ok, c fait, emprunter reussi");
                        }
                        else{System.out.println("ce DVD: "+numDVD+"est déjà emprunté");}
                    }
                    else{System.out.println("ce DVD: "+numDVD+"est déjà reservé");}
                }

//                if (nextLine != null) {
//                    StringBuffer line = new StringBuffer(nextLine);
//                    System.out.println(line);
////                    writer.println(line.reverse());
//                }
                else {
                    System.out.println("getSocketClient() déconnecté: " + getSocketClient().getInetAddress());
                    break;
                }
                //				writer.println(line);
            }

        } catch (IOException e) {
            System.out.println("pb service");
        }
    }
}
