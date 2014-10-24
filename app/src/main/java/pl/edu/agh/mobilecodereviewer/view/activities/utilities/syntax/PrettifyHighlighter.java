package pl.edu.agh.mobilecodereviewer.view.activities.utilities.syntax;

import android.text.TextUtils;

import prettify.PrettifyParser;
import syntaxhighlight.ParseResult;
import syntaxhighlight.Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        map.put("typ", "87cefa");
        map.put("tag", "87cefa");
        map.put("kwd", "00ff00");
        map.put("lit", "ffff00");
        map.put("com", "999999");
        map.put("str", "ff4500");
        map.put("pun", "eeeeee");
        map.put("pln", "000000");
        return map;
    }
}
