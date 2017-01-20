package sample;

/**
 * Created by piotrek on 11.01.17.
 */
public class Sailor {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sailor sailor = (Sailor) o;

        if (placeInTable != null ? !placeInTable.equals(sailor.placeInTable) : sailor.placeInTable != null)
            return false;
        if (sailNumber != null ? !sailNumber.equals(sailor.sailNumber) : sailor.sailNumber != null) return false;
        if (name != null ? !name.equals(sailor.name) : sailor.name != null) return false;
        if (surname != null ? !surname.equals(sailor.surname) : sailor.surname != null) return false;
        if (sex != null ? !sex.equals(sailor.sex) : sailor.sex != null) return false;
        if (nationality != null ? !nationality.equals(sailor.nationality) : sailor.nationality != null) return false;
        if (boardBrand != null ? !boardBrand.equals(sailor.boardBrand) : sailor.boardBrand != null) return false;
        if (sailBrand != null ? !sailBrand.equals(sailor.sailBrand) : sailor.sailBrand != null) return false;
        if (sponsors != null ? !sponsors.equals(sailor.sponsors) : sailor.sponsors != null) return false;
        return seasonPoints != null ? seasonPoints.equals(sailor.seasonPoints) : sailor.seasonPoints == null;
    }

    @Override
    public int hashCode() {
        int result = placeInTable != null ? placeInTable.hashCode() : 0;
        result = 31 * result + (sailNumber != null ? sailNumber.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        result = 31 * result + (boardBrand != null ? boardBrand.hashCode() : 0);
        result = 31 * result + (sailBrand != null ? sailBrand.hashCode() : 0);
        result = 31 * result + (sponsors != null ? sponsors.hashCode() : 0);
        result = 31 * result + (seasonPoints != null ? seasonPoints.hashCode() : 0);
        return result;
    }

    private Integer placeInTable;
    private String sailNumber;
    private String name;
    private String surname;
    private String sex;
    private String nationality;
    private String boardBrand;
    private String sailBrand;
    private String sponsors;
    private Integer seasonPoints;



    Sailor(String sailNumber, String name,
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

        this.placeInTable = null;
        this.seasonPoints = null;
    }

    public Integer getPlaceInTable() {
        return placeInTable;
    }

    public void setPlaceInTable(Integer placeInTable) {
        this.placeInTable = placeInTable;
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

    public Integer getSeasonPoints() {
        return seasonPoints;
    }

    public void setSeasonPoints(Integer seasonPoints) {
        this.seasonPoints = seasonPoints;
    }
}
