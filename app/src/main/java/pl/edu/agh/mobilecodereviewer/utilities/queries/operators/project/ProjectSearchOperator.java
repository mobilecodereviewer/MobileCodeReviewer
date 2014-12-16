package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.project;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.utils.StringPatternMatcher;

public class ProjectSearchOperator implements SearchOperator {
    private final StringPatternMatcher patternMatcher;

    public ProjectSearchOperator(StringPatternMatcher patternMatcher) {
        this.patternMatcher = patternMatcher;
    }

    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.getProject() == null) return false;
        return patternMatcher.match(changeInfo.getProject());
    }
}
