package pl.edu.agh.mobilecodereviewer.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Class represents data returned from gerrit instance
 * about information of the file
 */
public class FileInfoDTO {

    /**
     * Status of the file
     */
    private String status;

    /**
     * Lines inserted to file
     */
    @SerializedName("lines_inserted")
    private int linesInserted;

    /**
     * Lines deleted in file
     */
    @SerializedName("lines_deletd")
    private int linesDeleted;

    /**
     * Construct empty object
     */
    public FileInfoDTO() {
    }

    /**
     * Construct file info from status and number of lines inserted
     * @param status Status of the file
     * @param linesInserted Number of lines inserted
     */
    public FileInfoDTO(String status, int linesInserted) {
        this.status = status;
        this.linesInserted = linesInserted;
    }

    /**
     * Get status of the file
     * @return status of the file
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set status of the file
     * @param status new file status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get Number of lines inserted
     * @return number of lines inserted
     */
    public int getLinesInserted() {
        return linesInserted;
    }

    /**
     * Sets number of lines inserted
     * @param linesInserted number of lines inserted
     */
    public void setLinesInserted(int linesInserted) {
        this.linesInserted = linesInserted;
    }

    public int getLinesDeleted() {
        return linesDeleted;
    }

    public void setLinesDeleted(int linesDeleted) {
        this.linesDeleted = linesDeleted;
    }
}
