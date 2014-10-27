package pl.edu.agh.mobilecodereviewer.view.activities.utilities.syntax;

import android.text.TextUtils;

import prettify.PrettifyParser;
import syntaxhighlight.ParseResult;
import syntaxhighlight.Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This code (with minor changes) is taken from mcr application
 * created by fracz
 * https://github.com/fracz
 */

public class PrettifyHighlighter implements SyntaxHighlighter {
    public static final String LF = "\n";

    private static final Map<String, String> COLORS = buildColorsMap();

    private static final String FONT_PATTERN = "<font color=\"#%s\">%s</font>";

    private final Parser parser = new PrettifyParser();

    @Override
    public String highlight(String sourceCode, String language) {
        StringBuilder highlighted = new StringBuilder();
        List<ParseResult> results = parser.parse(language, sourceCode);
        for (ParseResult result : results) {
            String type = result.getStyleKeys().get(0);
            String content = sourceCode.substring(result.getOffset(), result.getOffset() + result.getLength());
            highlighted.append(highlighEachLine(content, type));
        }
        return highlighted.toString();
    }

    private String highlighEachLine(String content, String type) {
        String color = getColor(type);
        String[] lines = content.split(LF);
        if (lines.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(colorize(lines[0], color));
            for (int i = 1; i < lines.length; i++) {
                sb.append(LF);
                sb.append(colorize(lines[i], color));
            }
            return sb.toString();
        } else {
            return colorize(content, color);
        }
    }

    public static String encode(String string) {
        string = TextUtils.htmlEncode(string);
        return string.replace(" ", "&nbsp;");
    }

    private String colorize(String content, String color) {
        content = encode(content);
        return String.format(FONT_PATTERN, color, content);
    }

    private String getColor(String type) {
        return COLORS.containsKey(type) ? COLORS.get(type) : COLORS.get("pln");
    }

    private static Map<String, String> buildColorsMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("typ", "990033");
        map.put("tag", "3BAFF7");
        map.put("kwd", "000080");
        map.put("lit", "FF0000");
        map.put("com", "CCCC99");
        map.put("str", "2EC7FF");
        map.put("pun", "000000");
        map.put("pln", "000000");
        return map;
    }
}
