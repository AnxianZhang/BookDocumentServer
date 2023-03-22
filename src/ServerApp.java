import dataBase.Data;
import server.Server;
import services.EmpruntService;
import services.ReservationService;
import services.RetourService;
import services.ServiveMediateque;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class ServerApp {
    private static final int PORT_EMPRUNT;
    private static final int PORT_RETOUR;
    private static final int PORT_RESERVATION;

    static {
        PORT_RESERVATION = 3000;
        PORT_EMPRUNT = 4000;
        PORT_RETOUR = 5000;
    }

    public static void main(String[] args) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Data data = new Data();

        ServiveMediateque.setData(data);
        new Thread(new Server(ReservationService.class, PORT_RESERVATION)).start();
        new Thread(new Server(EmpruntService.class, PORT_EMPRUNT)).start();
        new Thread(new Server(RetourService.class, PORT_RETOUR)).start();
    }
}