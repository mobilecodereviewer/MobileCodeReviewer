package pl.edu.agh.mobilecodereviewer.model;

/**
 * Created by lee on 2014-10-17.
 */
public enum ChangeStatus {
    ALL("ALL") , // NOT A REAL STATUS,used for seeking change info with any status
    NEW("NEW"), SUBMITTED("SUBMITTED"), MERGED("MERGED"),
    ABANDONED("ABANDONED"), DRAFT("DRAFT");


    private String statusName;
    private ChangeStatus(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return statusName;
    }

    public boolean matchStatus(ChangeStatus changeStatus) {
        if (this == ALL || changeStatus == ALL) {
            return true;
        }else return this == changeStatus;
    }

    public static ChangeStatus createStatusFromString(String statusName) {
        String upperCaseStatusName = statusName.toUpperCase();
        switch (upperCaseStatusName) {
            case "NEW":
                return ChangeStatus.NEW;
            case "SUBMITTED":
                return ChangeStatus.SUBMITTED;
            case "MERGED":
                return ChangeStatus.MERGED;
            case "ABANDONED":
                return ChangeStatus.ABANDONED;
            case "DRAFT":
                return ChangeStatus.DRAFT;
            case "ALL":
                return ChangeStatus.ALL;
            default:
                throw new IllegalArgumentException("statusName must be one of: ALL,NEW,SUBMITTED,MERGED,ABANDONED,DRAFT but was " + statusName);
        }
    }

}
