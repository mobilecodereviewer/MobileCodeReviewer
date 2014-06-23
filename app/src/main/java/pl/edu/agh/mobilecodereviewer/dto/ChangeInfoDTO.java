package pl.edu.agh.mobilecodereviewer.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ChangeInfoDTO {

    private String id;

    @SerializedName("change_id")
    private String changeId;

    private String subject;

    @SerializedName("current_revision")
    private String currentRevision;

    private Map<String, RevisionInfoDTO> revisions;

    public String getCurrentRevision() {
        return currentRevision;
    }

    public void setCurrentRevision(String currentRevision) {
        this.currentRevision = currentRevision;
    }

    public Map<String, RevisionInfoDTO> getRevisions() {
        return revisions;
    }

    public void setRevisions(Map<String, RevisionInfoDTO> revisions) {
        this.revisions = revisions;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getChangeId() {
        return changeId;
    }

    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
