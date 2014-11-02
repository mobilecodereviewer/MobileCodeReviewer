package pl.edu.agh.mobilecodereviewer.dto;


public class CommentInputDTO {
    private int line;
    private String message;


    public CommentInputDTO(int line, String message) {
        this.line = line;
        this.message = message;
    }

    public int getLine() {
        return line;
    }

    public String getMessage() {
        return message;
    }

}
