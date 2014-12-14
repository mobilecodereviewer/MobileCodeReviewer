package pl.edu.agh.mobilecodereviewer.model;

import java.util.List;

/**
 * Model represents brief information about change.
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public class ChangeInfo {

    /**
     * id of change
     */
    private String id;

    /**
     * changeId of change
     */
    private String changeId;

    /**
     * subject of change
     */
    private String subject;

    private ChangeStatus status;

    private String ownerName;

    private String project;

    private String branch;

    private String updated;

    private String created;

    private Integer size;

    private String currentRevision;

    private Integer number;

    private List<LabelInfo> labels;

    public void setStatus(ChangeStatus status) {
        this.status = status;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * No-argument constructor ,doesnt initialize any fields etc.
     */
    public ChangeInfo() {
    }

    /**
     * Construct change with given id
     *
     * @param id
     */
    public ChangeInfo(String id) {
        this.id = id;
    }

    /**
     * Construct {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo} from given id,
     * and set changeId, and subject to given values.
     * If any parameter is null initialize it as zero character string
     *
     * @param id       id of change
     * @param changeId change ID of change
     * @param subject  subject of change
     * @return Constructed {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo}
     * with {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo#subject} and
     * {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo#changeId} set.
     */
    public static ChangeInfo valueOf(String id, String changeId, String subject) {
        ChangeInfo changeInfo = new ChangeInfo(String.valueOf(id));
        changeInfo.setChangeId(changeId);
        changeInfo.setSubject(subject);
        return changeInfo;
    }

    /**
     * Getter for {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo#id}
     *
     * @return id of change
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo#id}
     *
     * @param id id of change
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo#changeId}
     *
     * @return change id of change
     */
    public String getChangeId() {
        return changeId;
    }

    /**
     * Setter for {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo#changeId}
     *
     * @param changeId change Id to be set for change
     */
    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }

    /**
     * Getter for {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo#subject}
     *
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Setter for {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo#subject}
     *
     * @param subject subject of change
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ChangeStatus getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = ChangeStatus.createStatusFromString(status);
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCurrentRevision() {
        return currentRevision;
    }

    public void setCurrentRevision(String currentRevision) {
        this.currentRevision = currentRevision;
    }

    public List<LabelInfo> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelInfo> labels) {
        this.labels = labels;
    }

    /**
     * Return textual representation of {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo}
     *
     * @return textual representation of current {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo}
     * instance
     */
    @Override
    public String toString() {
        return "ChangeInfo{" +
                "id='" + id + '\'' +
                ", changeId='" + changeId + '\'' +
                ", subject='" + subject + '\'' +
                ", status=" + status +
                ", ownerName='" + ownerName + '\'' +
                ", project='" + project + '\'' +
                ", branch='" + branch + '\'' +
                ", updated='" + updated + '\'' +
                ", created='" + created + '\'' +
                ", size=" + size +
                ", currentRevision='" + currentRevision + '\'' +
                ", number=" + number +
                ", labels=" + labels +
                '}';
    }

}
