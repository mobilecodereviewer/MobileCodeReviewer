package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.view.api.ChangeInfoTabView;

/**
 * Created by d00d171 on 2014-07-09.
 */
public interface ChangeInfoTabController {

    void updateInfo(ChangeInfoTabView view, String changeId);
}
