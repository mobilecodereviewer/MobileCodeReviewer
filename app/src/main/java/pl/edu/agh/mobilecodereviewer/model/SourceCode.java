package pl.edu.agh.mobilecodereviewer.model;


import java.util.List;

/**
 * Model represents a source file in project
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public class SourceCode {
    /**
     * List of all lines ({@link pl.edu.agh.mobilecodereviewer.model.Line}) which source file posess
     */
    private List<Line> lines;

    /**
     * No-argument constructor ,doesnt initialize any fields etc.
     */
    public SourceCode() {
    }

    /**
     * Construct source code from given lines {@link pl.edu.agh.mobilecodereviewer.model.Line}
     * @param lines List of {@link pl.edu.agh.mobilecodereviewer.model.Line}
     */
    public SourceCode(List<Line> lines) {
        this.lines = lines;
    }

    /**
     * Getter for {@link pl.edu.agh.mobilecodereviewer.model.SourceCode#lines}
     * @return Lines from source code
     */
    public List<Line> getLines() {
        return lines;
    }

    public Line getLine(int lineNumber) {
        return lines.get(lineNumber-1);
    }

    public String getLineContent(int lineNumber) {
        return lines.get(lineNumber-1).getContent();
    }

    public List<Comment> getLineComments(int lineNumber) {
        return lines.get(lineNumber-1).getComments();
    }

    /**
     * Setter for {@link pl.edu.agh.mobilecodereviewer.model.SourceCode#lines}
     * @param lines Lines of code
     */
    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    /**
     * Construct Source code from given list of {@link pl.edu.agh.mobilecodereviewer.model.Line}
     * @param lines List of lines
     * @return Constructed source code {@link pl.edu.agh.mobilecodereviewer.model.SourceCode}
     */
    public static SourceCode valueOf(List<Line> lines) {
        return new SourceCode(lines);
    }
}
