package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by piotrek on 12.01.17.
 */
class LoginWindow {
    private static Boolean returnValue = false;
    static Boolean Display(Connection dbConnection){
        Stage loginWindow = new Stage();
        loginWindow.initModality(Modality.APPLICATION_MODAL);
        loginWindow.setTitle("Logowanie");
        loginWindow.resizableProperty().setValue(false);


        Label message = new Label("Aby zalogować się jako administrator\nmusisz podać login oraz hasło.");
        Label loginLabel = new Label("Login:");
        Label passLabel = new Label("Hasło:");
        Label errorLabel = new Label();

        TextField loginField = new TextField();
        loginField.setPromptText("twój login");

        PasswordField passField = new PasswordField();
        passField.setPromptText("twoje hasło");

        Button loginButton = new Button("Zaloguj");
        loginButton.setOnAction(e ->{
            String login = loginField.getText();
            String password = passField.getText();

            try {
                Statement statement = dbConnection.createStatement();
                String query = "SELECT * FROM administratorzy;";

                ResultSet adminResultSet = statement.executeQuery(query);

                while (adminResultSet.next()){
                    String adminLogin = adminResultSet.getString("login");
                    String adminPass = adminResultSet.getString("haslo");

                    if(adminLogin.equals(login) && adminPass.equals(password)){
                        returnValue = true;
                        loginWindow.close();
                        break;
                    }
                    else
                        returnValue = false;
                }
                if(!returnValue){
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("Błędny login lub hasło!");
                }
            }catch (Exception ex){
                loginWindow.close();
                AlertBox.Display("Błąd", ex.getMessage());
            }
        });

        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(10,50,10,50));

        layout.add(message,0,0, 2,1);
        layout.add(loginLabel, 0, 1);
        layout.add(loginField,1,1);
        layout.add(passLabel,0,2);
        layout.add(passField,1,2);
        layout.add(errorLabel,0,3,2,1);
        layout.add(loginButton,1,4);

        Scene loginScene = new Scene(layout);
        loginWindow.setScene(loginScene);
        loginWindow.showAndWait();
        return returnValue;
    }
}
