package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by piotrek on 12.01.17.
 */
public class AllCompetitorsList {
    public static ObservableList<Sailor> getSailorsObservableList(Connection dbConnection) throws Exception {
        ObservableList<Sailor> sailorsObservableList = FXCollections.observableArrayList();

        String query = "SELECT * FROM zawodnicy ORDER BY nazwisko ASC;";
        Statement statement = dbConnection.createStatement();
        ResultSet resultSetCompetitors = statement.executeQuery(query);

        while (resultSetCompetitors.next()){
            Sailor sailor = new Sailor(Integer.toString(resultSetCompetitors.getInt("nr_rejestracyjny")),
                    resultSetCompetitors.getString("imie"),
                    resultSetCompetitors.getString("nazwisko"),
                    resultSetCompetitors.getString("plec"),
                    resultSetCompetitors.getString("narodowosc"),
                    resultSetCompetitors.getString("marka_desek"),
                    resultSetCompetitors.getString("marka_zagli"),
                    resultSetCompetitors.getString("sponsorzy"));
            sailorsObservableList.add(sailor);
        }

        return sailorsObservableList;
    }

    public static TableView<Sailor> createAllCompetitorsTableView(ObservableList<Sailor> sailorsObservableList){
        TableColumn<Sailor, String> surnameColumn = new TableColumn<>("Nazwisko");
        surnameColumn.setMinWidth(200);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Sailor, String> nameColumn = new TableColumn<>("Imię");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Sailor, String> sailNumberColumn = new TableColumn<>("Nr rejestracyjny");
        sailNumberColumn.setMinWidth(200);
        sailNumberColumn.setCellValueFactory(new PropertyValueFactory<>("sailNumber"));

        TableColumn<Sailor, String> nationalityColumn = new TableColumn<>("Narodowość");
        nationalityColumn.setMinWidth(200);
        nationalityColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));


        TableView<Sailor> rankingTableView = new TableView<>();
        rankingTableView.setItems(sailorsObservableList);
        rankingTableView.getColumns().addAll(surnameColumn, nameColumn, sailNumberColumn, nationalityColumn );

        return rankingTableView;
    }
}
