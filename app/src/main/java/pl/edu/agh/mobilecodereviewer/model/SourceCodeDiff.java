package pl.edu.agh.mobilecodereviewer.model;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.dto.DiffContentDTO;
import pl.edu.agh.mobilecodereviewer.dto.DiffInfoDTO;


public class SourceCodeDiff {
    private List<DiffContentDTO> diffParts;
    private int linesCount;

    public SourceCodeDiff(DiffInfoDTO diffInfoDTO) {
        if ( diffInfoDTO != null )
            diffParts = diffInfoDTO.getDiffContent();
        linesCount = 0;
        initializeLineCount();
    }

    private int initializeLineCount() {
        if (diffParts == null || diffParts.size() == 0) {
            return 0;
        }

        for (int i=0;i < diffParts.size();i++)
            linesCount += calculateSizeOfDiffPart(i);
        return linesCount;
    }

    public int getLinesCount() {
        return linesCount;
    }


    public DiffLine getLine(int line) {
        int oldLine = line;
        if (diffParts == null || line >= linesCount || line < 0 ) {
            return null;
        }

        int i = 0;
        while (line >= calculateSizeOfDiffPart(i)) {
            line -= calculateSizeOfDiffPart(i);
            i++;
        }

        String content = getContentOfLineAtOffset(i, line);
        DiffLineType type = getDiffTypeOfLineAtOffset(i,line);
        int lineno = calculateLineNumber(oldLine);
        return new DiffLine(lineno, type, content);
    }

    private int calculateLineNumber(int offset) {
        int lineNumber = 0;
        int i = 0;
        while (offset >= calculateSizeOfDiffPart(i)) {
            offset -= calculateSizeOfDiffPart(i);
            lineNumber += calculateNumberOfLinesOfDiffPart(i);
            i++;
        }
        return lineNumber + offset + 1;
    }

    private int calculateNumberOfLinesOfDiffPart(int i) {
        if (isPartUnchanged(i)) {
            return diffParts.get(i).getLinesUnchanged().size();
        } else if (isPartReplaced(i)) {

            int linesBefore = diffParts.get(i).getLinesBeforeChange() == null ? 0 : diffParts.get(i).getLinesBeforeChange().size();
            int linesAfter = diffParts.get(i).getLinesAfterChange() == null ? 0 : diffParts.get(i).getLinesAfterChange().size();
            return linesBefore + linesAfter;
        } else return diffParts.get(i).getCountOfSkippedCommonLines();
    }

    private DiffLineType getDiffTypeOfLineAtOffset(int partId, int offset) {
        if (isPartUnchanged(partId)) {
            return DiffLineType.UNCHANGED;
        } else if (isPartReplaced(partId)) {

            if (diffParts.get(partId).getLinesAfterChange() != null) { // TODO te warunki bardzo nieczytalne , popraw
                if ( diffParts.get(partId).getLinesBeforeChange() == null)
                    return DiffLineType.ADDED;

                if (offset < diffParts.get(partId).getLinesBeforeChange().size()) {
                    return DiffLineType.REMOVED;
                } else
                    return DiffLineType.ADDED;
            } else
                return DiffLineType.REMOVED;

        } else
            return DiffLineType.SKIPPED;
    }

    private boolean isPartReplaced(int partId) {
        return diffParts.get(partId).getLinesAfterChange()  != null ||
                diffParts.get(partId).getLinesBeforeChange() != null;
    }

    private boolean isPartUnchanged(int partId) {
        return diffParts.get(partId).getLinesUnchanged() != null;
    }

    private String getContentOfLineAtOffset(int partId, int offset) {
        if (isPartUnchanged(partId)) {
            List<String> linesUnchanged = diffParts.get(partId).getLinesUnchanged();
            return linesUnchanged.get(offset);
        } else if (isPartReplaced(partId)) {

            if (diffParts.get(partId).getLinesBeforeChange() != null) {
                int beforeLinesCount = diffParts.get(partId).getLinesBeforeChange().size();
                if ( offset < beforeLinesCount)
                    return diffParts.get(partId).getLinesBeforeChange().get(offset);
                else
                    return diffParts.get(partId).getLinesAfterChange().get(offset - beforeLinesCount);
            } else {
                return diffParts.get(partId).getLinesAfterChange().get(offset);
            }
        } else {
            int linesToSkip = diffParts.get(partId).getCountOfSkippedCommonLines();
            if (linesToSkip == 1) {
                return "skipped 1 line"; // skipped
            } else {
                return "skipped " + linesToSkip + " lines";
            }
        }
    }

    private int calculateSizeOfDiffPart(int i) {
        if (isPartUnchanged(i)) {
            return diffParts.get(i).getLinesUnchanged().size();
        } else if (isPartReplaced(i)) {

            int linesBefore = diffParts.get(i).getLinesBeforeChange() == null ? 0 : diffParts.get(i).getLinesBeforeChange().size();
            int linesAfter = diffParts.get(i).getLinesAfterChange() == null ? 0 : diffParts.get(i).getLinesAfterChange().size();
            return linesBefore + linesAfter;
        } else return 1;
    }

}
















