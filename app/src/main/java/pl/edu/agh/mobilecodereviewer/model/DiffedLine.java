package pl.edu.agh.mobilecodereviewer.model;

/**
 * Created by lee on 2014-09-12.
 */
public class DiffedLine {

    private final String content;

    private final int lineNumber;
    private final int oldLineNumber;
    private final int newLineNumber;

    private final DiffLineType lineType;

    public DiffedLine(String content, int lineNumber, int oldLineNumber, int newLineNumber, DiffLineType lineType) {
        this.content = content;
        this.lineNumber = lineNumber;
        this.oldLineNumber = oldLineNumber;
        this.newLineNumber = newLineNumber;
        this.lineType = lineType;
    }

    public String getContent() {
        return content;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public DiffLineType getLineType() {
        return lineType;
    }

    public int getOldLineNumber() {
        return oldLineNumber;
    }

    public int getNewLineNumber() {
        return newLineNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiffedLine line = (DiffedLine) o;

        if (lineNumber != line.lineNumber) return false;
        if (content != null ? !content.equals(line.content) : line.content != null) return false;
        if (lineType != line.lineType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + lineNumber;
        result = 31 * result + (lineType != null ? lineType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DiffLine{" +
                "content='" + content + '\'' +
                ", lineNumber=" + lineNumber +
                ", lineType=" + lineType +
                '}';
    }
}
