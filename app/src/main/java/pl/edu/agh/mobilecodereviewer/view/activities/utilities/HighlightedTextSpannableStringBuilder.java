package pl.edu.agh.mobilecodereviewer.view.activities.utilities;


import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HighlightedTextSpannableStringBuilder extends SpannableStringBuilder{

    public static SpannableStringBuilder build(String text, String textToHighlight){
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        Pattern pattern = Pattern.compile(textToHighlight,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            spannableStringBuilder.setSpan(new BackgroundColorSpan(Color.YELLOW),
                    matcher.start(),
                    matcher.end(),
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return spannableStringBuilder;
    }

}
