package app;

import database.Data;
import document.DVD;
import server.Server;
import services.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

public class ServerApp {
    private static final int PORT_EMPRUNT;
    private static final int PORT_RETOUR;
    private static final int PORT_RESERVATION;

    static {
        PORT_RESERVATION = 3000;
        PORT_EMPRUNT = 4000;
        PORT_RETOUR = 5000;
    }

    public static void main(String[] args) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException{
        Data data = new Data();

        ServiceMediatheque.setData(data);

        Server sReservation = new Server(ReservationService.class, PORT_RESERVATION);
        Server sEmprunt = new Server(EmpruntService.class, PORT_EMPRUNT);
        Server sRetour = new Server(RetourService.class, PORT_RETOUR);

        ArrayList<Server> servers = new ArrayList<>(Arrays.asList(
                sReservation,
                sEmprunt,
                sRetour
        ));

        Thread t1 = new Thread(sReservation);
        Thread t2 = new Thread(sEmprunt);
        Thread t3 = new Thread(sRetour);

        t1.start();
        t2.start();
        t3.start();


        Scanner sc = new Scanner(System.in);
        while (true) {
            String s = sc.nextLine();
            if (Objects.equals(s, "quit")) {
                for (Server serv : servers) {
                    serv.close();
                }
                DVD.closeTimers();
                ReceptionTimeOut.closeTimeOut();
                data.saveData();
                break;
            }
        }
    }
}