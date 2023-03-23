package services;

import consolColor.Color;
import document.*;

import java.io.*;
import java.net.Socket;

public class EmpruntService extends ServiveMediatheque {
    public EmpruntService(Socket socketServer) throws IOException {
        super(socketServer);
    }

    @Override
    protected String welcomeMsg() {
        return "++++++++++ Welcome to the borrowing service ++++++++++";
    }

    @Override
    protected void theSpecificService() {
        try {
            super.emprunterDocument();

            System.out.println(Color.BLUE_BOLD + "Borrowing DVD (num: " + super.getNumDocument() + ") confirmed"
                    + Color.RESET
            );
            super.println(Color.BLUE_BOLD + "Borrowing DVD (num: " + super.getNumDocument() + ") confirms.##"
                    + Color.YELLOW_BOLD +
                    "You can leave by entering 'quit'.##"
                    + Color.RESET
            );
        } catch (RestrictionException e) {
            System.out.println(Color.RED_BOLD + (e.toString()
                    .replace("##", "\n")
                    .replace("You", super.getCurrentAbonneName()) + Color.RESET)
            );
            super.println(Color.RED_BOLD + e + Color.RESET);
        }
    }

    @Override
    protected String serviceName() {
        return "borrow";
    }

    @Override
    public void run() {
        try {
            super.searchAbonne();
            super.requestDocument();
        } catch (IOException e) {
            super.timeOutMsg();
        } finally {
            super.closeSocketClient();
        }
    }
}