package pl.edu.agh.mobilecodereviewer.model;

/**
 * Model represents brief information about File.
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public class FileInfo {

    private String changeId;

    private String revisionId;

    /**
     * Represent name of the file
     */
    private String fileName;

    /**
     * No-argument constructor ,doesnt initialize any fields etc.
     */
    public FileInfo() {
    }

    /**
     * Construct object with given fileName
     * @param fileName {@link pl.edu.agh.mobilecodereviewer.model.FileInfo#fileName}
     */
    public FileInfo(String fileName) {
        this.fileName = fileName;
    }

    public FileInfo(String changeId, String revisionId, String fileName){
        this.changeId = changeId;
        this.revisionId = revisionId;
        this.fileName = fileName;
    }

    /**
     * Construct object from name of the file
     * @param fileName {@link pl.edu.agh.mobilecodereviewer.model.FileInfo#fileName}
     * @return Constructed {@link pl.edu.agh.mobilecodereviewer.model.FileInfo}
     */
    public static FileInfo valueOf(String fileName) {
        return new FileInfo(String.valueOf(fileName));
    }

    public static FileInfo valueOf(String changeId, String revisionId, String fileName){
        return new FileInfo(String.valueOf(changeId), String.valueOf(revisionId), String.valueOf(fileName));
    }

    /**
     * Getter for {@link pl.edu.agh.mobilecodereviewer.model.FileInfo#fileName}
     * @return Name of the file
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Setter for {@link pl.edu.agh.mobilecodereviewer.model.FileInfo#fileName}
     * @param fileName Name of the file
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getChangeId() {
        return changeId;
    }

    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }

    public String getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(String revisionId) {
        this.revisionId = revisionId;
    }

    /**
     * Return textual representation of {@link pl.edu.agh.mobilecodereviewer.model.FileInfo}
     *
     * @return textual representation of current {@link pl.edu.agh.mobilecodereviewer.model.FileInfo}
     * instance
     */
    @Override
    public String toString() {
        return "FileInfo{" +
                "changeId='" + changeId + '\'' +
                ", revisionId='" + revisionId + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
