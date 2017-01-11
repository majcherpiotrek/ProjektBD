package sample;

/**
 * Created by piotrek on 11.01.17.
 */
public class Sailor {
    private String sailNumber;
    private String name;
    private String surname;
    private String sex;
    private String nationality;
    private String boardBrand;
    private String sailBrand;
    private String sponsors;

    public Sailor(String sailNumber, String name,
                  String surname, String sex,
                  String nationality, String boardBrand,
                  String sailBrand, String sponsors){
        this.sailNumber = sailNumber;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.nationality = nationality;
        this.boardBrand = boardBrand;
        this.sailBrand = sailBrand;
        this.sponsors = sponsors;
    }
    public String getSailNumber() {
        return sailNumber;
    }

    public void setSailNumber(String sailNumber) {
        this.sailNumber = sailNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBoardBrand() {
        return boardBrand;
    }

    public void setBoardBrand(String boardBrand) {
        this.boardBrand = boardBrand;
    }

    public String getSailBrand() {
        return sailBrand;
    }

    public void setSailBrand(String sailBrand) {
        this.sailBrand = sailBrand;
    }

    public String getSponsors() {
        return sponsors;
    }

    public void setSponsors(String sponsors) {
        this.sponsors = sponsors;
    }
}
