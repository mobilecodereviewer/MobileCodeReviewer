package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.before;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;

import java.util.Date;

/**
 * Created by lee on 2014-11-01.
 */
public class BeforeSearchOperator implements SearchOperator{
    private final Date date;

    public BeforeSearchOperator(Date date) {
        this.date = date;
    }

    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.getUpdated() == null ) return false;
        return changeInfo.getUpdated().before(date);
    }
}
