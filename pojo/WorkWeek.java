package pojo;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class WorkWeek {

    private final List<OpeningHours> openingTimes;

    public WorkWeek() {
        this.openingTimes = new ArrayList<>();
    }

    public List<OpeningHours> getOpeningTimes() {
        return openingTimes;
    }

    public OpeningHours getOpeningHours(OpeningHours source) {
        return new OpeningHours(source);
    }
    public int getCurrentDayOpeningTime(DayOfWeek dayOfWeek) {
        for (int i = 0; i < getOpeningTimes().size(); i++) {
            if (dayOfWeek.toString().equals(getOpeningTimes().get(i).getWeekday().toUpperCase())) {
                return getOpeningTimes().get(i).getOpeningTime().getHour();
            }
        }
        return 0;
    }
    public int getCurrentDayClosingTime(DayOfWeek dayOfWeek) {
        for (int i = 0; i < getOpeningTimes().size(); i++) {
            if (dayOfWeek.toString().equals(getOpeningTimes().get(i).getWeekday().toUpperCase())) {
                return (getOpeningTimes().get(i).getClosingTime().getHour());
            }
        }
        return 0;
    }

}


