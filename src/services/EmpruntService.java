package services;

import server.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EmpruntService extends Service {
    public EmpruntService(Socket socketServer) {
        super(socketServer);
    }

    @Override
    public void run() {
        try {

            //Service Inversee
            System.out.println("client connecté: " + getSocketClient().getInetAddress());
            InputStreamReader isReader = new InputStreamReader(getSocketClient().getInputStream());
            BufferedReader reader = new BufferedReader(isReader);
            PrintWriter writer = new PrintWriter(getSocketClient().getOutputStream(), true);
//			System.out.println(reader.readLine());
            while (true) {
                String nextLine = reader.readLine();
                if (nextLine != null) {
                    StringBuffer line = new StringBuffer(nextLine);
                    System.out.println(line);
                    writer.println(line.reverse());
                }
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
