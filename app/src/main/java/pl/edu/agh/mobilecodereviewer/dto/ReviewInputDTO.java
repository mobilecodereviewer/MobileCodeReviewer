package pl.edu.agh.mobilecodereviewer.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReviewInputDTO {
    Map<String, List<CommentInputDTO>> comments;

    public ReviewInputDTO(Map<String, List<CommentInputDTO>> comments) {
        this.comments = comments;
    }

    public Map<String, List<CommentInputDTO>> getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewInputDTO that = (ReviewInputDTO) o;

        if (comments != null ? !comments.equals(that.comments) : that.comments != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return comments != null ? comments.hashCode() : 0;
    }

    public static ReviewInputDTO createFromSingleComment(String path, CommentInputDTO commentInputDTO) {
        Map<String, List<CommentInputDTO>> comments = new HashMap<>();
        comments.put(path, Collections.singletonList(commentInputDTO));
        return new ReviewInputDTO(comments);
    }

}