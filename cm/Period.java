package cm;

import java.util.ArrayList;

public class Period {

    private int startHour;
    private int endHour;

    public Period(int startH, int endH){
        this.startHour = startH;
        this.endHour = endH;

        if( startH < 0 || startH >24 && endH < 0 || endH >24) {
            throw new IllegalArgumentException("startHour and endHour have to between 0 and 24");
        }

        if( startH >= endH){
            throw new IllegalArgumentException("duration have to be positive");
        }

    }

    public int duration(){
        return this.endHour - this.endHour;
    }

    /**
     * This method is to calculate the number of hours in which two periods overlap
     * @param period is the period to check
     * @return the number od hours in which two periods overlap
     */
    public int hourOverlap(Period period){
        int hours = 0;
        for(int i = this.startHour; i < this.endHour; i++){
            if(i >= period.startHour && i <= period.endHour){
                hours++;
            }
        }
        return hours;
    }


    /**
     * this method is to calculate how many hours in the period included in the list
     * @param list a period list to check
     * @return the total hours in the period that is included in the list
     */
    public int hourCalculate(Period period, ArrayList<Period> list){
        int total = 0;
        int hours = 0;
        for(int i = 0; i < list.size(); i++){
            total = period.hourOverlap(list.get(i));
            hours = total + hours;
        }
        return hours;
    }

    public Boolean overlaps(Period period){
        return this.startHour < period.endHour && this.endHour > period.startHour;
    }
}