package pl.edu.agh.mobilecodereviewer.dto;

import java.util.Date;

/**
 * Class represent data returned from gerrit instance
 * about information of comment
 */
public class CommentInfoDTO {
    /**
     * Comment Identifier
     */
    private String id;

    /**
     * Line of source code
     */
    private int line;

    /**
     * Message of the comment
     */
    private String message;

    /**
     * Date of adding comment
     */
    private String updated;

    /**
     * Author of comment;
     */
    private AccountInfoDTO author;

    /**
     * Create empty object
     */
    public CommentInfoDTO() {
    }

    /**
     * Create Comment from parameters
     * @param id Identifier of the comment
     * @param line Line of source code
     * @param message Message of comment
     */
    public CommentInfoDTO(String id, int line, String message) {
        this.id = id;
        this.line = line;
        this.message = message;
    }

    /**
     * Get identifier of the comment
     * @return identifier of the comment
     */
    public String getId() {
        return id;
    }

    /**
     * Sets identifier of the comment to given
     * @param id new comment id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get line number of source code where comment begin
     * @return Line number of source code
     */
    public int getLine() {
        return line;
    }

    /**
     * Sets line number of source where comment begins
     * to given
     * @param line line number
     */
    public void setLine(int line) {
        this.line = line;
    }

    /**
     * Gets the message of the comment
     * @return message of the comment
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message of the comment to give
     * @param message message of the comment
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public AccountInfoDTO getAuthor() {
        return author;
    }

    public void setAuthor(AccountInfoDTO author) {
        this.author = author;
    }

    /**
     * Construct object from line number and message
     * @param line line number
     * @param message message of the comment
     * @return Constructed {@link pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO}
     */
    public static CommentInfoDTO valueOf(int line,String message) {
        return new CommentInfoDTO("",line,message);
    }
}
