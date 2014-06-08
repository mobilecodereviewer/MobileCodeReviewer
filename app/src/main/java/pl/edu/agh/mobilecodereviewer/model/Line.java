package pl.edu.agh.mobilecodereviewer.model;


import com.google.common.collect.Lists;

import java.util.List;

public class Line {
    private int lineNumber;
    private String line;
    private List<Comment> comments;

    public Line() {
        comments = Lists.newLinkedList();
    }

    public Line(int lineNumber, String line, List<Comment> comments) {
        this.lineNumber = lineNumber;
        this.line = line;
        this.comments = comments;
    }

    public Line(int lineNumber,String line ) {
        this.line = line;
        this.lineNumber = lineNumber;
        this.comments = Lists.newLinkedList();
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean hasComments() {
        return comments.size() > 0;
    }

    public static Line valueOf(int lineNumber,String line) {
        return new Line(lineNumber,line);
    }

    public static Line valueOf(int lineNumber, String line, List<Comment> comments) {
        return new Line(lineNumber,line,comments);
    }
}
