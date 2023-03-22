import dataBase.Data;
import server.Server;
import services.EmpruntService;
import services.ReservationService;
import services.RetourService;
import services.ServiveMediateque;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class ServerApp {
    private static final int PORT_EMPRUNT;
    private static final int PORT_RETOUR;
    private static final int PORT_RESERVATION;

    static {
        PORT_RESERVATION = 3000;
        PORT_EMPRUNT = 4000;
        PORT_RETOUR = 5000;
    }

    public static void main(String[] args) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        Data data = new Data();

        ServiveMediateque.setData(data);

        Server sReservation = new Server(ReservationService.class, PORT_RESERVATION);
        Server sEmprunt = new Server(EmpruntService.class, PORT_EMPRUNT);
        Server sRetour = new Server(RetourService.class, PORT_RETOUR);

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
                sReservation.close();
                sEmprunt.close();
                sRetour.close();

                t1.join();
                t2.join();
                t3.join();

                data.saveData();
                break;
            }
        }
    }
}