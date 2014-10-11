package pl.edu.agh.mobilecodereviewer.dao.gerrit;


import javax.inject.Singleton;

import pl.edu.agh.mobilecodereviewer.dao.api.AccountDAO;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.RestApi;
import pl.edu.agh.mobilecodereviewer.dto.AccountInfoDTO;
import pl.edu.agh.mobilecodereviewer.model.AccountInfo;

@Singleton
public class AccountDAOImpl implements AccountDAO{

    private RestApi restApi;

    @Override
    public AccountInfo getAccountInfo() {
        AccountInfoDTO accountInfoDTO = restApi.getAccountInfo();
        return new AccountInfo(accountInfoDTO.getAccountId(), accountInfoDTO.getName(), accountInfoDTO.getEmail(), accountInfoDTO.getUsername());

    }

    @Override
    public void initialize(RestApi restApi) {
        this.restApi = restApi;
    }
}
