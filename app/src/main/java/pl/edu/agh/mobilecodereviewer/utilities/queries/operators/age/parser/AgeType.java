package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.age.parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public enum AgeType {
    SECONDS, HOURS, MINUTES, DAYS, WEEKS, MONTHS, YEARS;


    private final static Set<String> SECONDS_FORMS = new HashSet<>(Arrays.asList("s","sec","second","seconds") );
    private final static Set<String> MINUTES_FORMS = new HashSet<>(Arrays.asList("m","min","minute","minutes") );
    private final static Set<String> HOURS_FORMS = new HashSet<>(Arrays.asList("h","hr","hour","hours") );
    private final static Set<String> DAYS_FORMS = new HashSet<>(Arrays.asList("d","day","days") );
    private final static Set<String> WEEKS_FORMS = new HashSet<>(Arrays.asList("w","week","weeks") );
    private final static Set<String> MONTHS_FORMS = new HashSet<>(Arrays.asList("mon","month","months") );
    private final static Set<String> YEARS_FORMS = new HashSet<>(Arrays.asList("y","year","years") );

    public static AgeType createTypeFromString(String type) {
        if (SECONDS_FORMS.contains(type)) {
            return SECONDS;
        } else if (MINUTES_FORMS.contains(type)){
            return MINUTES;
        } else if (HOURS_FORMS.contains(type)) {
            return HOURS;
        } else if (DAYS_FORMS.contains(type)) {
            return DAYS;
        } else if (WEEKS_FORMS.contains(type)) {
            return WEEKS;
        } else if (MONTHS_FORMS.contains(type)) {
            return MONTHS;
        } else if (YEARS_FORMS.contains(type)) {
            return YEARS;
        } else throw new IllegalArgumentException("Unrecognized name of type");
    }
}
