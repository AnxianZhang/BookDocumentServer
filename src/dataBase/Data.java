package dataBase;

import abonnee.Abonne;
import document.ConcurrentDocument;
import document.DVD;
import services.Document;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class Data {
    private ArrayList<Document> documents;
    private ArrayList<Abonne> abonees;

    public Data() throws SQLException {
        this.documents = new ArrayList<>();
        this.abonees = new ArrayList<>();
        initData();
    }

    private void initData() throws SQLException {
        DatabaseConnection.makeConnexion();

        // un statement par requete !!!
        Statement DVDStatement = DatabaseConnection.getConnexion().createStatement();
        Statement aboneesStatement = DatabaseConnection.getConnexion().createStatement();

        String DVDsRequest = "SELECT * FROM DVD";
        String aboneeRequest = "SELECT * FROM Abonee";

        ResultSet DVDs = DVDStatement.executeQuery(DVDsRequest);
        ResultSet abonees = aboneesStatement.executeQuery(aboneeRequest);

        addAllAbonee(abonees);
        addAllDocuments(DVDs);

        aboneesStatement.close();
        DVDStatement.close();
        DatabaseConnection.close();
    }

    private void addAllAbonee(ResultSet aboneesData) throws SQLException {
        while (aboneesData.next()) {
            int numAbonee = aboneesData.getInt("numAbonee");
            int anneeNaiss = aboneesData.getInt("anneeNaissance");
            String nom = aboneesData.getString("nom");

            this.abonees.add(new Abonne(numAbonee, anneeNaiss, nom));
        }
    }

    private void addAllDocuments(ResultSet documentsData) throws SQLException {
        while (documentsData.next()) {
            int numDoc = documentsData.getInt("numDoc");
            boolean estEmprunte = parseToBool(documentsData.getString("estEmprunte"));
            boolean estRetourne = parseToBool(documentsData.getString("estRetourne"));
            boolean estAdulte = parseToBool(documentsData.getString("estAdulte"));
            String titre = documentsData.getString("titre");
            Integer numAbonee = documentsData.getInt("numAbonee");
            this.documents.add(
                    new ConcurrentDocument(
                            new DVD(
                                    numDoc,
                                    estEmprunte,
                                    estRetourne,
                                    titre,
                                    estAdulte,
                                    getAbonee(numAbonee)
                            )
                    )
            );
        }
    }

    private boolean parseToBool(String val) {
        assert (Objects.equals(val, "N") || Objects.equals(val, "Y"));
        return !val.equals("N");
    }

    public Abonne getAbonee(Integer numAbonee) {
        if (numAbonee != null && numAbonee > 0 && numAbonee <= this.abonees.size()) {
            for (Abonne abonee : this.abonees) {
                if (abonee.getNumAbonee() == numAbonee) {
                    return abonee;
                }
            }
        }
        return null;
    }

    public Document getDocument(int numDoc) {
        return (numDoc > 0 && numDoc <= this.documents.size()) ? this.documents.get(numDoc - 1) : null;
    }
}