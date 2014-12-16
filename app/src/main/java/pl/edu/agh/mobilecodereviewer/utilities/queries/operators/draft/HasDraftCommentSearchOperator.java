package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.draft;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;


public class HasDraftCommentSearchOperator implements SearchOperator {
    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.getHasDraftComments() == null) return false;
        return changeInfo.getHasDraftComments();
    }
}
