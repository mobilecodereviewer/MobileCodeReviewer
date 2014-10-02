package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.view.api.ChangeDetailsView;

/**
 * Created by d00d171 on 2014-10-01.
 */
public interface ChangeDetailsController {

    void updateSetReviewPopup(ChangeDetailsView view, String changeId);

}
