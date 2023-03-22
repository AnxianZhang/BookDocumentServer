package dataBase;

import abonnee.Abonne;
import document.ConcurrentDocument;
import document.DVD;
import services.Document;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class Data {
    private ArrayList<Document> documents;
    private ArrayList<Abonne> abonees;

    public Data(){
        this.documents = new ArrayList<>();
        this.abonees = new ArrayList<>();
        try {
            initData();
        }catch (SQLException e){
            e.printStackTrace();
        }
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

    public void saveData() {
        try {
            DatabaseConnection.makeConnexion();
            DatabaseConnection.getConnexion().setAutoCommit(false);
            PreparedStatement updateStatement = DatabaseConnection.getConnexion().prepareStatement(
                    "UPDATE DVD SET estEmprunte = ?, estRetourne = ?, numAbonee = ? WHERE numDoc = ?"
            );
            for (Document d : this.documents) {
                Abonne a = d.emprunteur();

                updateStatement.setString(1, a == null ? "N" : "Y");
                updateStatement.setString(2, a == null ? "Y" : "N");
                if (a == null) {
                    updateStatement.setNull(3, Types.INTEGER);
                }
                else {
                    updateStatement.setInt(3, a.getNumAbonee());
                }
                updateStatement.setInt(4, d.numero());
                updateStatement.executeUpdate();
            }

            DatabaseConnection.getConnexion().commit();
            updateStatement.close();
            DatabaseConnection.close();
        } catch (Exception e) {
            try {
                DatabaseConnection.getConnexion().rollback();
            } catch (SQLException eSQL) {
                System.out.println("rollback success");
            }
            System.out.println("Problem occurred in data update");
//            e.printStackTrace();
        }
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