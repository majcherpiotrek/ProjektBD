package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

class AlertBox {

    static void Display(String windowTitle, String message) {

        Stage AlertBoxWindow = new Stage();
        AlertBoxWindow.initModality(Modality.APPLICATION_MODAL); //razem z showAndWait() powoduje, że nie można edytować innego okna, zanim to nie zostanie zamknięte
        AlertBoxWindow.setTitle(windowTitle);
        AlertBoxWindow.resizableProperty().setValue(false);

        Label label = new Label(message); //Wiadomość wyświetlana w oknie

        Button button = new Button();
        button.setText("OK");
        button.setPrefSize(120, 20);
        button.setPadding(new Insets(5, 5, 5, 5));
        button.setOnAction(e -> {

            AlertBoxWindow.close();
        });

        VBox layout = new VBox();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setSpacing(20);
        layout.getChildren().addAll(label,button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        AlertBoxWindow.setScene(scene);
        AlertBoxWindow.showAndWait();

    }
}

