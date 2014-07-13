package pl.edu.agh.mobilecodereviewer.model;

public class MergeableInfo {

    private String submitType;

    private boolean mergeable;

    public MergeableInfo(){}

    public static MergeableInfo valueOf(String submitType, boolean mergeable){
        MergeableInfo mergeableInfo = new MergeableInfo();
        mergeableInfo.setSubmitType(submitType);
        mergeableInfo.setMergeable(mergeable);
        return mergeableInfo;
    }

    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }

    public boolean isMergeable() {
        return mergeable;
    }

    public void setMergeable(boolean mergeable) {
        this.mergeable = mergeable;
    }

    @Override
    public String toString() {
        return "MergeableInfo{" +
                "submitType='" + submitType + '\'' +
                ", mergeable=" + mergeable +
                '}';
    }
}
