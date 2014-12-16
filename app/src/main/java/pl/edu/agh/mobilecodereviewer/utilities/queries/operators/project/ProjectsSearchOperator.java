package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.project;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;

/**
 * Created by lee on 2014-11-01.
 */
public class ProjectsSearchOperator implements SearchOperator {
    private final String value;

    public ProjectsSearchOperator(String value) {
        this.value = value;
    }

    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.getProject() == null ) return false;
        return changeInfo.getProject().startsWith(value);
    }
}
