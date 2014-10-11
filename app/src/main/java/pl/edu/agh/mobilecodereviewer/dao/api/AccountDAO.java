package pl.edu.agh.mobilecodereviewer.dao.api;

import pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.RestApi;
import pl.edu.agh.mobilecodereviewer.model.AccountInfo;

public interface AccountDAO {

    AccountInfo getAccountInfo();

    void initialize(RestApi restApi);

}
