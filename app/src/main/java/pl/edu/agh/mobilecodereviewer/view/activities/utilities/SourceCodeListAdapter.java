package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.widget.TextView;

/**
 * Created by lee on 2014-10-22.
 */
public interface SourceCodeListAdapter {
    void showLineNumbers();
    void hideLineNumbers();
    void setCodeTextViewContent(TextView txtCodeContent, String prefix,int position);
}
