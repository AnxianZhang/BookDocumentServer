package dataBase;

import document.Document;

import javax.print.Doc;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Documents {
    public ArrayList<Document> getDocuments() throws SQLException {
        ArrayList<Document> documents = new ArrayList<>();

        Statement statement = DatabaseConnection.getConnexion().createStatement();

        String documentsRequest = "SELECT * FROM document";
        String DVDsRequest = "SELECT * FROM DVD";
        String aboneeRequest = "SELECT * FROM Abonee";

        ResultSet documentsData = statement.executeQuery(documentsRequest);
        ResultSet DVDs = statement.executeQuery(DVDsRequest);
        ResultSet abonees = statement.executeQuery(aboneeRequest);

        docu
    }
}
