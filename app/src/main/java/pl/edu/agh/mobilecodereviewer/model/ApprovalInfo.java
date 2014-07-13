package pl.edu.agh.mobilecodereviewer.model;

public class ApprovalInfo {

    private String approverName;

    private Integer value;

    private String date;

    private boolean maxValueForLabel;

    private boolean minValueForLabel;

    public ApprovalInfo(String approverName, Integer value, String date){
        this.approverName = approverName;
        this.value = value;
        this.date = date;
        this.maxValueForLabel = false;
        this.minValueForLabel = false;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isMaxValueForLabel() {
        return maxValueForLabel;
    }

    public void setMaxValueForLabel(boolean maxValueForLabel) {
        this.maxValueForLabel = maxValueForLabel;
    }

    public boolean isMinValueForLabel() {
        return minValueForLabel;
    }

    public void setMinValueForLabel(boolean minValueForLabel) {
        this.minValueForLabel = minValueForLabel;
    }

    @Override
    public String toString() {
        return "ApprovalInfo{" +
                "approverName='" + approverName + '\'' +
                ", value=" + value +
                ", date='" + date + '\'' +
                ", maxValueForLabel=" + maxValueForLabel +
                ", minValueForLabel=" + minValueForLabel +
                '}';
    }
}
