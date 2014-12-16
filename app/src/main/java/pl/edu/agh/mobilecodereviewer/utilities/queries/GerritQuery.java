package pl.edu.agh.mobilecodereviewer.utilities.queries;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;

import java.util.LinkedList;
import java.util.List;

public class GerritQuery {
    private final SearchOperator searchOperator;

    GerritQuery(SearchOperator searchOperator) {
        this.searchOperator = searchOperator;
    }

    public List<ChangeInfo> execute(List<ChangeInfo> listToQuery) {
        List<ChangeInfo> found = new LinkedList<ChangeInfo>();
        for (ChangeInfo changeInfo : listToQuery) {
            if ( searchOperator.match(changeInfo ) )
                found.add(changeInfo);
        }

        return found;
    }
}
