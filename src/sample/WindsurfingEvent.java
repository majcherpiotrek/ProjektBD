package sample;

/**
 * Created by piotrek on 12.01.17.
 */
public class WindsurfingEvent {
    private Integer eventID;
    private String name;
    private String location;
    private String date;
    private Integer prizeMoney;
    private Integer season;

    WindsurfingEvent(Integer eventID, String name, String location, String date, Integer prizeMoney, Integer season){
        this.eventID = eventID;
        this.name = name;
        this.location = location;
        this.date = date;
        this.prizeMoney = prizeMoney;
        this.season = season;
    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(Integer prizeMoney) {
        this.prizeMoney = prizeMoney;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }
}
