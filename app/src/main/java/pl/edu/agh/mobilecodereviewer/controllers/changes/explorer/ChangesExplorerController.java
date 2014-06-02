package pl.edu.agh.mobilecodereviewer.controllers.changes.explorer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import pl.edu.agh.mobilecodereviewer.dao.ChangeInfo;

@Singleton
public class ChangesExplorerController {

    @Inject
    public ChangesExplorerController(){
    }

    public List<ChangeInfo> getChangesList(){
        List<ChangeInfo> changes = new ArrayList<ChangeInfo>();
        ChangeInfo ch = new ChangeInfo();
        ch.setName("DatName");
        changes.add(ch);
        return changes;
    }

}
