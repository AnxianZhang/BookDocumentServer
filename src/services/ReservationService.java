package services;

import consolColor.Color;
import database.RestrictionException;

import java.io.IOException;
import java.net.Socket;

public class ReservationService extends ServiceMediatheque {

    public ReservationService(Socket socketServer) throws IOException {
        super(socketServer);
    }

    @Override
    protected String welcomeMsg() {
        return "++++++++++ Welcome to the booking service ++++++++++";
    }

    @Override
    protected void theSpecificService() {
        try {
            super.reserverDocument();
            super.println(Color.BLUE_BOLD + "Reservation of the DVD confirmed, you have 2 hours to come and pick it up. ##"
                    + Color.YELLOW_BOLD + "Otherwise, we will be forced to cancel it.##You can leave by entering 'quit'.##"
                    + Color.RESET
            );
            System.out.println(Color.BLUE_BOLD + "Booking DVD (num: " + super.getNumDocument() + ") confirmed" + Color.RESET);
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
        return "book";
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