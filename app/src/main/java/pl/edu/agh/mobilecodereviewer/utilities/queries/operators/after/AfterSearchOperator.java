package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.after;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;

import java.util.Date;

public class AfterSearchOperator implements SearchOperator {
    private final Date date;

    public AfterSearchOperator(Date date) {
        this.date = date;
    }

    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.getUpdated() == null) return false;
        return changeInfo.getUpdated().after(date);
    }
}
