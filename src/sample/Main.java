package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main extends Application {

    public static Connection dbConnection;

    @Override
    public void start(Stage mainWindow) throws Exception{
        mainWindow.setTitle("Ranking Puchar Świata w windsurfingu!");

        try{
            //Połączenie z BD
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSl=false", "root", "root");
        }catch(Exception ex){
            AlertBox.Display("Błąd połączenia", ex.getMessage());
        }

        //Zapytanie
        Statement statement = dbConnection.createStatement();

        //Pobranie danych
        ResultSet dataSet = statement.executeQuery("SELECT * FROM zawodnicy");

        ObservableList<Sailor> sailorsObservableList = FXCollections.observableArrayList();

        while(dataSet.next()){
            sailorsObservableList.add(
                    new Sailor(dataSet.getString("nr_rejestracyjny"),
                                dataSet.getString("imie"),
                                dataSet.getString("nazwisko"),
                                dataSet.getString("plec"),
                                dataSet.getString("narodowosc"),
                                dataSet.getString("marka_desek"),
                                dataSet.getString("marka_zagli"),
                                dataSet.getString("sponsorzy")));
        }

        TableView<Sailor> sailorsTable;

        TableColumn<Sailor, String> sailNumberColumn = new TableColumn<>("sailNumber");
        sailNumberColumn.setMinWidth(200);
        sailNumberColumn.setCellValueFactory(new PropertyValueFactory<>("sailNumber"));

        TableColumn<Sailor, String> nameColumn = new TableColumn<>("name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Sailor, String> surnameColumn = new TableColumn<>("surname");
        surnameColumn.setMinWidth(200);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Sailor, String> sexColumn = new TableColumn<>("sex");
        sexColumn.setMinWidth(200);
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));

        TableColumn<Sailor, String> nationalityColumn = new TableColumn<>("nationality");
        nationalityColumn.setMinWidth(200);
        nationalityColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));

        TableColumn<Sailor, String> sailBrandColumn = new TableColumn<>("sailBrand");
        sailBrandColumn.setMinWidth(200);
        sailBrandColumn.setCellValueFactory(new PropertyValueFactory<>("sailBrand"));

        TableColumn<Sailor, String> boardBrandColumn = new TableColumn<>("boardBrand");
        boardBrandColumn.setMinWidth(200);
        boardBrandColumn.setCellValueFactory(new PropertyValueFactory<>("boardBrand"));

        TableColumn<Sailor, String> sponsorsColumn = new TableColumn<>("sponsors");
        sponsorsColumn.setMinWidth(200);
        sponsorsColumn.setCellValueFactory(new PropertyValueFactory<>("sponsors"));

        sailorsTable = new TableView<>();
        sailorsTable.setItems(sailorsObservableList);
        sailorsTable.getColumns().addAll(sailNumberColumn, nameColumn,
                surnameColumn, sexColumn,
                nationalityColumn, sailBrandColumn,
                boardBrandColumn, sponsorsColumn);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(sailorsTable);
        Scene scene = new Scene(vbox);
        mainWindow.setScene(scene);
        mainWindow.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
