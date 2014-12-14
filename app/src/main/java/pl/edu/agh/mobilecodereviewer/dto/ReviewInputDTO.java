package pl.edu.agh.mobilecodereviewer.dto;

import java.util.List;
import java.util.Map;


public class ReviewInputDTO {
    String message = null;
    Map<String,Integer> labels = null;
    Map<String, List<CommentInputDTO>> comments = null;

    public ReviewInputDTO(String message,Map<String,Integer> labels) {
        this.message = message;
        this.labels = labels;
    }

    public ReviewInputDTO(Map<String, List<CommentInputDTO>> comments) {
        this.comments = comments;
    }

    public Map<String, List<CommentInputDTO>> getComments() {
        return comments;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Integer> getLabels() {
        return labels;
    }

    public void setComments(Map<String, List<CommentInputDTO>> comments){
        this.comments = comments;
    }

    public static ReviewInputDTO createVoteReview(String message, Map<String, Integer> votes) {
        return new ReviewInputDTO(message, votes);
    }
}
