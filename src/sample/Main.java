package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.enumerations.Gender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*TODO
-trzeba zrobić tak, żeby dodanie wyników zawodów powodowało zaktualizowanie punktów w wynikach zawodów
-ADMIN: dodawanie sezonu, dodawanie zawodów + wyników zawodów, dodawanie zawodników
-wyświetlenie listy zawodów w sezonie
-wyświetlenie listy zawodników w sezonie
 */
public class Main extends Application {

    public static Connection dbConnection;
    private Scene mainScene;

    @Override
    public void start(Stage mainWindow) throws Exception{
        mainWindow.setTitle("Ranking Puchar Świata w windsurfingu!");

        //ChoiceBox do wyboru płci
        ChoiceBox<Gender> genderChoiceBox = new ChoiceBox<>();
        genderChoiceBox.getItems().addAll(Gender.MALE, Gender.FEMALE);
        genderChoiceBox.setValue(Gender.MALE);

        //ChoiceBox do wyboru sezonu
        ChoiceBox<Integer> seasonChoiceBox = new ChoiceBox<>();
        seasonChoiceBox.getItems().addAll(2014, 2015, 2016);
        seasonChoiceBox.setValue(seasonChoiceBox.getItems().get(seasonChoiceBox.getItems().size() - 1)); // najnowszy sezon

        //Przycisk wyświetlający klasyfikację generalną danego sezonu (wyświetlana od razu domyślnie)
        Button rankingButton = new Button("Klasyfikacja sezonu");

        //Przycisk wyświetlający listę zawodów w sezonie
        Button eventsListButton = new Button("Lista zawodów");

        //Przycisk wyświetlający listę zawodników/zawodniczek startujących w danym sezonie
        Button sailorListButton = new Button("Lista startujących w sezonie");


        TableColumn<Sailor, String> placeColumn = new TableColumn<>("Miejsce");
        placeColumn.setMinWidth(200);
        placeColumn.setCellValueFactory(new PropertyValueFactory<>("placeInTable"));

        TableColumn<Sailor, String> nameColumn = new TableColumn<>("Imię");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Sailor, String> surnameColumn = new TableColumn<>("Nazwisko");
        surnameColumn.setMinWidth(200);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Sailor, String> sailNumberColumn = new TableColumn<>("Nr rejestracyjny");
        sailNumberColumn.setMinWidth(200);
        sailNumberColumn.setCellValueFactory(new PropertyValueFactory<>("sailNumber"));

        TableColumn<Sailor, String> pointsColumn = new TableColumn<>("Punkty");
        pointsColumn.setMinWidth(200);
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("seasonPoints"));

        ObservableList<Sailor> sailorsObservableList = FXCollections.observableArrayList();
        try {
            sailorsObservableList = SeasonRanking.getSailorsObservableList(dbConnection, genderChoiceBox.getValue(), seasonChoiceBox.getValue());
        }catch(Exception ex){
            AlertBox.Display("Bład pobierania danych", ex.getMessage());
        }
        TableView<Sailor> rankingTableView = new TableView<>();
        rankingTableView.setItems(sailorsObservableList);
        rankingTableView.getColumns().addAll(placeColumn, nameColumn, surnameColumn, sailNumberColumn, pointsColumn );

        //Layout
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setHgap(8);
        layout.setVgap(8);
        layout.add(genderChoiceBox, 0,0);
        layout.add(seasonChoiceBox, 1,0);
        layout.add(rankingButton,2,0);
        layout.add(eventsListButton,3,0);
        layout.add(sailorListButton, 4,0);
        layout.add(rankingTableView,0,1,5,1);


        mainScene = new Scene(layout);
        mainWindow.setScene(mainScene);
        mainWindow.show();
    }


    public static void main(String[] args) {
        try{
            //Połączenie z BD
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSl=false", "root", "root");
        }catch(Exception ex){
            AlertBox.Display("Błąd połączenia", ex.getMessage());
            return;
        }
        launch(args);
    }
}
