package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.view.api.ChangeMessagesTabView;

/**
 * Created by d00d171 on 2014-07-12.
 */
public interface ChangeMessagesTabController {

    void updateMessages(ChangeMessagesTabView view, String changeId);

}
