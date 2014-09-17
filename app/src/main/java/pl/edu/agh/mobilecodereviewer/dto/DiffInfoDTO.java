package pl.edu.agh.mobilecodereviewer.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lee on 2014-09-10.
 */
public class DiffInfoDTO {

    @SerializedName("content")
    private List<DiffContentDTO> diffContent;

    public DiffInfoDTO() {
    }

    public DiffInfoDTO(List<DiffContentDTO> diffContent) {
        this.diffContent = diffContent;
    }

    public List<DiffContentDTO> getDiffContent() {
        return diffContent;
    }
}
