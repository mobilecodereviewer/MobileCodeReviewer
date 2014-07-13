package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.view.api.CommitMessageTabView;

public interface CommitMessageTabController {

    void updateMessage(CommitMessageTabView view, String changeId);

}
