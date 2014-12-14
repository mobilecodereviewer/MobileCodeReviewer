package pl.edu.agh.mobilecodereviewer.model.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.edu.agh.mobilecodereviewer.dto.ApprovalInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.LabelInfoDTO;
import pl.edu.agh.mobilecodereviewer.model.ApprovalInfo;
import pl.edu.agh.mobilecodereviewer.model.LabelInfo;

public final class LabelInfoHelper {

    public static List<LabelInfo> labelsFromMap(Map<String, LabelInfoDTO> labelInfoDTOMap, boolean extractValuesSetInfo){
        List<LabelInfo> labelInfos = new ArrayList<LabelInfo>();

        for(String labelName : labelInfoDTOMap.keySet()){
            labelInfos.add(LabelInfoHelper.labelInfoFromDTO(labelName, labelInfoDTOMap.get(labelName), extractValuesSetInfo));
        }

        return labelInfos;
    }


    public static LabelInfo labelInfoFromDTO(String labelName, LabelInfoDTO labelInfoDTO, boolean extractValuesSetInfo){
        List<ApprovalInfo> approvalInfoList = new ArrayList<ApprovalInfo>();

        if(labelInfoDTO.getAll() != null) {
            for (ApprovalInfoDTO approvalInfoDTO : labelInfoDTO.getAll()) {
                approvalInfoList.add(approvalInfoFromDTO(approvalInfoDTO, labelInfoDTO.getValues().keySet()));
            }
        }

        LabelInfo labelInfo = new LabelInfo(labelName, approvalInfoList, labelInfoDTO.getValues());
        labelInfo.setAbbreviation(labelName.replaceAll("([^A-Z])", ""));

        if(extractValuesSetInfo) {
            extractValuesSetInfo(labelInfo);
        }

        return labelInfo;
    }

    private static void extractValuesSetInfo(LabelInfo labelInfo){
        boolean isMax = false;
        boolean isMin = false;
        Integer minValue = null;

        for(ApprovalInfo approvalInfo : labelInfo.getAll()){
            if(minValue == null || approvalInfo.getValue().compareTo(minValue) < 0){
                minValue = approvalInfo.getValue();
                isMax = approvalInfo.isMaxValueForLabel();
                isMin = approvalInfo.isMinValueForLabel();
            }
        }

        labelInfo.setHasMaxLabelValueApproval(isMax);
        labelInfo.setHasMinLabelValueApproval(isMin);
        labelInfo.setMinApprovalValueSet(minValue);
    }

    private static ApprovalInfo approvalInfoFromDTO(ApprovalInfoDTO approvalInfoDTO, Set<Integer> values){
        ApprovalInfo approvalInfo = new ApprovalInfo(approvalInfoDTO.getAccountId(), approvalInfoDTO.getName(), approvalInfoDTO.getEmail(), approvalInfoDTO.getUsername(), approvalInfoDTO.getValue(), approvalInfoDTO.getDate());

        List<Integer> sortedValues = new ArrayList<Integer>(values);
        Collections.sort(sortedValues);

        if (approvalInfo.getValue() != null && sortedValues.size() > 0) {

            if (approvalInfo.getValue().equals(sortedValues.get(0))) {
                approvalInfo.setMinValueForLabel(true);
            } else if (approvalInfo.getValue().equals(sortedValues.get(sortedValues.size() - 1))) {
                approvalInfo.setMaxValueForLabel(true);
            }

        }

        return approvalInfo;
    }

}
