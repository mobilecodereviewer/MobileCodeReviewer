package pl.edu.agh.mobilecodereviewer.model.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import pl.edu.agh.mobilecodereviewer.dto.ApprovalInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.LabelInfoDTO;
import pl.edu.agh.mobilecodereviewer.model.ApprovalInfo;
import pl.edu.agh.mobilecodereviewer.model.LabelInfo;

public final class LabelInfoHelper {

    public static LabelInfo createLabelInfoFromDTO(String labelName, LabelInfoDTO labelInfoDTO) {
        List<ApprovalInfo> approvalInfoList = new ArrayList<ApprovalInfo>();

        if (labelInfoDTO.getAll() != null) {
            for (ApprovalInfoDTO approvalInfoDTO : labelInfoDTO.getAll()) {
                 approvalInfoList.add(createApprovalInfoFromDTO(approvalInfoDTO, labelInfoDTO.getValues().keySet()));
            }
        }
        return new LabelInfo(labelName, approvalInfoList, labelInfoDTO.getValues());
    }

    private static ApprovalInfo createApprovalInfoFromDTO(ApprovalInfoDTO approvalInfoDTO, Set<Integer> values) {

        if (approvalInfoDTO.getValue() == null) {
            approvalInfoDTO.setValue(0);
        }

        ApprovalInfo approvalInfo = new ApprovalInfo(approvalInfoDTO.getName(), approvalInfoDTO.getValue(), approvalInfoDTO.getDate());

        List<Integer> sortedValues = new ArrayList<Integer>(values);
        Collections.sort(sortedValues);

        if (approvalInfo.getValue().equals(sortedValues.get(0))) {
            approvalInfo.setMinValueForLabel(true);
        } else if (approvalInfo.getValue().equals(sortedValues.get(sortedValues.size() - 1))) {
            approvalInfo.setMaxValueForLabel(true);
        }

        return approvalInfo;
    }

}
