package pl.edu.agh.mobilecodereviewer.model;

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
                '}';
    }

}
