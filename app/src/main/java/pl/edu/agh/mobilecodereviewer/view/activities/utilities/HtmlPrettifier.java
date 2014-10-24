package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.view.activities.utilities.syntax.PrettifyHighlighter;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.syntax.SyntaxHighlighter;

/**
 * Created by lee on 2014-10-24.
 */
public class HtmlPrettifier {
    private String extension;
    private List<String> content;

    public HtmlPrettifier(String extension, List<String> content) {
        this.extension = extension;
        this.content = content;
    }

    public String[] buildHtmlContent() {
        SyntaxHighlighter prettifyHighlighter = new PrettifyHighlighter();
        StringBuilder joinedSourceBuilder = new StringBuilder();
        for (int i=0;i<content.size()-1;i++) {
            joinedSourceBuilder.append( content.get(i) + "\n");
        }
        joinedSourceBuilder.append( content.get( content.size()-1) );

        String joinedSourceCode = joinedSourceBuilder.toString();
        String prettifiedSourceCode = prettifyHighlighter.highlight(joinedSourceCode, extension);
        return prettifiedSourceCode.split("\n");
    }
}
