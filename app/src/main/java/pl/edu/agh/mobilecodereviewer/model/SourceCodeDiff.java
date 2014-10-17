package pl.edu.agh.mobilecodereviewer.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.dto.DiffContentDTO;
import pl.edu.agh.mobilecodereviewer.dto.DiffInfoDTO;

/**
 * Created by lee on 2014-10-16.
 */
public class SourceCodeDiff {
    private DiffInfoDTO diffInfoDTO;
    private Map<Integer, DiffedLine> lines;
    private int countOfLines;

    public SourceCodeDiff(DiffInfoDTO diffInfoDTO) {
        this.diffInfoDTO = diffInfoDTO;
        lines = new HashMap<Integer, DiffedLine>();
        initialize();
    }

    private void initialize() {
        int i=0;
        int oldLines = 0;
        int newLines = 0;
        List<DiffContentDTO> contents = getContent();
        for (DiffContentDTO content : contents) {
            if ( isContentUnchanged(content) ) {
                for (String line : content.getLinesUnchanged()) {
                    lines.put(i,new DiffedLine(line,i,oldLines,newLines,DiffLineType.UNCHANGED ) );
                    i++;oldLines++;newLines++;
                }
            } else if ( isContentChanged(content) ) {
                if (content.getLinesBeforeChange() != null) {
                    for (String line : content.getLinesBeforeChange()) {
                        lines.put(i, new DiffedLine(line,i,oldLines,newLines,DiffLineType.REMOVED ) );
                        i++;oldLines++;
                    }
                }
                if (content.getLinesAfterChange() != null) {
                    for (String line : content.getLinesAfterChange()) {
                        lines.put(i, new DiffedLine(line,i,oldLines,newLines,DiffLineType.ADDED ) );
                        i++;newLines++;
                    }
                }
            } else { // content skipped
                lines.put(i,new DiffedLine(createSkippedLine(content.getCountOfSkippedCommonLines()),i,oldLines,newLines,DiffLineType.SKIPPED ));
                oldLines+=content.getCountOfSkippedCommonLines();
                newLines+=content.getCountOfSkippedCommonLines();
            }
        }
        countOfLines = i;
    }

    private String createSkippedLine(int countOfSkippedCommonLines) {
        return "Skipped " + countOfSkippedCommonLines + " lines";
    }

    private boolean isContentUnchanged(DiffContentDTO content) {
        return content.getLinesUnchanged() != null && content.getLinesUnchanged().size() > 0;
    }

    private boolean isContentChanged(DiffContentDTO content) {
        return (content.getLinesBeforeChange() != null  && content.getLinesBeforeChange().size() > 0) || 
               (content.getLinesAfterChange() != null && content.getLinesAfterChange().size() > 0);
    }

    private List<DiffContentDTO> getContent() {
        return this.diffInfoDTO.getDiffContent();
    }

    public DiffedLine getLine(int numline) {
        if (numline < 0 || numline > countOfLines) {
            return null;
        }
        return lines.get(numline);
    }

    public int getLinesCount() {
        return countOfLines;
    }
}
