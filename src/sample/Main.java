package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main extends Application {

    public static Connection dbConnection;

    @Override
    public void start(Stage mainWindow) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        mainWindow.setTitle("Ranking Puchar Świata w windsurfingu!");

        try{

        }catch(Exception ex){

        }
        mainWindow.setScene(new Scene(root, 300, 275));
        mainWindow.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
