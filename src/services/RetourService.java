package services;

import server.Service;

import java.net.Socket;

public class RetourService extends Service {
    public RetourService(Socket socketServer) {
        super(socketServer);
    }

    @Override
    public void run() {

    }
}
