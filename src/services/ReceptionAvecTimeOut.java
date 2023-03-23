package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ReceptionAvecTimeOut {
    private static class TimeOut extends TimerTask {
        private Socket sockCli;
        private Timer t;

        public TimeOut (Socket s, Timer t){
            this.sockCli = s;
            this.t = t;
        }

        @Override
        public void run(){
            try {
                this.sockCli.close();
                this.t.cancel(); // pourquoi
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static String recevoir(BufferedReader sockIn, Socket sockCli) throws IOException {
        Timer t = new Timer();
        t.schedule(new TimeOut(sockCli, t), 1000L * 5);

        String cliRep = sockIn.readLine();
        t.cancel();

        return cliRep;
    }
}