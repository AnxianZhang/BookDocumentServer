package services;

import consolColor.Color;

import java.io.IOException;
import java.net.Socket;

public class RetourService extends ServiveMediateque {
//    private static Data data;

    public RetourService(Socket socketServer) throws IOException {
        super(socketServer);
    }

//    public static void setData(Data d) {
//        data = d;
//    } // ok

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
//        System.out.println("========== Client connection " + super.getSocketClient().getInetAddress() + " ==========");

//        String reponse = "";
//        Document chosenDocument = null;

//        Socket client = super.getSocketClient();
//        BufferedReader in = null;
//        PrintWriter out = null;
//            super.println("++++++++++ Welcome to the return service ++++++++++");
//            String customerResponse = "";

        try {
            super.welcomeInfo();
            super.requestDocument();
//            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            out = new PrintWriter(client.getOutputStream(), true);


//            while (true) {
//                while (chosenDocument == null) {
//                    super.println("Please enter a (valid) number of DVD that you wish to return: ");
//
//                    customerResponse = super.readLine();
//                    if (Objects.equals(customerResponse, "quit")) {
////                    System.out.println(customerResponse);
//                        break;
//                    }
//                    int numDVD = Integer.parseInt(customerResponse);
//                    chosenDocument = data.getDocument(numDVD);
//                }
//
//                if (!customerResponse.equals("quit")) {
//                    super.println("ok");
//                    System.out.println("Request of " + client.getInetAddress()
//                            + " for DVD (num: " + chosenDocument.numero() + ")"
//                    );
//
//                    chosenDocument.retour();
//                    reponse = "Return of the DVD (" + chosenDocument.numero() + ") successful";
//                    System.out.println(reponse);
//                    super.println(reponse + "##You can leave by entering 'quit'##");
//
//                    customerResponse = "";
//                    chosenDocument = null;
//                }
//                else {
//                    break;
//                }
//
//            }
//            while (chosenDocument == null) {
//                out.println("Please enter a (valid) number of DVD that you wish to borrow: ");
//                int numDVD = Integer.parseInt(in.readLine());
//                chosenDocument = data.getDocument(numDVD);
//            }
//            out.println("ok");
//            out.println("ok");


        } catch (IOException e) {
            System.out.println("pb service");
        } finally {
            super.closeSocketClient();
        }
    }
}