package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.AlertBox;

import java.sql.Connection;

/**
 * Created by piotrek on 20.01.17.
 */
public class AdminInterface {
    private Stage adminWindow;
    private Connection dbConnection;

    public AdminInterface(Connection connection) {
        this.adminWindow = new Stage();
        adminWindow.setTitle("Panel administracyjny");
        this.dbConnection = connection;
    }

    public void Display(){
        //Przycisk dodawania zawodnika
        Button addSailorButton = new Button("Dodaj zawodnika");
        addSailorButton.setMinWidth(140);
        addSailorButton.setPadding(new Insets(2,2,2,2));
        //Przycisk usunięcia zawodnika
        Button removeSailorButton = new Button("Usuń zawodnika");
        removeSailorButton.setMinWidth(140);
        removeSailorButton.setPadding(new Insets(2,2,2,2));
        //Przycisk dodawania zawodów
        Button addEventButton = new Button("Dodaj zawody");
        addEventButton.setMinWidth(140);
        addEventButton.setPadding(new Insets(2,2,2,2));
        //Przycisk usuwania zawodów
        Button removeEventButton = new Button("Usuń zawody");
        removeEventButton.setMinWidth(140);
        removeEventButton.setPadding(new Insets(2,2,2,2));
        //Przycisk dodania wyników zawodów
        Button addEventResultsButton = new Button("Dodaj wyniki zawodów");
        addEventResultsButton.setMinWidth(140);
        addEventResultsButton.setPadding(new Insets(2,2,2,2));
        //Przycisk usuwania wyników zawodów
        Button removeEventResultsButton = new Button("Usuń wyniki zawodów");
        removeEventResultsButton.setMinWidth(140);
        removeEventResultsButton.setPadding(new Insets(2,2,2,2));

        GridPane layout = new GridPane();
        layout.setHgap(15);
        layout.setVgap(8);
        layout.setPadding(new Insets(10,10,10,10));
        //Przyciski dodawania/usuwania zawodnika
        layout.add(addSailorButton, 0,0);
        layout.add(removeSailorButton,0,1);
        //Przyciski dodawania/usuwania zawodów
        layout.add(addEventButton,1,0);
        layout.add(removeEventButton,1,1);
        //Przyciski dodawania/usuwania wyników zawodów
        layout.add(addEventResultsButton,2,0);
        layout.add(removeEventResultsButton,2,1);

        Scene scene = new Scene(layout);
        adminWindow.setScene(scene);
        adminWindow.show();
    }

    private void showAddSailorWindow(){
        
    }

}
