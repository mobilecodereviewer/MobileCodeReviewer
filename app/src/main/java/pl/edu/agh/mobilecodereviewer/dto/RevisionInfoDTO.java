package pl.edu.agh.mobilecodereviewer.dto;


import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Class represents data from gerrit instance
 * about information of the revision
 */
public class RevisionInfoDTO {

    /**
     * Identifier of the revision
     */
    @SerializedName("_number")
    private String number;

    /**
     * Mapping between file name and file information
     * model {@link pl.edu.agh.mobilecodereviewer.dto.FileInfoDTO}
     */
    private Map<String, FileInfoDTO> files;

    private CommitInfoDTO commit;

    /**
     * Construct empty object
     */
    public RevisionInfoDTO() {
    }

    /**
     * Construct information about revision from parameters
     * @param number identifier of the revision
     * @param files mapping between file name and file information model \
     *      {@link pl.edu.agh.mobilecodereviewer.dto.FileInfoDTO}
     */
    public RevisionInfoDTO(String number, Map<String, FileInfoDTO> files) {
        this.number = number;
        this.files = files;
    }

    /**
     * Gets identifier of the revision
     * @return identifier of the revision
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets identifier of the revision
     * @param number Identifier of the revision
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Mapping between file path and file information model {@link pl.edu.agh.mobilecodereviewer.dto.FileInfoDTO}
     * @return
     */
    public Map<String, FileInfoDTO> getFiles() {
        return files;
    }

    /**
     * Sets mapping between file name and file information model {@link pl.edu.agh.mobilecodereviewer.dto.FileInfoDTO}
     * @param files
     */
    public void setFiles(Map<String, FileInfoDTO> files) {
        this.files = files;
    }

    public CommitInfoDTO getCommit() {
        return commit;
    }

    public void setCommit(CommitInfoDTO commit) {
        this.commit = commit;
    }
}
