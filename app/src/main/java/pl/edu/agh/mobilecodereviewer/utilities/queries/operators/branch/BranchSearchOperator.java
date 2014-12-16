package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.branch;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.utils.StringPatternMatcher;

public class BranchSearchOperator implements SearchOperator {
    private final StringPatternMatcher patternMatcher;

    public BranchSearchOperator(StringPatternMatcher patternMatcher) {
        this.patternMatcher = patternMatcher;
    }

    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.getBranch() == null) return false;
        return patternMatcher.match(changeInfo.getBranch());
    }
}
