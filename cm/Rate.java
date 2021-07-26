package cm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Rate {
    private CarParkKind kind;
    private BigDecimal hourlyNormalRate;
    private BigDecimal hourlyReducedRate;
    private ArrayList <Period> reducedPeriods = new ArrayList<Period>();
    private ArrayList <Period> normalPeriods;

    public Rate(CarParkKind kind, BigDecimal normalRate, BigDecimal reducedRate, ArrayList <Period> reducedPeriods,ArrayList <Period> normalPeriods){
        this.kind = kind;
        this.hourlyNormalRate = normalRate;
        this.hourlyReducedRate = reducedRate;
        this.reducedPeriods = reducedPeriods;
        this.normalPeriods = normalPeriods;

        if (normalPeriods == null || reducedPeriods == null){
            throw new IllegalArgumentException("normalPeriods and reducedPeriods cannot be null");
        }

        if (normalRate == null || reducedRate == null){
            throw new IllegalArgumentException("normalRate and reducedRate cannot be null");
        }

        // num.compareTo(BigDecimal.ZERO)
        if(normalRate.compareTo(BigDecimal.ZERO) < 0 || reducedRate.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("the normalRate and reducedRate have to be greater or equal to 0");
        }

        // a.compareTo(b)
        if(normalRate.compareTo(reducedRate) < 0){
            throw new IllegalArgumentException("the normalRate have to be greater or equal to  than the reducedRate");
        }

        if(checkValidPeriod(reducedPeriods) == true || checkValidPeriod(normalPeriods) == true){
            throw new IllegalArgumentException("the normalPeriods or reducedPeriods is invalid");
        }

        if(checkValidPeriods(normalPeriods, reducedPeriods) == true){
            throw new IllegalArgumentException("the normalPeriods overlap reducedPeriods");
        }



    }


    /**
     * This method is to check if two different lists overlap
     * @param period1 is one of the periods to check
     * @param period2 is another period to check
     * @return true if two list overlap
     */
    public Boolean checkValidPeriods(ArrayList <Period> period1, ArrayList <Period> period2 ){
        Boolean flag = false;
        for(int i = 0; i < period1.size(); i++){
            flag = checkOverlap(period1.get(i), period2);
            if(flag == true){
                break;
            }
        }
        return flag;
    }

    /**
     * This method is to check whether the periods included in the list overlap
     * @param list is the period list to check
     * @return true if the periods included in the list overlap
     */
    public boolean checkValidPeriod(ArrayList<Period> list){
        Boolean flag = false;
        if(list.size() >= 2) {
            for (int i = 0; i < list.size(); i++) {
                List<Period> sublist = ((ArrayList<Period>) list).subList(i + 1, list.size());
                flag = checkOverlap(list.get(i), sublist);
                if(flag == true){
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * This method is to check whether the period overlaps with the periods in the existing collection
     * @param period is the period checked
     * @param list the existing collection of periods
     * @return true if the period overlaps with the existing collection
     */
    public Boolean checkOverlap(Period period,List<Period> list){
        Boolean flag = false;  //the overlap marks
        for(int i = 0; i < list.size(); i++){
            flag = period.overlaps(list.get(i));
            if (flag == true){
                break;
            }
        }
        return flag;
    }


    public BigDecimal calculate(Period periodStay){
        int normalHour = periodStay.hourCalculate(periodStay,normalPeriods);
        int reducedHour = periodStay.hourCalculate(periodStay,reducedPeriods);
        return (this.hourlyNormalRate.multiply(BigDecimal.valueOf(normalHour))).add(this.hourlyReducedRate.multiply(BigDecimal.valueOf(reducedHour)));
    }
}