package pl.edu.agh.mobilecodereviewer.model.utilities;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.model.Line;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
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
