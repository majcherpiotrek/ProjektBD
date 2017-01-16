package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.enumerations.Gender;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/*TODO
-wrzucić buttony do funkcji ustawiających scenę zamiast globalnych
-trzeba zrobić tak, żeby dodanie wyników zawodów powodowało zaktualizowanie punktów w wynikach zawodów
-przy dodawaniu wyników zawodów sprawdzać, czy zawodnicy na liście istnieją w tabeli zawodników!
-ADMIN: dodawanie sezonu, dodawanie zawodów + wyników zawodów, dodawanie zawodników
-listenery do choiceboxów
-wyświetlanie profilu zawodnika po wybraniu go z listy
-wyświetlanie profilu zawodów lub wyników zawodów po wybraniu ich z listy

PO ZROBIENIU DODAWANIA DANYCH PRZEZ ADMINA:
-dodać kobiety
-dodać kilka sezonów
 */
public class Main extends Application {

    private static Connection dbConnection;
    private Stage mainWindow;
    private Scene mainScene;
    private GridPane layout;
    private ChoiceBox<Gender> genderChoiceBox;
    private ChoiceBox<Integer> seasonChoiceBox;
    private Button rankingButton;
    private Button eventsListButton;
    private Button sailorListButton;
    private Button adminLoginButton;

    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
            //Połączenie z BD
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSl=false", "root", "root");
        }catch(Exception ex){
            AlertBox.Display("Błąd połączenia", ex.getMessage());
            return;
        }

        mainWindow=primaryStage;
        mainWindow.setTitle("Ranking Puchar Świata w windsurfingu!");

        //ChoiceBox do wyboru płci
        genderChoiceBox = new ChoiceBox<>();
        genderChoiceBox.getItems().addAll(Gender.MALE, Gender.FEMALE);
        genderChoiceBox.setValue(Gender.MALE);

        //ChoiceBox do wyboru sezonu
        seasonChoiceBox = new ChoiceBox<>();
        seasonChoiceBox.getItems().addAll(2014, 2015, 2016);
        seasonChoiceBox.setValue(seasonChoiceBox.getItems().get(seasonChoiceBox.getItems().size() - 1)); // najnowszy sezon

        //Przycisk wyświetlający klasyfikację generalną danego sezonu (wyświetlana od razu domyślnie)
        rankingButton = new Button("Klasyfikacja sezonu");
        rankingButton.setOnAction(e -> {
            //if(layout.getChildren().size() == 1)
              //  initStandardLayout(layout);
            mainWindow.setScene(mainScene);
        });

        //Przycisk wyświetlający listę zawodów w sezonie
        eventsListButton = new Button("Lista zawodów");
        eventsListButton.setOnAction(event -> {
            try {
                setEventsListLayout(mainWindow);

            }catch (Exception ex){
                AlertBox.Display("Błąd pobierania danych", ex.getMessage());
            }
        });

        //Przycisk wyświetlający listę wszystkich zawodników i zawodniczek
        sailorListButton = new Button("Lista wszystkich zawodników");
        sailorListButton.setOnAction(e -> {
            try {
                setAllCompetitorsListLayout(mainWindow);
            }catch(Exception ex){
                AlertBox.Display("Błąd pobierania danych", ex.getMessage());
            }
        });

        adminLoginButton = new Button("Logowanie administratora");
        adminLoginButton.setOnAction(e ->{
            Boolean isLoggedIn = false;
            isLoggedIn = LoginWindow.Display(dbConnection);
            System.out.println(isLoggedIn);
            if(isLoggedIn)
                setAdminInterfaceScene(mainScene, mainWindow);
        });

        TableView<Sailor> rankingTableView = new TableView<>();
        try{
            ObservableList<Sailor> sailorObservableList = SeasonRanking.getSailorsObservableList(dbConnection, genderChoiceBox.getValue(), seasonChoiceBox.getValue());
            rankingTableView = SeasonRanking.createSeasonRankigTableView(sailorObservableList);
        }catch (Exception ex){
            AlertBox.Display("Błąd pobierania danych", ex.getMessage());
        }

        //Layout
        layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setHgap(8);
        layout.setVgap(8);
        layout.add(genderChoiceBox, 0,0);
        layout.add(seasonChoiceBox, 1,0);
        layout.add(rankingButton,2,0);
        layout.add(eventsListButton,3,0);
        layout.add(sailorListButton, 4,0);
        layout.add(rankingTableView,0,1,5,1);
        layout.add(adminLoginButton, 0, 2);


        mainScene = new Scene(layout);
        mainWindow.setScene(mainScene);
        mainWindow.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void initStandardLayout(GridPane layoutToInit){
        layoutToInit.setPadding(new Insets(10,10,10,10));
        layoutToInit.setHgap(8);
        layoutToInit.setVgap(8);
        layoutToInit.add(genderChoiceBox, 0,0);
        layoutToInit.add(seasonChoiceBox, 1,0);
        layoutToInit.add(rankingButton,2,0);
        layoutToInit.add(eventsListButton,3,0);
        layoutToInit.add(sailorListButton, 4,0);
        layoutToInit.add(adminLoginButton, 0, 2);
    }

    private void setSeasonRankingScene(Stage parentWindow) throws Exception{

    }

    private void setAllCompetitorsListLayout(Stage parentWindow) throws Exception{

        GridPane allCompetitorsLayout = new GridPane();
        //zainicjowanie wspólnych przycisków
        allCompetitorsLayout.setPadding(new Insets(10,10,10,10));
        allCompetitorsLayout.setHgap(8);
        allCompetitorsLayout.setVgap(8);


        //Pozyskanie danych z bazy
        ObservableList<Sailor> allCompetitorsObservableList = AllCompetitorsList.getSailorsObservableList(dbConnection);
        TableView<Sailor> allCompetitorsTableView = AllCompetitorsList.createAllCompetitorsTableView(allCompetitorsObservableList);

        //Dodanie buttonów i tabeli do layoutu
        allCompetitorsLayout.add(rankingButton,0,0);
        allCompetitorsLayout.add(eventsListButton,1,0);
        allCompetitorsLayout.add(sailorListButton, 2,0);
        allCompetitorsLayout.add(adminLoginButton, 0,2);
        allCompetitorsLayout.add(allCompetitorsTableView, 0, 1, 3, 1);

        Scene allCompetitorsScene = new Scene(allCompetitorsLayout);
        parentWindow.setScene(allCompetitorsScene);
    }

    private void setEventsListLayout(Stage parentWindow) throws Exception{
        GridPane eventsListLayout = new GridPane();
        initStandardLayout(eventsListLayout);

        ObservableList<WindsurfingEvent> eventsInSeasonObservableList = EventsInSeason.getEventsObservableList(dbConnection, seasonChoiceBox.getValue());
        TableView<WindsurfingEvent> eventsInSeasonTableView = EventsInSeason.createEventsInSeasonTableView(eventsInSeasonObservableList);
        eventsListLayout.add(eventsInSeasonTableView,0, 1,5,1);

        Scene eventsListScene = new Scene(eventsListLayout);
        parentWindow.setScene(eventsListScene);
    }

    private void setAdminInterfaceScene(Scene parentScene, Stage parentWindow){
        Button back = new Button("back");
        back.setOnAction(e->{
           /* try{
                Admin admin = new Admin(dbConnection);
                admin.addSailor(123,"piotrek","majcher","M","polska","pulsboards","severne","dakine,caas");
            }catch(SQLException ex){
                AlertBox.Display("update failed", ex.getMessage());
            }*/
            parentWindow.setScene(parentScene);
        });
        VBox vBox = new VBox();
        vBox.getChildren().addAll(back);
        Scene scene = new Scene(vBox);
        parentWindow.setScene(scene);
    }


}
