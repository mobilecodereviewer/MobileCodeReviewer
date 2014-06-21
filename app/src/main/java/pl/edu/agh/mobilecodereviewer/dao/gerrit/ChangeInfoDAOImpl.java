package pl.edu.agh.mobilecodereviewer.dao.gerrit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.FileInfo;

public class ChangeInfoDAOImpl implements ChangeInfoDAO {

    private String mockedRestCall(String endpoint){
        if(endpoint.equals("/changes")){
            return "["+
            "{"+
                "\"id\": \"demo~master~Idaf5e098d70898b7119f6f4af5a6c13343d64b57\","+
                    "\"change_id\": \"Idaf5e098d70898b7119f6f4af5a6c13343d64b57\","+
                    "\"subject\": \"One change\""+
            "},"+
            "{"+
                "\"id\": \"demo~master~I09c8041b5867d5b33170316e2abc34b79bbb8501\","+
                    "\"change_id\": \"I09c8041b5867d5b33170316e2abc34b79bbb8501\","+
                   "\"subject\": \"Another change\""+
            "}"+
            "]";
        }
        if(endpoint.equals("/changes/1")){
            return "{"+
            "\"id\": \"demo~master~1\","+
            "\"change_id\": \"1\","+
            "\"subject\": \"Another change\""+
            "}";
        }
        if(endpoint.equals("/changes/2")){
            return "{"+
                    "\"id\": \"demo~master~2\","+
                    "\"change_id\": \"2\","+
                    "\"subject\": \"One change\""+
                    "}";
        }
        return null;
    };

    @Override
    public ChangeInfo getChangeInfoById(String id) {

        Properties prop = new Properties();
        prop.put(EndpointsHelper.EndpointProperties.CHANGE_ID, id);

        String endpoint = EndpointsHelper.createEndpointFromProperties(EndpointsHelper.Endpoints.CHANGE_DETAILS, prop);

        Gson gson = new Gson();
        ChangeInfoDTO changeInfoDTO = gson.fromJson(mockedRestCall(endpoint), ChangeInfoDTO.class);

        ChangeInfo changeInfo = ChangeInfo.valueOf(changeInfoDTO.getId(), changeInfoDTO.getChangeId(), changeInfoDTO.getSubject());

        return changeInfo;
    }

    @Override
    public List<ChangeInfo> getAllChangesInfo() {

        String endpoint = EndpointsHelper.createEndpointFromProperties(EndpointsHelper.Endpoints.CHANGES, new Properties());

        Gson gson = new Gson();
        List<ChangeInfoDTO> changeInfoDtos = gson.fromJson(mockedRestCall(endpoint), new TypeToken<List<ChangeInfoDTO>>(){}.getType());

        List<ChangeInfo> changeInfoModels = new ArrayList<ChangeInfo>();

        for(ChangeInfoDTO changeInfoDTO : changeInfoDtos){
            ChangeInfo changeInfoModel = ChangeInfo.valueOf(changeInfoDTO.getId(), changeInfoDTO.getChangeId(), changeInfoDTO.getSubject());
            changeInfoModels.add(changeInfoModel);
        }

        return changeInfoModels;
    }

    @Override
    public List<FileInfo> getModifiedFiles(String id) {
        return null;
    }

    public static void main(String[] args){

        ChangeInfoDAOImpl changeInfoDAO = new ChangeInfoDAOImpl();

        List<ChangeInfo> changeInfos = changeInfoDAO.getAllChangesInfo();
        for(ChangeInfo changeInfo : changeInfos){
            System.out.println(changeInfo);
        }

        System.out.println(changeInfoDAO.getChangeInfoById("1"));

        System.out.println(changeInfoDAO.getChangeInfoById("2"));

    };
}
