package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;

/**
 * Created by lee on 2014-10-13.
 */
public class ChangesExplorerSearchViewExpandableListAdapter extends ChangesExplorerViewExpandableListAdapter {
    private final String textToSearch;

    public ChangesExplorerSearchViewExpandableListAdapter(Activity context, List<ChangeInfo> changeInfos,String textToSearch, List<String> visibleLabels) {
        super(context, changeInfos, visibleLabels);
        this.textToSearch = textToSearch;
    }

    @Override
    protected void setTextViewContent(TextView textView, String content) {
        if (content != null) {
            final SpannableStringBuilder spannableStringBuilder = HighlightedTextSpannableStringBuilder.build(content, textToSearch);
            textView.setText(spannableStringBuilder);
        }else textView.setText("");
    }

}
