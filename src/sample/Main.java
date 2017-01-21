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
-wyświetlanie wyników konkretnych zawodów
-wyświetlanie profili zawodników
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Connection dbConnection;
        try{
            //Połączenie z BD
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/windsurfingDB?useSSl=false", "root", "root");
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
}
