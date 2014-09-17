package pl.edu.agh.mobilecodereviewer.model;

/**
 * Created by lee on 2014-09-12.
 */
public class DiffLine{

    private final String content;
    private final int lineNumber;
    private final DiffLineType lineType;

    public DiffLine(int lineNumber, DiffLineType diffLineType, String content) {
        this.lineNumber = lineNumber;
        this.lineType = diffLineType;
        this.content = content;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiffLine line = (DiffLine) o;

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
