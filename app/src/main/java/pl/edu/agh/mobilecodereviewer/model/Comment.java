package pl.edu.agh.mobilecodereviewer.model;


public class Comment {
    private String content;

    public Comment() {
    }

    public Comment(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static Comment valueOf(String content) {
        return new Comment(content);
    }
}
