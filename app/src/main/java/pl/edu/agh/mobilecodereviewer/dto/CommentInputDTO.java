package pl.edu.agh.mobilecodereviewer.dto;

/**
 * Created by lee on 2014-09-22.
 */
public class CommentInputDTO {
    private int line;
    private String message;
    private String path;

    public CommentInputDTO(int line, String message, String path) {
        this.line = line;
        this.message = message;
        this.path = path;
    }

    public int getLine() {
        return line;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
