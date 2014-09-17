package pl.edu.agh.mobilecodereviewer.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lee on 2014-09-10.
 */
public class DiffContentDTO {

    @SerializedName("a")
    private List<String> linesBeforeChange;

    @SerializedName("b")
    private List<String> linesAfterChange;

    @SerializedName("ab")
    private List<String> linesUnchanged;

    @SerializedName("skip")
    private int countOfSkippedCommonLines;

    public DiffContentDTO() {
    }

    public DiffContentDTO(List<String> linesBeforeChange, List<String> linesAfterChange,
                          List<String> linesUnchanged, int countOfSkippedCommonLines) {
        this.linesBeforeChange = linesBeforeChange;
        this.linesAfterChange = linesAfterChange;
        this.linesUnchanged = linesUnchanged;
        this.countOfSkippedCommonLines = countOfSkippedCommonLines;
    }

    public List<String> getLinesBeforeChange() {
        return linesBeforeChange;
    }

    public List<String> getLinesAfterChange() {
        return linesAfterChange;
    }

    public List<String> getLinesUnchanged() {
        return linesUnchanged;
    }

    public int getCountOfSkippedCommonLines() {
        return countOfSkippedCommonLines;
    }
}
