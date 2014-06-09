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
    private String content;

    /**
     * No-argument constructor ,doesnt initialize any fields etc.
     */
    public Comment() {
    }

    /**
     * Construct object from content
     * @param content Content of the comment
     */
    public Comment(String content) {
        this.content = content;
    }

    /**
     * Getter of the {@link pl.edu.agh.mobilecodereviewer.model.Comment#content}
     * @return content of comment
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter for the {@link pl.edu.agh.mobilecodereviewer.model.Comment#content}
     * @param content Value for content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Construct object from given content
     * @param content {@link pl.edu.agh.mobilecodereviewer.model.Comment#content}
     * @return Constructed {@link pl.edu.agh.mobilecodereviewer.model.Comment}
     */
    public static Comment valueOf(String content) {
        return new Comment( String.valueOf(content) );
    }
}
