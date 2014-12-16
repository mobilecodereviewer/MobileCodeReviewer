package pl.edu.agh.mobilecodereviewer.utilities.queries;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;

import java.util.List;

/**
 * Created by lee on 2014-10-31.
 */
public class NullGerritQuery extends GerritQuery {
    public NullGerritQuery(SearchOperator searchOperator) {
        super(searchOperator);
    }

    @Override
    public List<ChangeInfo> execute(List<ChangeInfo> listToQuery) {
        return listToQuery;
    }
}
