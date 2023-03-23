import dataBase.Data;
import server.Server;
import services.EmpruntService;
import services.ReservationService;
import services.RetourService;
import services.ServiveMediatheque;

import javax.management.timer.TimerMBean;
import java.awt.dnd.DropTarget;
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

//    private static class TimerBeforeClosingServer extends TimerTask {
//        private Data data;
//        private ArrayList<Server> serverToClose;
//
//        public TimerBeforeClosingServer (Data d, ArrayList<Server> s){
//            this.data = d;
//            this.serverToClose = s;
//        }
//
//        @Override
//        public void run() {
//            for (Server s : this.serverToClose){
//                s.close();
//            }
//            this.data.saveData();
//        }
//    }

    public static void main(String[] args) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        Data data = new Data();

        ServiveMediatheque.setData(data);

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
                // methode declared in Server.java
                // when interrupted, new customer wouldn't be able to connect the servers
//                sReservation.interrupt();
//                sEmprunt.interrupt();
//                sRetour.interrupt();

//                Timer t = new Timer();
//                t.schedule(new TimerBeforeClosingServer(data, servers), 1000L * 10);
                    for (Server serv : servers){
                        serv.close();
                    }
                break;
            }
        }
    }
}