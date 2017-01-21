package sample;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by piotrek on 12.01.17.
 */
public class Admin {
    private Connection connection;
    private Statement statement;

    public Admin(Connection dbConnection){
        connection = dbConnection;
    }

    public void addSailor(Integer sailNumber, String name,
                          String surname, String sex,
                          String nationality, String boardBrand,
                          String sailBrand, String sponsors) throws SQLException{

        statement = connection.createStatement();
        String update = "";
        update += "INSERT INTO zawodnicy(nr_rejestracyjny, imie, nazwisko," +
                "plec, narodowosc, marka_desek, marka_zagli, sponsorzy) values (\'";
        update += sailNumber + "\',";
        update += "\'"+name+"\',";
        update += "\'"+surname+"\',";
        update += "\'"+sex+"\',";
        update += "\'"+nationality+"\',";
        update += "\'"+boardBrand+"\',";
        update += "\'"+sailBrand+"\',";
        update += "\'"+sponsors+"\');";

        statement.executeUpdate(update);
        System.out.println("Update successfull");
    }

    public void removeSailor(String sailNumber) throws SQLException {
        statement = connection.createStatement();
        String update = "DELETE FROM zawodnicy WHERE nr_rejestracyjny=" + sailNumber + ";";

        statement.executeUpdate(update);
    }

    public void addAdmin(String login, String password) throws SQLException {
        statement = connection.createStatement();
        String update = "INSERT INTO administratorzy(login,haslo) values(\""+login+"\",\""+password+"\");";
        statement.executeUpdate(update);
        System.out.println("Update successfull");

    }

    public void removeAdmin(String login, String password) throws SQLException {
        statement = connection.createStatement();
        String update = "DELETE FROM administratorzy WHERE login=\""+login+"\" AND haslo=\""+password+"\";";
        statement.executeUpdate(update);
        System.out.println("Update successfull");
    }

    public void addEvent(Integer eventID, String eventName, String eventLocation, String date, Integer prizeMoney, Integer season) throws SQLException {
        statement = connection.createStatement();
        String update = "";
        update += "INSERT INTO zawody(id_zawody, nazwa_zawodow, miejsce_rozegrania," +
                "data, pula_nagrod, sezon_sezon) values (\'";
        update += eventID + "\',";
        update += "\'"+eventName+"\',";
        update += "\'"+eventLocation+"\',";
        update += "\'"+date+"\',";
        update += "\'"+prizeMoney+"\',";
        update += "\'"+season+"\');";

        statement.executeUpdate(update);
        System.out.println("Update successfull");
    }
}
