package pl.edu.agh.mobilecodereviewer.model;

import java.util.List;
import java.util.Date;

import pl.edu.agh.mobilecodereviewer.model.utilities.ChangeInfoHelper;

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

    private Date updated;

    private String created;

    private Integer size;

    private String currentRevision;

    private Integer number;


    private List<LabelInfo> labels;

    private Integer insertions;

    private Integer deletions;

    private Boolean starred = false;

    private Boolean has_draft_comments = false;

    private Boolean mergeable = false;

    private Boolean reviewed = false;

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

    public void setStatus(ChangeStatus status) {
        this.status = status;
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

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(String updated) {
        this.updated = ChangeInfoHelper.gerritTimeToDate(updated);
    }

    public void setUpdated(Date updateDate) {
        this.updated = updateDate;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getInsertions() {
        return insertions;
    }

    public void setInsertions(Integer insertions) {
        this.insertions = insertions;
    }

    public Integer getDeletions() {
        return deletions;
    }

    public void setDeletions(Integer deletions) {
        this.deletions = deletions;
    }

    public Boolean hasStarred() {
        return starred;
    }

    public void setStarred(Boolean starred) {
        this.starred = starred;
    }

    public Boolean getHasDraftComments() {
        return has_draft_comments;
    }

    public void setHasDraftComments(Boolean has_draft_comments) {
        this.has_draft_comments = has_draft_comments;
    }

    public Boolean getMergeable() {
        return mergeable;
    }

    public void setMergeable(Boolean mergeable) {
        this.mergeable = mergeable;
    }

    public Boolean getReviewed() {
        return reviewed;
    }

    public void setReviewed(Boolean reviewed) {
        this.reviewed = reviewed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChangeInfo that = (ChangeInfo) o;

        if (branch != null ? !branch.equals(that.branch) : that.branch != null) return false;
        if (changeId != null ? !changeId.equals(that.changeId) : that.changeId != null)
            return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (currentRevision != null ? !currentRevision.equals(that.currentRevision) : that.currentRevision != null)
            return false;
        if (deletions != null ? !deletions.equals(that.deletions) : that.deletions != null)
            return false;
        if (has_draft_comments != null ? !has_draft_comments.equals(that.has_draft_comments) : that.has_draft_comments != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (insertions != null ? !insertions.equals(that.insertions) : that.insertions != null)
            return false;
        if (labels != null ? !labels.equals(that.labels) : that.labels != null) return false;
        if (mergeable != null ? !mergeable.equals(that.mergeable) : that.mergeable != null)
            return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (ownerName != null ? !ownerName.equals(that.ownerName) : that.ownerName != null)
            return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;
        if (reviewed != null ? !reviewed.equals(that.reviewed) : that.reviewed != null)
            return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        if (starred != null ? !starred.equals(that.starred) : that.starred != null) return false;
        if (status != that.status) return false;
        if (subject != null ? !subject.equals(that.subject) : that.subject != null) return false;
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (changeId != null ? changeId.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (ownerName != null ? ownerName.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (branch != null ? branch.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (currentRevision != null ? currentRevision.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (labels != null ? labels.hashCode() : 0);
        result = 31 * result + (insertions != null ? insertions.hashCode() : 0);
        result = 31 * result + (deletions != null ? deletions.hashCode() : 0);
        result = 31 * result + (starred != null ? starred.hashCode() : 0);
        result = 31 * result + (has_draft_comments != null ? has_draft_comments.hashCode() : 0);
        result = 31 * result + (mergeable != null ? mergeable.hashCode() : 0);
        result = 31 * result + (reviewed != null ? reviewed.hashCode() : 0);
        return result;
    }

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
                ", updated=" + updated +
                ", created='" + created + '\'' +
                ", size=" + size +
                ", currentRevision='" + currentRevision + '\'' +
                ", number=" + number +
                ", labels=" + labels +
                ", insertions=" + insertions +
                ", deletions=" + deletions +
                ", starred=" + starred +
                ", has_draft_comments=" + has_draft_comments +
                ", mergeable=" + mergeable +
                ", reviewed=" + reviewed +
                '}';
    }
}
