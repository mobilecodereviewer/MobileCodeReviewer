package pl.edu.agh.mobilecodereviewer.dto;

import java.util.List;
import java.util.Map;

public class LabelInfoDTO {

    private List<ApprovalInfoDTO> all;

    private Map<Integer, String> values;

    public List<ApprovalInfoDTO> getAll() {
        return all;
    }

    public void setAll(List<ApprovalInfoDTO> all) {
        this.all = all;
    }

    public Map<Integer, String> getValues() {
        return values;
    }

    public void setValues(Map<Integer, String> values) {
        this.values = values;
    }
}
