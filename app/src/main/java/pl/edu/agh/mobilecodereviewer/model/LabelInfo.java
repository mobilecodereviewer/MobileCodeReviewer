package pl.edu.agh.mobilecodereviewer.model;

import java.util.List;
import java.util.Map;

public class LabelInfo {

    String name;

    String abbreviation;

    List<ApprovalInfo> all;

    Map<Integer, String> values;

    boolean hasMaxLabelValueApproval;

    boolean hasMinLabelValueApproval;

    Integer minApprovalValueSet;

    public LabelInfo(String name, List<ApprovalInfo> all, Map<Integer, String> values){
        this.name = name;
        this.all = all;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ApprovalInfo> getAll() {
        return all;
    }

    public void setAll(List<ApprovalInfo> all) {
        this.all = all;
    }

    public Map<Integer, String> getValues() {
        return values;
    }

    public void setValues(Map<Integer, String> values) {
        this.values = values;
    }

    public boolean isHasMaxLabelValueApproval() {
        return hasMaxLabelValueApproval;
    }

    public void setHasMaxLabelValueApproval(boolean hasMaxLabelValueApproval) {
        this.hasMaxLabelValueApproval = hasMaxLabelValueApproval;
    }

    public boolean isHasMinLabelValueApproval() {
        return hasMinLabelValueApproval;
    }

    public void setHasMinLabelValueApproval(boolean hasMinLabelValueApproval) {
        this.hasMinLabelValueApproval = hasMinLabelValueApproval;
    }

    public Integer getMinApprovalValueSet() {
        return minApprovalValueSet;
    }

    public void setMinApprovalValueSet(Integer minApprovalValueSet) {
        this.minApprovalValueSet = minApprovalValueSet;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return "LabelInfo{" +
                "name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", all=" + all +
                ", values=" + values +
                ", hasMaxLabelValueApproval=" + hasMaxLabelValueApproval +
                ", hasMinLabelValueApproval=" + hasMinLabelValueApproval +
                ", minApprovalValueSet=" + minApprovalValueSet +
                '}';
    }

}
