package pl.edu.agh.mobilecodereviewer.dto;


public class CommentInfoDTO {
    private String id;
    private int line;
    private String message;

    public CommentInfoDTO() {
    }

    public CommentInfoDTO(String id, int line, String message) {
        this.id = id;
        this.line = line;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static CommentInfoDTO valueOf(int line,String message) {
        return new CommentInfoDTO("",line,message);
    }
}
