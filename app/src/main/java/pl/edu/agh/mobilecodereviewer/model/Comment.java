package pl.edu.agh.mobilecodereviewer.model;

/**
 * Model represents information about comment
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public class Comment {
    /**
     * Content of the comment
     */
    private String draftId;

    private String content;

    private String path;

    private int line;

    private String author;

    private String updated;

    private boolean isDraft;

    /**
     * No-argument constructor ,doesnt initialize any fields etc.
     */
    public Comment() {
    }

    public Comment(int line, String path, String content, String author, String updated) {
        this.content = content;
        this.path = path;
        this.line = line;
        this.author = author;
        this.updated = updated;
    }

    public Comment(int line, String content) {
        this.line = line;
        this.content = content;
    }

    /**
     * Getter of the {@link pl.edu.agh.mobilecodereviewer.model.Comment#content}
     * @return content of comment
     */
    public String getContent() {
        return content;
    }

    public String getPath() {
        return path;
    }

    public int getLine() {
        return line;
    }

    public String getAuthor() {
        return author;
    }

    public String getUpdated() {
        return updated;
    }

    public boolean isDraft() {
        return isDraft;
    }

    public void setDraft(boolean isPending) {
        this.isDraft = isPending;
    }

    public void setContent(String content){this.content = content;}

    public String getDraftId() {
        return draftId;
    }

    public void setDraftId(String draftId) {
        this.draftId = draftId;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (line != comment.line) return false;
        if (content != null ? !content.equals(comment.content) : comment.content != null)
            return false;
        return !(path != null ? !path.equals(comment.path) : comment.path != null);

    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + line;
        return result;
    }

    /**
     * Construct object from given content
     * @param content {@link pl.edu.agh.mobilecodereviewer.model.Comment#content}
     * @return Constructed {@link pl.edu.agh.mobilecodereviewer.model.Comment}
     */
    public static Comment valueOf(int line,String path,String content, String author, String updated) {
        return new Comment( line,path,String.valueOf(content),  author, updated );
    }

    @Override
    public String toString() {
        return "Comment{" +
                "draftId='" + draftId + '\'' +
                ", content='" + content + '\'' +
                ", path='" + path + '\'' +
                ", line=" + line +
                ", author='" + author + '\'' +
                ", updated='" + updated + '\'' +
                ", isDraft=" + isDraft +
                '}';
    }

}
