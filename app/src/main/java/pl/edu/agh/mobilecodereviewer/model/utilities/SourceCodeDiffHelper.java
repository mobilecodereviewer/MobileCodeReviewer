package pl.edu.agh.mobilecodereviewer.model.utilities;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;

public class SourceCodeDiffHelper {

    public static List<String> getContent(SourceCodeDiff sourceCodeDiff) {
       List<String> result = new LinkedList<String>();

        for(int i = 0; i<sourceCodeDiff.getLinesCount(); i++){
            result.add(sourceCodeDiff.getLine(i).getContent());
        }

        return result;
    }

}
