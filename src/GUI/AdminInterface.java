package GUI;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Admin;
import sample.AlertBox;
import sample.AllCompetitorsList;
import sample.Sailor;
import sample.enumerations.Gender;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by piotrek on 20.01.17.
 */
public class AdminInterface {
    private Stage adminWindow;
    private Connection dbConnection;
    private Scene scene;

    public AdminInterface(Connection connection) {
        this.adminWindow = new Stage();
        adminWindow.setTitle("Panel administracyjny");
        adminWindow.setResizable(false);
        this.dbConnection = connection;
    }

    public void Display(){
        //Przycisk dodawania zawodnika
        Button addSailorButton = new Button("Dodaj zawodnika");
        addSailorButton.setMinWidth(140);
        addSailorButton.setPadding(new Insets(2,2,2,2));
        addSailorButton.setOnAction(e->showAddSailorWindow(scene));
        //Przycisk usunięcia zawodnika
        Button removeSailorButton = new Button("Usuń zawodnika");
        removeSailorButton.setMinWidth(140);
        removeSailorButton.setPadding(new Insets(2,2,2,2));
        removeSailorButton.setOnAction(e->{
            try {
                showRemoveSailorWindow(scene);
            }catch (Exception ex){
                AlertBox.Display("Błąd", "Nie udało się załadować listy zawodników!");
            }
        });
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

        scene = new Scene(layout);
        adminWindow.setScene(scene);
        adminWindow.show();
    }

    private void showAddSailorWindow(Scene parentScene){

        Button returnButton = new Button("Powrót");
        returnButton.setOnAction(e-> adminWindow.setScene(parentScene));

        //Etykiety pól wprowadzania danych
        Label sailNumberLabel = new Label("Nr rejestracyjny:");
        Label nameLabel = new Label("Imię:");
        Label surnameLabel = new Label("Nazwisko:");
        Label genderLabel = new Label("Płeć:");
        Label nationalityLabel = new Label("Narodowość:");
        Label boardBrandLabel = new Label("Marka desek:");
        Label sailBrandLabel = new Label("Marka żagli:");
        Label sponsorsLabel = new Label("Sponsorzy:");
        Label errorLabel = new Label();

        TextField sailNumberTextField = new TextField();
        TextField nameTextField = new TextField();
        TextField surnameTextField = new TextField();
        ChoiceBox<Gender> genderChoiceBox = new ChoiceBox<>();
        genderChoiceBox.getItems().addAll(Gender.MALE,Gender.FEMALE);
        genderChoiceBox.setValue(Gender.MALE);
        TextField nationalityTextField = new TextField();
        TextField boardBrandTextField = new TextField();
        TextField sailBrandTextField = new TextField();
        TextField sponsorsTextField = new TextField();

        Button confirmButton = new Button("Dodaj!");
        confirmButton.setOnAction(e->{

            String name = nameTextField.getText();
            String surname = surnameTextField.getText();
            Gender gender = genderChoiceBox.getValue();
            String genderString = (gender == Gender.FEMALE) ? "K" : "M";
            String nationality = nationalityTextField.getText();
            String boardBrand = boardBrandTextField.getText();
            String sailBrand = sailBrandTextField.getText();
            String sponsors = sponsorsTextField.getText();

            Integer sailNumber;
            try{

                sailNumber = Integer.parseInt(sailNumberTextField.getText());
                Admin admin = new Admin(dbConnection);
                try{
                    admin.addSailor(sailNumber,
                            name,
                            surname,
                            genderString,
                            nationality,
                            boardBrand,
                            sailBrand,
                            sponsors);

                    errorLabel.setTextFill(Color.GREEN);
                    errorLabel.setText("Dodawanie zawodnika zakończone powodzeniem!");
                }catch (SQLException ex){
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("Nie udało się dodać zawodnika!\nSprawdź wprowadzone dane!");
                }
            }catch (NumberFormatException ex){
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("Podaj poprawny numer rejestracyjny!");
            }
        });

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20,20,20,20));
        layout.setVgap(5);
        layout.setHgap(5);

        layout.add(sailNumberLabel,0,0);
        layout.add(sailNumberTextField,1,0);

        layout.add(nameLabel,0,1);
        layout.add(nameTextField,1,1);

        layout.add(surnameLabel,0,2);
        layout.add(surnameTextField,1,2);

        layout.add(genderLabel, 0,3);
        layout.add(genderChoiceBox,1,3);

        layout.add(nationalityLabel,0,4);
        layout.add(nationalityTextField,1,4);

        layout.add(boardBrandLabel,0,5);
        layout.add(boardBrandTextField,1,5);

        layout.add(sailBrandLabel,0,6);
        layout.add(sailBrandTextField,1,6);

        layout.add(sponsorsLabel,0,7);
        layout.add(sponsorsTextField,1,7);

        layout.add(errorLabel,0,8,2,1);

        layout.add(returnButton,0,9);
        layout.add(confirmButton,1,9);

        Scene addSailorScene = new Scene(layout);
        adminWindow.setScene(addSailorScene);
    }

    private void showRemoveSailorWindow(Scene parentScene) throws Exception{
        ObservableList<Sailor> allCompetitorsObservableList = AllCompetitorsList.getSailorsObservableList(dbConnection);
        TableView<Sailor> allSailorsTableView = AllCompetitorsList.createAllCompetitorsTableView(allCompetitorsObservableList);

        Label chooseSailorLabel = new Label("Wybierz zawodnika lub zawodniczkę do usunięcia:");
        chooseSailorLabel.setPadding(new Insets(5,5,5,5));
        Label errorLabel = new Label();

        Button returnButton = new Button("Powrót");
        returnButton.setPadding(new Insets(5,5,5,5));
        returnButton.setOnAction(e->adminWindow.setScene(parentScene));

        Button removeSailorButton = new Button("Usuń");
        removeSailorButton.setPadding(new Insets(5,5,5,5));
        removeSailorButton.setOnAction(e->{
            Sailor sailorToRemove = allSailorsTableView.getSelectionModel().getSelectedItem();
            try{
                Admin admin = new Admin(dbConnection);
                admin.removeSailor(sailorToRemove.getSailNumber());
                errorLabel.setTextFill(Color.GREEN);
                errorLabel.setText("Usunieto zawodnika: " + sailorToRemove.getSailNumber()+ " " + sailorToRemove.getName() + " " + sailorToRemove.getSurname());
                allCompetitorsObservableList.removeAll(sailorToRemove);
                allSailorsTableView.refresh();
            }catch (Exception ex){
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("Nie udało się wykonać operacji usuwania!");
            }
        });

        VBox layout = new VBox();
        layout.setSpacing(10);
        HBox buttonsLayout = new HBox();
        buttonsLayout.setSpacing(20);
        buttonsLayout.getChildren().addAll(returnButton,removeSailorButton);

        layout.getChildren().addAll(chooseSailorLabel,allSailorsTableView,errorLabel,buttonsLayout);

        Scene scene = new Scene(layout);
        adminWindow.setScene(scene);
    }

}
