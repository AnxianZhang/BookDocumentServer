import dataBase.Data;
import dataBase.DatabaseConnection;
import server.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerApp {
    private static final int PORT_EMPRUNT, PORT_RETOUR, PORT_RESERVATION;
    static{
        PORT_RESERVATION = 3000;
        PORT_EMPRUNT = 4000;
        PORT_RETOUR = 5000;
    }

    public static void main(String[] args) throws SQLException {
        Data data = new Data();

//        new Thread(new Server(ReservationService.class, PORT_RESERVATION)).start();
//        new Thread(new Server(EmpruntService.class, PORT_EMPRUNT)).start();
//        new Thread(new Server(RetourService.class, PORT_RETOUR)).start();
    }
}