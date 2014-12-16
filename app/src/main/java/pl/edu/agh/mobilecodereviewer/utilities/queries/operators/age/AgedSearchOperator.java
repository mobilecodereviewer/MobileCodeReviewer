package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.age;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.age.parser.AgeType;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.age.parser.AgeValue;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by lee on 2014-10-31.
 */
public class AgedSearchOperator implements SearchOperator {
    private final Date changedSince;

    public AgedSearchOperator(AgeValue ageValue) {
        Calendar calendar = Calendar.getInstance();
        int value = ageValue.value();
        if (ageValue.type() == AgeType.SECONDS) {
            calendar.add(Calendar.SECOND, -value);
        } else if (ageValue.type() == AgeType.MINUTES) {
            calendar.add(Calendar.MINUTE, -value);
        } else if (ageValue.type() == AgeType.HOURS) {
            calendar.add(Calendar.HOUR, -value);
        } else if (ageValue.type() == AgeType.DAYS) {
            calendar.add(Calendar.DAY_OF_YEAR, -value);
        } else if (ageValue.type() == AgeType.WEEKS) {
            calendar.add(Calendar.WEEK_OF_YEAR, -value);
        } else if (ageValue.type() == AgeType.YEARS) {
            calendar.add(Calendar.YEAR, -value);
        }
        this.changedSince = calendar.getTime();
    }

    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.getUpdated() == null) return false;
        return changeInfo.getUpdated().after( changedSince );
    }
}
