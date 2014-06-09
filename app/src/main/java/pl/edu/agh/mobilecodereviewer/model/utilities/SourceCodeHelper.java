package pl.edu.agh.mobilecodereviewer.model.utilities;


import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.model.Line;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;

/**
 * Source Code Helper miscellaneous utility class.
 */
public final class SourceCodeHelper {


    /**
     * Get Content from given source code
     * @param sourceCode {@link pl.edu.agh.mobilecodereviewer.model.SourceCode}
     * @return List of content from source code
     */
    public static List<String> getContent(SourceCode sourceCode) {
        return Lists.transform(sourceCode.getLines(),
                new Function<Line, String>() {

                    @Override
                    public String apply(Line from) {
                        return from.getContent();
                    }
                }
        );
    }

    /**
     * Get Information about which lines has comments
     * @param sourceCode {@link pl.edu.agh.mobilecodereviewer.model.SourceCode}
     * @return List of boolean expressing if given line has comments
     */
    public static List<Boolean> getHasLineComments(SourceCode sourceCode) {
        return Lists.transform(sourceCode.getLines(),
                new Function<Line, Boolean>() {

                    @Override
                    public Boolean apply(Line from) {
                        return from.hasComments();
                    }
                }
        );
    }
}
