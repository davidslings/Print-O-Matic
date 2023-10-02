package pojo;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;

public class OpeningHours {
    private String weekday;
    private LocalTime openingTime;
    private LocalTime closingTime;




    public OpeningHours(String weekday, LocalTime openingTime, LocalTime closingTime) {
        this.weekday = weekday;
        this.openingTime = openingTime;
        this.closingTime = closingTime;

    }

    public OpeningHours(OpeningHours source) {
        this.weekday = source.weekday;
        this.openingTime = source.openingTime;
        this.closingTime = source.closingTime;
    }


    public LocalTime getOpeningTime() {
        return this.openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return this.closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public String getWeekday() {
        return this.weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

}  
