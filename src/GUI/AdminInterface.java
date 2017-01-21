package GUI;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.*;
import sample.enumerations.Gender;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Klasa wyświetlająca GUI administratora
 * Created by piotrek on 20.01.17.
 */
public class AdminInterface {
    private Stage adminWindow;
    private Connection dbConnection;
    private Scene scene;
    TableView<WindsurfingEvent> eventsTableView;

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
        addEventButton.setOnAction(e->{
                showAddEventWindow(scene);
        });
        
        //Przycisk usuwania zawodów
        Button removeEventButton = new Button("Usuń zawody");
        removeEventButton.setMinWidth(140);
        removeEventButton.setPadding(new Insets(2,2,2,2));
        removeEventButton.setOnAction(e->{
        	try {
        		showRemoveEventWindow(scene);

        	}catch (Exception ex){
        		AlertBox.Display("Błąd", "Nie udało się załadować listy zawod�w!");
               }
            });
        
        //Przycisk dodania wyników zawodów
        Button addEventResultsButton = new Button("Dodaj wyniki zawodów");
        addEventResultsButton.setMinWidth(140);
        addEventResultsButton.setPadding(new Insets(2,2,2,2));
        addEventResultsButton.setOnAction(e->{
            try {
                showAddResultsWindow(scene);
            }catch (Exception ex){
                AlertBox.Display("Błąd", "Nie udało się załadować listy zawodów!");
            }
        });
        
        //Przycisk usuwania wynik�w
        Button removeEventResultsButton = new Button("Usuń wyniki zawod�w");
        removeEventResultsButton.setMinWidth(140);
        removeEventResultsButton.setPadding(new Insets(2,2,2,2));
        removeEventResultsButton.setOnAction(e->{
        	try {
        		showRemoveEventsResultsWindow(scene);

        	}catch (Exception ex){
        		AlertBox.Display("Błąd", "Nie udało się załadować listy zawod�w!");
               }
            });
        
        //Przycisk dodawania administratora
        Button addAdminButton = new Button("Dodaj administrtora");
        addAdminButton.setMinWidth(140);
        addAdminButton.setPadding(new Insets(2,2,2,2));
        addAdminButton.setOnAction(e->{
                showAddAdminWindow(scene);
        });
        
        //Przycisk usuwania administratora
        Button removeAdminButton = new Button("Usuń administratora");
        removeAdminButton.setMinWidth(140);
        removeAdminButton.setPadding(new Insets(2,2,2,2));
        removeAdminButton.setOnAction(e->{
            showRemoveAdminWindow(scene);
        });

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
        //Przyciski dodawania/usuwania administratora
        layout.add(addAdminButton,3,0);
        layout.add(removeAdminButton,3,1);
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

    private void showAddEventWindow(Scene parentScene){
        Button returnButton = new Button("Powrót");
        returnButton.setOnAction(e-> adminWindow.setScene(parentScene));

        //Etykiety pól wprowadzania danych
        Label eventIdLabel = new Label("ID:");
        Label eventNameLabel = new Label("Nazwa:");
        Label eventLocationLabel = new Label("Miejsce rozegrania:");
        Label eventDateLabel = new Label("Data (RRRR-MM-DD):");
        Label eventPrizeMoneyLabel = new Label("Pula nagród:");
        Label eventSeasonLabel = new Label("Sezon:");
        Label errorLabel = new Label();

        TextField eventIDTextField = new TextField();
        TextField eventNameTextField = new TextField();
        TextField eventLocationTextField = new TextField();
        TextField eventDateTextField = new TextField();
        TextField eventPrizeMoneyTextField = new TextField();
        TextField eventSeasonTextField = new TextField();

        Button confirmButton = new Button("Dodaj!");
        confirmButton.setOnAction(e->{

            String eventIDString = eventIDTextField.getText();
            String eventNameString = eventNameTextField.getText();
            String eventLocationString = eventLocationTextField.getText();
            String eventDateString = eventDateTextField.getText();
            String eventPrizeMoneyString = eventPrizeMoneyTextField.getText();
            String eventSeasonString = eventSeasonTextField.getText();

            Integer eventID;
            Integer eventSeason;
            Integer eventPrizeMoney;
            try{
                eventID = Integer.parseInt(eventIDString);
                eventSeason = Integer.parseInt(eventSeasonString);
                eventPrizeMoney = Integer.parseInt(eventPrizeMoneyString);
                Admin admin = new Admin(dbConnection);
                try{
                    admin.addEvent(eventID,
                            eventNameString,
                            eventLocationString,
                            eventDateString,
                            eventPrizeMoney,
                            eventSeason);
                    errorLabel.setTextFill(Color.GREEN);
                    errorLabel.setText("Dodawanie zawodów zakończone powodzeniem!");
                }catch (SQLException ex){
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("Nie udało się dodać zawodów!\nSprawdź wprowadzone dane!");
                }
            }catch (NumberFormatException ex){
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("Podaj poprawne ID, pulę nagród oraz sezon");
            }
        });

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20,20,20,20));
        layout.setVgap(5);
        layout.setHgap(5);

        layout.add(eventIdLabel,0,0);
        layout.add(eventIDTextField,1,0);

        layout.add(eventNameLabel,0,1);
        layout.add(eventNameTextField,1,1);

        layout.add(eventLocationLabel,0,2);
        layout.add(eventLocationTextField,1,2);

        layout.add(eventDateLabel, 0,3);
        layout.add(eventDateTextField,1,3);

        layout.add(eventPrizeMoneyLabel,0,4);
        layout.add(eventPrizeMoneyTextField,1,4);

        layout.add(eventSeasonLabel,0,5);
        layout.add(eventSeasonTextField,1,5);

        layout.add(errorLabel,0,6,2,1);

        layout.add(returnButton,0,7);
        layout.add(confirmButton,1,7);

        Scene addSailorScene = new Scene(layout);
        adminWindow.setScene(addSailorScene);
    }

    private void showAddAdminWindow(Scene parentScene){
        Label loginLabel = new Label("Login:");
        Label passLabel = new Label("Hasło:");
        Label errorLabel = new Label();

        TextField loginTextField = new TextField();
        TextField passwordTextField = new TextField();

        Button returnButton = new Button("Powrót");
        returnButton.setOnAction(e->adminWindow.setScene(parentScene));

        Button confirmButton = new Button("Dodaj administratora");
        confirmButton.setOnAction(e->{
            String login = loginTextField.getText();
            String pass = passwordTextField.getText();

            if(login.length() < 5 || pass.length() < 5){
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("Login i hasło muszą zawierać\nprzynajmniej 5 znaków!");
            }else{
                Admin admin = new Admin(dbConnection);
                try{
                    admin.addAdmin(login,pass);
                    errorLabel.setTextFill(Color.GREEN);
                    errorLabel.setText("Dodanie nowego konta administratora\nzakończone powodzeniem.");
                }catch (SQLException ex){
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("Nie udało się dodać administratora.");
                }
            }
        });

       GridPane layout = new GridPane();
       layout.setPadding(new Insets(20,20,20,20));
       layout.setHgap(10);
       layout.setVgap(10);

       layout.add(loginLabel,0,0);
       layout.add(loginTextField,1,0);

       layout.add(passLabel,0,1);
       layout.add(passwordTextField,1,1);

       layout.add(errorLabel,0,2,2,1);

       layout.add(returnButton,0,3);
       layout.add(confirmButton,1,3);

       Scene scene = new Scene(layout);
       adminWindow.setScene(scene);
    }

    private void showRemoveAdminWindow(Scene parentScene){
        Label loginLabel = new Label("Login:");
        Label passLabel = new Label("Hasło:");
        Label errorLabel = new Label();

        TextField loginTextField = new TextField();
        TextField passwordTextField = new TextField();

        Button returnButton = new Button("Powrót");
        returnButton.setOnAction(e->adminWindow.setScene(parentScene));

        Button confirmButton = new Button("Usuń administratora");
        confirmButton.setOnAction(e->{
            String login = loginTextField.getText();
            String pass = passwordTextField.getText();

            Admin admin = new Admin(dbConnection);
            try{
                admin.removeAdmin(login,pass);
                errorLabel.setTextFill(Color.GREEN);
                errorLabel.setText("Usunięcie konta administratora\nzakończone powodzeniem.");
            }catch (SQLException ex){
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("Błędne dane!");
            }

        });

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20,20,20,20));
        layout.setHgap(10);
        layout.setVgap(10);

        layout.add(loginLabel,0,0);
        layout.add(loginTextField,1,0);

        layout.add(passLabel,0,1);
        layout.add(passwordTextField,1,1);

        layout.add(errorLabel,0,2,2,1);

        layout.add(returnButton,0,3);
        layout.add(confirmButton,1,3);

        Scene scene = new Scene(layout);
        adminWindow.setScene(scene);
    }

    private void showAddResultsWindow(Scene parentScene) throws Exception {
        //Zainicjowanie listy sezonów
        ArrayList<Integer> allSeasons;
        try {
            allSeasons = Seasons.getAllSeasons(dbConnection);
        } catch (SQLException ex) {
            AlertBox.Display("Błąd połączenia!", ex.getMessage());
            return;
        }
        //ChoiceBox do wyboru sezonu
        ChoiceBox<Integer> seasonChoiceBox = new ChoiceBox<>();
        for (Integer season : allSeasons)
            seasonChoiceBox.getItems().add(season);
        seasonChoiceBox.setValue(seasonChoiceBox.getItems().get(0)); // najnowszy sezon

        ObservableList<WindsurfingEvent> eventsInSeasonObservableList = EventsInSeason.getEventsObservableList(dbConnection, seasonChoiceBox.getValue());
        eventsTableView = EventsInSeason.createEventsInSeasonTableView(eventsInSeasonObservableList);

        Button returnButton = new Button("Powrót");
        returnButton.setOnAction(e->adminWindow.setScene(parentScene));

        Button addResultsButton = new Button("Dodaj wyniki");
        addResultsButton.setOnAction(e->{
            try {
                showAddResultsChooseSailorWindow(eventsTableView.getSelectionModel().getSelectedItem().getEventID(), seasonChoiceBox.getValue());
            } catch (Exception ex) {
                AlertBox.Display("Błąd", "Nie udało się załadować listy zawodników");
            }
        });

        VBox layout = new VBox();
        Button refershEventsTableViewButton = new Button("Odśwież listę zawodów");
        refershEventsTableViewButton.setOnAction(e->{
            try {
                layout.getChildren().remove(eventsTableView);
                ObservableList<WindsurfingEvent> eventsList = EventsInSeason.getEventsObservableList(dbConnection, seasonChoiceBox.getValue());
                eventsTableView = EventsInSeason.createEventsInSeasonTableView(eventsList);
                layout.getChildren().add(eventsTableView);
            }catch (Exception ex){
                AlertBox.Display("Błąd", "Nie udało się załadować listy zawodów!");
            }
        });


        layout.setSpacing(10);
        HBox buttonsLayout = new HBox();
        buttonsLayout.setSpacing(20);

        buttonsLayout.getChildren().addAll(returnButton,addResultsButton);

        layout.getChildren().addAll(seasonChoiceBox,refershEventsTableViewButton,buttonsLayout,eventsTableView);

        Scene scene = new Scene(layout);
        adminWindow.setScene(scene);
    }

    private void showAddResultsChooseSailorWindow(Integer eventID, Integer season) throws Exception {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Wybierz zawodnika do dodania wyniku");
        window.setResizable(false);

        ObservableList<Sailor> allCompetitorsObservableList = AllCompetitorsList.getSailorsObservableList(dbConnection);
        TableView<Sailor> allSailorsTableView = AllCompetitorsList.createAllCompetitorsTableView(allCompetitorsObservableList);

        Label singleEliminationResultLabel = new Label("Miejsce w pojedynczej eliminacji:");
        Label doubleEliminationResultLabel = new Label("Miejsce w podwójnej eliminacji:");
        Label rankingPointsLabel = new Label("Zdobyte punkty rankingowe:");
        Label errorLabel = new Label();

        TextField singleEliminationReultTextField = new TextField();
        TextField doubleEliminationResultTextField = new TextField();
        TextField rankingPointsTextField = new TextField();


        Button addResultButton = new Button("Dodaj wynik");
        addResultButton.setOnAction(e->{
           // (Integer eventID, Integer sailorID, Integer singleEliminationResult, Integer doubleEliminationResult, Integer rankingPoints, Integer season)
             String sailNumberString = allSailorsTableView.getSelectionModel().getSelectedItem().getSailNumber();
             String singleElimResString = singleEliminationReultTextField.getText();
             String doubleElimReString = doubleEliminationResultTextField.getText();
             String rankingPointsString = rankingPointsTextField.getText();

             Integer sailNumber;
             Integer singleResult;
             Integer doubleResult;
             Integer rankingPoints;
            try{
                sailNumber = Integer.parseInt(sailNumberString);
                singleResult = Integer.parseInt(singleElimResString);
                doubleResult = Integer.parseInt(doubleElimReString);
                rankingPoints = Integer.parseInt(rankingPointsString);
                Admin admin = new Admin(dbConnection);
                try{
                    admin.addEventResults(eventID,
                            sailNumber,
                            singleResult,
                            doubleResult,
                            rankingPoints,
                            season);

                    errorLabel.setTextFill(Color.GREEN);
                    errorLabel.setText("Dodawanie wyników zawodów zakończone powodzeniem!");
                }catch (SQLException ex){
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("Nie udało się dodać wyników!\nSprawdź wprowadzone dane!");
                }
            }catch (NumberFormatException ex){
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("Podaj poprawne dane!");
            }
        });

        GridPane layout = new GridPane();

        layout.add(allSailorsTableView,0,0,2,1);

        layout.add(singleEliminationResultLabel,0,1);
        layout.add(singleEliminationReultTextField,1,1);

        layout.add(doubleEliminationResultLabel,0,2);
        layout.add(doubleEliminationResultTextField,1,2);

        layout.add(rankingPointsLabel,0,3);
        layout.add(rankingPointsTextField,1,3);

        layout.add(errorLabel,0,4,2,1);
        layout.add(addResultButton,0,5);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
    
    
    private void showRemoveEventWindow(Scene parentScene) throws Exception{
    	 //Zainicjowanie listy sezonów
        ArrayList<Integer> allSeasons;
        try {
            allSeasons = Seasons.getAllSeasons(dbConnection);
        } catch (SQLException ex) {
            AlertBox.Display("Błąd połączenia!", ex.getMessage());
            return;
        }
        //ChoiceBox do wyboru sezonu
        ChoiceBox<Integer> seasonChoiceBox = new ChoiceBox<>();
        for (Integer season : allSeasons)
            seasonChoiceBox.getItems().add(season);
        seasonChoiceBox.setValue(seasonChoiceBox.getItems().get(0)); // najnowszy sezon

    	
    	
        ObservableList<WindsurfingEvent> eventsObservableList = EventsInSeason.getEventsObservableList(dbConnection, seasonChoiceBox.getValue());
        TableView<WindsurfingEvent> eventsTableView = EventsInSeason.createEventsInSeasonTableView(eventsObservableList);

        Label chooseEventLabel = new Label("Wybierz zawody do usunięcia:");
        chooseEventLabel.setPadding(new Insets(5,5,5,5));
        Label errorLabel = new Label();

        Button returnButton = new Button("Powrót");
        returnButton.setPadding(new Insets(5,5,5,5));
        returnButton.setOnAction(e->adminWindow.setScene(parentScene));

        Button removeEventButton = new Button("Usuń");
        removeEventButton.setPadding(new Insets(5,5,5,5));
        removeEventButton.setOnAction(e->{
            WindsurfingEvent eventToRemove = eventsTableView.getSelectionModel().getSelectedItem();
            try{
                Admin admin = new Admin(dbConnection);
                admin.removeEvent(eventToRemove.getEventID());
                errorLabel.setTextFill(Color.GREEN);
                errorLabel.setText("Usunieto zawody: " + eventToRemove.getEventID()+ " " + eventToRemove.getName() + " " + eventToRemove.getSeason());
                eventsObservableList.removeAll(eventToRemove);
                eventsTableView.refresh();
            }catch (Exception ex){
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("Nie udało się wykonać operacji usuwania!");
            }
        });

        VBox layout = new VBox();
        layout.setSpacing(10);
        HBox buttonsLayout = new HBox();
        buttonsLayout.setSpacing(20);
        buttonsLayout.getChildren().addAll(returnButton,removeEventButton);

        layout.getChildren().addAll(chooseEventLabel,eventsTableView,errorLabel,buttonsLayout);

        Scene scene = new Scene(layout);
        adminWindow.setScene(scene);
    }
    
    private void showRemoveEventsResultsWindow(Scene parentScene) throws Exception{
   	 //Zainicjowanie listy sezonów
       ArrayList<Integer> allSeasons;
       try {
           allSeasons = Seasons.getAllSeasons(dbConnection);
       } catch (SQLException ex) {
           AlertBox.Display("Błąd połączenia!", ex.getMessage());
           return;
       }
       //ChoiceBox do wyboru sezonu
       ChoiceBox<Integer> seasonChoiceBox = new ChoiceBox<>();
       for (Integer season : allSeasons)
           seasonChoiceBox.getItems().add(season);
       seasonChoiceBox.setValue(seasonChoiceBox.getItems().get(0)); // najnowszy sezon

   	
   	
       ObservableList<WindsurfingEvent> eventsObservableList = EventsInSeason.getEventsObservableList(dbConnection, seasonChoiceBox.getValue());
       TableView<WindsurfingEvent> eventsTableView = EventsInSeason.createEventsInSeasonTableView(eventsObservableList);

       Label chooseEventLabel = new Label("Wybierz zawody, kt�rych wyniki usun��:");
       chooseEventLabel.setPadding(new Insets(5,5,5,5));
       Label errorLabel = new Label();

       Button returnButton = new Button("Powrót");
       returnButton.setPadding(new Insets(5,5,5,5));
       returnButton.setOnAction(e->adminWindow.setScene(parentScene));

       Button removeEventButton = new Button("Usuń");
       removeEventButton.setPadding(new Insets(5,5,5,5));
       removeEventButton.setOnAction(e->{
           WindsurfingEvent eventToRemove = eventsTableView.getSelectionModel().getSelectedItem();
           try{
               Admin admin = new Admin(dbConnection);
               admin.removeEventsResults(eventToRemove.getEventID());
               errorLabel.setTextFill(Color.GREEN);
               errorLabel.setText("Usunieto wyniki zawodow: " + eventToRemove.getEventID()+ " " + eventToRemove.getName() + " " + eventToRemove.getSeason());
               //eventsObservableList.removeAll(eventToRemove);
               eventsTableView.refresh();
           }catch (Exception ex){
               errorLabel.setTextFill(Color.RED);
               errorLabel.setText("Nie udało się wykonać operacji usuwania!");
           }
       });

       VBox layout = new VBox();
       layout.setSpacing(10);
       HBox buttonsLayout = new HBox();
       buttonsLayout.setSpacing(20);
       buttonsLayout.getChildren().addAll(returnButton,removeEventButton);

       layout.getChildren().addAll(chooseEventLabel,eventsTableView,errorLabel,buttonsLayout);

       Scene scene = new Scene(layout);
       adminWindow.setScene(scene);
   }
}
