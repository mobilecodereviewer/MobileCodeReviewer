package pl.edu.agh.mobilecodereviewer.model;


import com.google.common.collect.Lists;

import java.util.List;

/**
 * Model represents brief information about line in source code
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public class Line {
    /**
     * Number of the line
     */
    private int lineNumber;

    /**
     * Content of the line
     */
    private String content;

    /**
     * Comment for a given file
     */
    private List<Comment> comments;

    private boolean pendingComments;

    /**
     * No-argument constructor ,doesnt initialize any fields etc.
     */
    public Line() {
    }

    /**
     * Construct Line from given line number,content and comments
     * @param lineNumber {@link pl.edu.agh.mobilecodereviewer.model.Line#lineNumber}
     * @param content {@link pl.edu.agh.mobilecodereviewer.model.Line#content}
     * @param comments {@link pl.edu.agh.mobilecodereviewer.model.Line#comments}
     */
    public Line(int lineNumber, String content, List<Comment> comments) {
        this.lineNumber = lineNumber;
        this.content = content;
        this.comments = comments;
    }

    /**
     * Construct Line from given line number,content and without comments
     * @param lineNumber {@link pl.edu.agh.mobilecodereviewer.model.Line#lineNumber}
     * @param content {@link pl.edu.agh.mobilecodereviewer.model.Line#content}
     */
    public Line(int lineNumber,String content) {
        this.content = content;
        this.lineNumber = lineNumber;
        this.comments = Lists.newLinkedList();
    }

    /**
     * Getter for {@link pl.edu.agh.mobilecodereviewer.model.Line#lineNumber}
     * @return Line Number
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Setter for {@link pl.edu.agh.mobilecodereviewer.model.Line#lineNumber}
     * @param lineNumber Number of the line to assign
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * Getter for {@link pl.edu.agh.mobilecodereviewer.model.Line#content}
     * @return Content of the line
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter for {@link pl.edu.agh.mobilecodereviewer.model.Line#comments}
     * @param content Set content of the line
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter for {@link pl.edu.agh.mobilecodereviewer.model.Line#comments}
     * @return Comment of the line
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * Setter for {@link pl.edu.agh.mobilecodereviewer.model.Line#comments}
     * @param comments
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Check if there is a comment for a line
     * @return true if there are a comments otherwise false
     */
    public boolean hasComments() {
        return comments.size() > 0;
    }

    public boolean isPendingComments() {
        return pendingComments;
    }

    public void setPendingComments(boolean pendingComments) {
        this.pendingComments = pendingComments;
    }

    /**
     * Construct Line from line number and content
     * @param lineNumber {@link pl.edu.agh.mobilecodereviewer.model.Line#lineNumber}
     * @param content {@link pl.edu.agh.mobilecodereviewer.model.Line#content}
     * @return Constructed {@link pl.edu.agh.mobilecodereviewer.model.Line}
     */
    public static Line valueOf(int lineNumber,String content) {
        return new Line(lineNumber,content);
    }

    /**
     * Construct Line from line number and content
     * @param lineNumber {@link pl.edu.agh.mobilecodereviewer.model.Line#lineNumber}
     * @param content {@link pl.edu.agh.mobilecodereviewer.model.Line#content}
     * @param comments {@link pl.edu.agh.mobilecodereviewer.model.Line#comments}
     * @return Constructed {@link pl.edu.agh.mobilecodereviewer.model.Line}
     */
    public static Line valueOf(int lineNumber, String content, List<Comment> comments) {
        return new Line(lineNumber,content,comments);
    }
}
