package services;

import consolColor.Color;

import java.io.IOException;
import java.net.Socket;

public class RetourService extends ServiveMediatheque {
    public RetourService(Socket socketServer) throws IOException {
        super(socketServer);
    }

    @Override
    protected String welcomeMsg(){
        return "++++++++++ Welcome to the return service ++++++++++";
    }

    @Override
    protected void theSpecificService() {
        super.retourDocument();
        String reponse = "Return of the DVD (" + super.getNumDocument() + ") successful";
        System.out.println(Color.BLUE_BOLD + reponse  + Color.RESET);
        super.println(Color.BLUE_BOLD + reponse
                + Color.YELLOW_BOLD + "##You can leave by entering 'quit'##" + Color.RESET
        );
    }

    @Override
    protected String serviceName() {
        return "return";
    }

    @Override
    public void run() {
        try {
            super.welcomeInfo();
            super.requestDocument();
        } catch (IOException e) {
            super.timeOutMsg();
        } finally {
            super.closeSocketClient();
        }
    }
}