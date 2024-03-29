package services;

import abonnee.Abonne;
import consolColor.Color;
import database.Data;
import database.Document;
import database.RestrictionException;
import server.Service;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public abstract class ServiceMediatheque extends Service {
    private static Data data;
    private Document chosenDocument;
    private Abonne currentAbonne;
    private String customerResponse;

    protected ServiceMediatheque(Socket socketClient) throws IOException {
        super(socketClient);
        this.chosenDocument = null;
        this.currentAbonne = null;
        this.customerResponse = "";
    }

    public static void setData(Data d) {
        data = d;
    }

    protected abstract String welcomeMsg();

    protected abstract void theSpecificService();

    protected abstract String serviceName();

    protected void welcomeInfo() {
        System.out.println("========== Client connection " + super.getSocketClient().getInetAddress() + " ==========");
        super.println(welcomeMsg());
    }

    protected void searchAbonne() throws IOException {
        welcomeInfo();

        while (this.currentAbonne == null) {
            super.println("Please enter your customer number (a valid one): ||");

            this.customerResponse = ReceptionTimeOut.recevoir(super.getSockIn(), super.getSocketClient());
            if (Objects.equals(this.customerResponse, "quit")) {
                break;
            }
            int numAbonee = Integer.parseInt(customerResponse);
            this.currentAbonne = data.getAbonee(numAbonee);
        }
    }

    private void handlingResponse() {
        super.println("ok");

        String abonneInfo = "";
        if (this.currentAbonne != null) {
            abonneInfo = " (customer name: " + this.currentAbonne.getNom() + " num: " + currentAbonne.getNumAbonee() + ")";
        }

        System.out.println(Color.BLUE_BOLD +
                "Request of " + super.getSocketClient().getInetAddress() + abonneInfo
                + " for DVD (num: " + this.chosenDocument.numero() + ")"
                + Color.RESET
        );

        theSpecificService();

        // init for next request
        this.customerResponse = "";
        this.chosenDocument = null;
    }

    protected void requestDocument() throws IOException {
        while (true) {
            if (!this.customerResponse.equals("quit")) {
                while (chosenDocument == null) {
                    super.println("Please enter a (valid) number of DVD that you wish to " + serviceName() + ": ");

                    this.customerResponse = ReceptionTimeOut.recevoir(super.getSockIn(), super.getSocketClient()); // time
                    if (Objects.equals(this.customerResponse, "quit")) {
                        break;
                    }
                    int numDVD = Integer.parseInt(this.customerResponse);
                    this.chosenDocument = data.getDocument(numDVD);
                }
                if (!this.customerResponse.equals("quit")) {
                    handlingResponse();
                }
            } else {
                break;
            }
        }
    }

    protected void timeOutMsg() {
        System.out.println(Color.RED_BOLD + "Time out of: " + super.getSocketClient().getInetAddress() + Color.RESET);
    }

    protected void closeSocketClient() {
        try {
            super.getSocketClient().close();
            System.out.println("========== Client disconnection " + super.getSocketClient().getInetAddress() + " deconnectee ==========");
            System.out.println();
        } catch (IOException e) {
            System.out.println("Problem when closing socket in ServiceMediatheque");;
        }
    }

    protected int getNumDocument() {
        return this.chosenDocument.numero();
    }

    protected String getCurrentAbonneName() {
        return this.currentAbonne.getNom();
    }

    protected void emprunterDocument() throws RestrictionException {
        this.chosenDocument.empruntPar(this.currentAbonne);
    }

    protected void reserverDocument() throws RestrictionException {
        this.chosenDocument.reservationPour(this.currentAbonne);
    }

    protected void retourDocument() {
        this.chosenDocument.retour();
    }
}