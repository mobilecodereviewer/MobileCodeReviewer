package pl.edu.agh.mobilecodereviewer.dto;

public class ApprovalInfoDTO extends AccountInfoDTO{

    private Integer value;

    private String date;

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
}
