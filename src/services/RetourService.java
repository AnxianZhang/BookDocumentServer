package services;

import dataBase.Data;
import document.Document;
import server.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class RetourService extends Service {
    private static Data data;

    public RetourService(Socket socketServer) {
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

        Socket client = super.getSocketClient();
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);

            out.println("++++++++++ Welcome to the return service ++++++++++");
            String customerResponse = "";

            while (true) {
                while (chosenDocument == null) {
                    out.println("Please enter a (valid) number of DVD that you wish to return: ");

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
                    System.out.println("Request of " + client.getInetAddress()
                            + "for DVD (num: " + chosenDocument.numero() + ")"
                    );

                    chosenDocument.retour();
                    reponse = "Return of the DVD (" + chosenDocument.numero() + ") successful";
                    System.out.println(reponse);
                    out.println(reponse + "##You can leave by entering 'quit'##");

                    customerResponse = "";
                    chosenDocument = null;
                }
                else {
                    break;
                }

            }
//            while (chosenDocument == null) {
//                out.println("Please enter a (valid) number of DVD that you wish to borrow: ");
//                int numDVD = Integer.parseInt(in.readLine());
//                chosenDocument = data.getDocument(numDVD);
//            }
//            out.println("ok");
//            out.println("ok");


        } catch (IOException e) {
            System.out.println("pb service");
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