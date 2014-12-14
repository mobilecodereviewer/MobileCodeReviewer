package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;

public class ChangesExplorerSearchViewListAdapter extends ChangesExplorerViewListAdapter{

    private String textToSearch;

    public ChangesExplorerSearchViewListAdapter(Activity context, List<ChangeInfo> content, String textToSearch, List<String> visibileLabels) {
        super(context, content, visibileLabels);
        this.textToSearch = textToSearch;
    }

    @Override
    protected void setSubject(TextView view, String subject){
        if (subject != null) {
            final SpannableStringBuilder spannableStringBuilder = HighlightedTextSpannableStringBuilder.build(subject, textToSearch);
            view.setText(spannableStringBuilder);
        }else view.setText("");
    }

}
