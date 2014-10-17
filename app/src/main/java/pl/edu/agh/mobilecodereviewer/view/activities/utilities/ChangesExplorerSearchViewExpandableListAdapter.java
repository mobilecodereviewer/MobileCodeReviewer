package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;

/**
 * Created by lee on 2014-10-13.
 */
public class ChangesExplorerSearchViewExpandableListAdapter extends ChangesExplorerViewExpandableListAdapter {
    private final String textToSearch;

    public ChangesExplorerSearchViewExpandableListAdapter(Activity context, List<ChangeInfo> changeInfos,String textToSearch) {
        super(context, changeInfos);
        this.textToSearch = textToSearch;
    }

    @Override
    protected void setTextViewContent(TextView textView, String content) {
        if (content != null) {
            final SpannableStringBuilder spannableStringBuilder = createSpannableStringWithFoundTextHighlighted(content);
            textView.setText(spannableStringBuilder);
        }else textView.setText("");
    }

    private SpannableStringBuilder createSpannableStringWithFoundTextHighlighted(String content) {
        final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        Pattern pattern = Pattern.compile(textToSearch,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            spannableStringBuilder.setSpan(new BackgroundColorSpan(Color.YELLOW),
                                           matcher.start(),
                                           matcher.end(),
                                           Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return spannableStringBuilder;
    }
}
