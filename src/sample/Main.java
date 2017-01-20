package sample;

import GUI.GeneralInterface;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;


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


!!!!!!!!!!!!!!!!!!!!
GUI nadal się wysypuje w pewnym momencie przy przeskakiwaniu między kartami.
Naprawić gui i dodać panel administratora!!
 */
public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        Connection dbConnection;
        try{
            //Połączenie z BD
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSl=false", "root", "root");
        }catch(Exception ex){
            AlertBox.Display("Błąd połączenia", ex.getMessage());
            return;
        }

        GeneralInterface GUI = new GeneralInterface(primaryStage, dbConnection);
        GUI.Display();
    }


    public static void main(String[] args) {
        launch(args);
    }



    private void setAllCompetitorsListLayout(Stage parentWindow) throws Exception{
/*
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
        parentWindow.setScene(allCompetitorsScene);*/
    }



    private void setAdminInterfaceScene(Scene parentScene, Stage parentWindow){
        Button back = new Button("back");
       /* back.setOnAction(e->{
            try{
                Admin admin = new Admin(dbConnection);
                admin.addSailor(123,"piotrek","majcher","M","polska","pulsboards","severne","dakine,caas");

            }catch(SQLException ex){
                AlertBox.Display("update failed", ex.getMessage());
            }
            parentWindow.setScene(parentScene);
        });*/
        VBox vBox = new VBox();
        vBox.getChildren().addAll(back);
        Scene scene = new Scene(vBox);
        parentWindow.setScene(scene);
    }


}
