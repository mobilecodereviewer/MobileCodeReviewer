package pl.edu.agh.mobilecodereviewer.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.dto.DiffContentDTO;
import pl.edu.agh.mobilecodereviewer.dto.DiffInfoDTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SourceCodeDiffTest {

    @Test
    public void isRunning() throws Exception {
        assertEquals(true,true);
    }

    private void checkIfCalculateValidNumberOfLines(DiffInfoDTO diffInfo, int expectedNumberOfLines) {
        SourceCodeDiff sourceCodeDiff = new SourceCodeDiff(diffInfo);
        int numberOfLines = sourceCodeDiff.getLinesCount();
        assertEquals(expectedNumberOfLines, numberOfLines);
    }

    private List<String> lineList(String... lines) {
        return Arrays.asList(lines);
    }

    @Test
    public void shouldCalculateNumberOfLinesAsZeroForNullInput() throws Exception {
        DiffInfoDTO diffInfo = null;
        checkIfCalculateValidNumberOfLines(diffInfo , 0);
    }

    @Test
    public void shouldCalculateNumberOfLinesAsZeroForEmptyInput() throws Exception {
        DiffInfoDTO diffInfo = new DiffInfoDTO();
        checkIfCalculateValidNumberOfLines(diffInfo, 0);
    }

    @Test
    public void shouldCalculateNumberOfLinesAsZeroForZeroPartsInput() throws Exception {
        DiffInfoDTO diffInfo = new DiffInfoDTO(Collections.EMPTY_LIST);
        checkIfCalculateValidNumberOfLines(diffInfo,0);
    }

    private DiffContentDTO createDiffContentDTO(List<String> beforeChange,List<String> afterChange,List<String> unchanged,
                                          int skipped) {
        return new DiffContentDTO (beforeChange, afterChange, unchanged, skipped );
    }

    private DiffInfoDTO createDiffInfoDTO(DiffContentDTO... diffContents) {
        return new DiffInfoDTO(Arrays.asList(diffContents));
    }

    @Test
    public void shouldClaculateNumberOfLinesForOneDiffPartOfUnchangedLines() throws Exception {
        DiffInfoDTO diffInfo = createDiffInfoDTO(
                createDiffContentDTO(null, null, lineList("include <iostream>", "int main(){}"), 0)
        );
        checkIfCalculateValidNumberOfLines(diffInfo, 2);
    }

    @Test
    public void shouldCalculateNumberOfLinesForOneDiffPartOfDifferentParts() throws Exception {
        DiffInfoDTO diffInfo = createDiffInfoDTO(
                createDiffContentDTO(lineList("include <stdio.h>"), lineList("include <iostream>", "int main(int argc) {}"),
                        null, 0)
        );
        checkIfCalculateValidNumberOfLines(diffInfo, 3);
    }

    @Test
    public void shouldCalculateNumberOfLinesForTwoPartsOneWithUnchangedLinesSecondWithDifferentLines() throws Exception {
        DiffInfoDTO diffInfo = createDiffInfoDTO(
                 createDiffContentDTO(null,null,lineList("include <iostream>") , 0),
                 createDiffContentDTO(lineList("include<math.h>"),lineList("include<math>"),null,0)
        );
        checkIfCalculateValidNumberOfLines(diffInfo, 3);
    }

    @Test
    public void shouldCalculateNumberOfLinesForThreePartFirstUnchangedSecondSkippedThirdUnchanged() throws Exception {
        DiffInfoDTO diffInfo = createDiffInfoDTO(
                createDiffContentDTO(null,null,lineList("include <iostream>") , 0),
                createDiffContentDTO(null,null,null,5),
                createDiffContentDTO(null,null,lineList("int main() {}") , 0)
        );
        checkIfCalculateValidNumberOfLines(diffInfo, 3);
    }

    @Test
    public void shouldCalculateNumberOfLinesForOnePartDifferentWithSomeLines() throws Exception {
        DiffInfoDTO diffInfo = createDiffInfoDTO(
                createDiffContentDTO(lineList("include <iostream>","// end"),
                                     lineList("include <iostrim>","include <math>","include <lol.h>"),
                                     null , 0)
        );
        checkIfCalculateValidNumberOfLines(diffInfo, 5);
    }

    @Test
    public void shouldCalculateNumberOfLinesForOnePartWithAddedLinesWithoutRemovedLines() throws Exception {
        DiffInfoDTO diffInfo = createDiffInfoDTO(
                createDiffContentDTO(null,
                        lineList("include <iostrim>","include <math>","include <lol.h>"),
                        null , 0)
        );
        checkIfCalculateValidNumberOfLines(diffInfo, 3);
    }

    private void checkIfGetLineReturnValidLine(DiffInfoDTO diffInfoDTO, int numline, DiffLine expectedLine) throws Exception {
        SourceCodeDiff sourceCodeDiff = new SourceCodeDiff(diffInfoDTO);
        DiffLine actualLine = sourceCodeDiff.getLine(numline);
        assertEquals( expectedLine , actualLine );
    }

    @Test
    public void shouldGetLineForNullDiffInfoDTOReturnNull() throws Exception {
        int anyLine = 0;
        checkIfGetLineReturnValidLine(null, anyLine, null);
    }

    @Test
    public void shouldGetLineForEmptyDiffInfoDTOReturnNullAndIndexGreaterThanElementCount() throws Exception {
        int anyLine = 0;
        DiffInfoDTO diffInfoDTO = createDiffInfoDTO();
        checkIfGetLineReturnValidLine(diffInfoDTO, anyLine, null);
    }

    @Test
    public void shouldGetLineForEmptyDiffInfoDTOReturnNullAndIndexIsBelowZero() throws Exception {
        int anyLine = -5;
        DiffInfoDTO diffInfoDTO = createDiffInfoDTO( );
        checkIfGetLineReturnValidLine(diffInfoDTO, anyLine, null);
    }

    @Test
    public void shouldGetLineForOneLineEmptyDiffDTOReturnValidValue() throws Exception {
        int firstLine = 0;
        DiffInfoDTO diffInfoDTO = createDiffInfoDTO(
                createDiffContentDTO(null, null, lineList("include <iostream>"), 0)
        );
        checkIfGetLineReturnValidLine(diffInfoDTO,firstLine,new DiffLine(1,DiffLineType.UNCHANGED,"include <iostream>") );
    }

    @Test
    public void shouldGetLineForThirdLineOnePartDiffDTOReturnValidValie() throws Exception {
        int thirdLine = 2;
        DiffInfoDTO diffInfoDTO = createDiffInfoDTO(
                createDiffContentDTO(null, null, lineList("include <iostream>","include <math.h>","using namespace sdt;"), 0)
        );
        checkIfGetLineReturnValidLine(diffInfoDTO,thirdLine,new DiffLine(3,DiffLineType.UNCHANGED,"using namespace sdt;") );
    }

    @Test
    public void shouldGetLineForThirdLineTwoPartDiffDTOReturnValidValue() throws Exception {
        int thirdLine = 2;
        DiffInfoDTO diffInfoDTO = createDiffInfoDTO(
                createDiffContentDTO(null, null, lineList("include <iostream>"), 0),
                createDiffContentDTO(null, null, lineList("include <math.h>","using namespace sdt;") , 0)
        );
        checkIfGetLineReturnValidLine(diffInfoDTO,thirdLine,new DiffLine(3,DiffLineType.UNCHANGED,"using namespace sdt;") );
    }

    @Test
    public void shouldGetLineForSecondAddedLineOnePartReplaceDiffDTOReturnValidValue() throws Exception {
        int secondLine = 1;
        DiffInfoDTO diffInfoDTO = createDiffInfoDTO(
                createDiffContentDTO(lineList("include <math.h>"), lineList("include <math>"), null,0)
        );
        checkIfGetLineReturnValidLine(diffInfoDTO,secondLine,new DiffLine(2,DiffLineType.ADDED,"include <math>") );
    }

    @Test
    public void shouldGetLineForFirstRemovedLineOnePartReplaceDiffDTOReturnValidValue() throws Exception {
        int firstLine = 0;
        DiffInfoDTO diffInfoDTO = createDiffInfoDTO(
                createDiffContentDTO(lineList("include <math.h>"), lineList("include <math>"), null,0)
        );
        checkIfGetLineReturnValidLine(diffInfoDTO,firstLine,new DiffLine(1,DiffLineType.REMOVED,"include <math.h>") );
    }


    @Test
    public void shouldGetLineForSecondLineTwoPartWithReplacedLineDiffDTOReturnValidValue() throws Exception {
        int secondLine = 1;
        DiffInfoDTO diffInfoDTO = createDiffInfoDTO(
                createDiffContentDTO(null, null, lineList("include <iostream>"), 0),
                createDiffContentDTO(lineList("include <math.h>"), lineList("include <math>"), null,0)
        );
        SourceCodeDiff sourceCodeDiff = new SourceCodeDiff(diffInfoDTO);
        DiffLine actualLine = sourceCodeDiff.getLine(secondLine);
        assertEquals( null , diffInfoDTO.getDiffContent().get(1).getLinesUnchanged()); // TODO checkIfGetLineReturnValidLine
        assertEquals(new DiffLine(2,DiffLineType.REMOVED,"include <math.h>"), actualLine );
    }

    @Test
    public void shouldGetLineForThirdLineTwoPartWithReplaceLineDiffDTOReturnValidValue() throws Exception {
        int thirdLine = 2;
        DiffInfoDTO diffInfoDTO = createDiffInfoDTO(
            createDiffContentDTO(null, null, lineList("int main(int argc,char **argv) {"), 0),
            createDiffContentDTO(lineList("int i;"), lineList("int sum;"), null,0)
        );
        checkIfGetLineReturnValidLine(diffInfoDTO,thirdLine,new DiffLine(3,DiffLineType.ADDED,"int sum;") );
    }

    @Test
    public void shouldGetLineForOnlyRemovedLineOnePartDiffDTOReturnValidValue() throws Exception {
        int firstLine = 0;
        DiffInfoDTO diffInfoDTO = createDiffInfoDTO(
            createDiffContentDTO(lineList("int l;"), null, null, 0)
        );
        checkIfGetLineReturnValidLine(diffInfoDTO,firstLine,new DiffLine(1, DiffLineType.REMOVED, "int l;") );
    }

    @Test
    public void shouldGetLineForOnlyAddedLineOnePartDiffDTOReturnValidValue() throws Exception {
        int firstLine = 0;
        DiffInfoDTO diffInfoDTO = createDiffInfoDTO(
                createDiffContentDTO(null, lineList("int l;"), null, 0)
        );
        SourceCodeDiff sourceCodeDiff = new SourceCodeDiff(diffInfoDTO);
        DiffLine actualLine = sourceCodeDiff.getLine(firstLine);
        assertEquals(new DiffLine(1, DiffLineType.ADDED, "int l;"), actualLine );
    }

    @Test
    public void shouldGetLineForthForFourLiveFourPartDiffDTOReturnValidValue() throws Exception {
        int forthLine = 3;
        DiffInfoDTO diffInfoDTO = createDiffInfoDTO(
                createDiffContentDTO(null, null, lineList("int w;"), 0),
                createDiffContentDTO(lineList("int k;"), null, null, 0),
                createDiffContentDTO(null, null, lineList("int l;"), 0),
                createDiffContentDTO(null, lineList("int m;"), null, 0)
        );
        checkIfGetLineReturnValidLine(diffInfoDTO,forthLine,new DiffLine(4, DiffLineType.ADDED, "int m;" ) );
    }

    @Test
    public void shouldGetLineFirstForTwoLinesFirstSkipped1OnePartDiffDTOReturnSkippedLine() throws Exception {
        int firstLine = 0;
        DiffInfoDTO diffInfoDTO = createDiffInfoDTO(
            createDiffContentDTO(null,null,null,1)
        );
        checkIfGetLineReturnValidLine(diffInfoDTO,firstLine,new DiffLine(1,DiffLineType.SKIPPED,"skipped 1 line") );
    }

    @Test
    public void shouldGetLineFirstForTwoLinesFirstSkipped3OnePartDiffDTOReturnSkippedLine() throws Exception {
        int firstLine = 0;
        DiffInfoDTO diffInfoDTO = createDiffInfoDTO(
            createDiffContentDTO(null,null,null,3)
        );
        checkIfGetLineReturnValidLine(diffInfoDTO, firstLine, new DiffLine(1, DiffLineType.SKIPPED, "skipped 3 lines"));
    }

    @Test
    public void shouldGetLineForThirdLineWithSecondLineSkippedSomeLinesCalculateNextLineDependendOfSkippedLines() throws Exception {
        int thirdLine = 2;
        DiffInfoDTO diffInfoDTO = createDiffInfoDTO(
                createDiffContentDTO(null, null, lineList("if (k == 1) {"),0),
                createDiffContentDTO(null, null, null,10),
                createDiffContentDTO(null, null, lineList("int m;") , 0)
        );
        checkIfGetLineReturnValidLine(diffInfoDTO, thirdLine, new DiffLine(12, DiffLineType.UNCHANGED, "int m;"));
    }

    @Test
    public void shouldGetLineForComplicatedScenarioWorksAppropriate() throws Exception {
        int sixLine = 5;
        DiffInfoDTO diffInfoDTO = createDiffInfoDTO(
                createDiffContentDTO(null, null, lineList("while (m > 1) {"),0),
                createDiffContentDTO(null, null, null,3),
                createDiffContentDTO(lineList("int l;","int w;"), null, null,0),
                createDiffContentDTO(null, lineList("int h;"), null,0),
                createDiffContentDTO(null, null, lineList("super();"),0)
        );
        checkIfGetLineReturnValidLine(diffInfoDTO, sixLine, new DiffLine(8, DiffLineType.UNCHANGED, "super();"));
    }
}
















