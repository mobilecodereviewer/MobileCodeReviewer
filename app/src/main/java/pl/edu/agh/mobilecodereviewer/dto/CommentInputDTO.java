package pl.edu.agh.mobilecodereviewer.dto;


public class CommentInputDTO {
    private String path;
    private int line;
    private String message;

    public CommentInputDTO(int line, String message) {
        this.line = line;
        this.message = message;
    }

    public CommentInputDTO(int line, String message, String path) {
        this.line = line;
        this.message = message;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLine() {
        return line;
    }

    public String getMessage() {
        return message;
    }

}
