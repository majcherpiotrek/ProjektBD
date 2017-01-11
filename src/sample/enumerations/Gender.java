package sample.enumerations;

/**
 * Created by piotrek on 11.01.17.
 */
public enum Gender {
    MALE("Mężczyźni"),
    FEMALE("Kobiety");

    private String gender;

    Gender(String gender){
        this.gender = gender;
    }

    @Override
    public String toString(){
        return this.gender;
    }
}
