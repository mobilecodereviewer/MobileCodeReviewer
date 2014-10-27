package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.model.DiffLineType;
import pl.edu.agh.mobilecodereviewer.model.DiffedLine;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;
import pl.edu.agh.mobilecodereviewer.model.utilities.SourceCodeDiffHelper;

public class SourceCodeDiffViewListAdapter extends ArrayAdapter<String>  implements SourceCodeListAdapter {

    private final Activity context;
    private final SourceCodeDiff sourceCodeDiff;
    private final List<Boolean> hasComments;
    private boolean showLineNumbers;

    public SourceCodeDiffViewListAdapter(Activity context,
                                         SourceCodeDiff sourceCodeDiff,
                                         List<Boolean> hasComments) {
        super(context, R.layout.layout_source_diff_line, SourceCodeDiffHelper.getContent(sourceCodeDiff));
        this.context = context;
        this.sourceCodeDiff = sourceCodeDiff;
        this.hasComments = hasComments;
        showLineNumbers = false;
    }

    @Override
    public View getView(int clickedOnListPosition, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout_source_diff_line, null, true);

        TextView txtContent = (TextView) rowView.findViewById(R.id.codeDiffText);
        TextView txtLineNumberBeforeChange = (TextView) rowView.findViewById(R.id.lineNumberBeforeChangeText);
        TextView txtLineNumberAfterChange = (TextView) rowView.findViewById(R.id.lineNumberAfterChangeText);

        LinearLayout lineContainer = (LinearLayout) rowView.findViewById(R.id.diffLineMainLayout);

        if ( sourceCodeDiff != null) {
            DiffedLine diffedLine = sourceCodeDiff.getLine(clickedOnListPosition);
            colorAndHighlightLineIfHasComments(txtContent, lineContainer, diffedLine);

            writeLineToTextView(lineContainer, txtContent, txtLineNumberBeforeChange, txtLineNumberAfterChange, diffedLine);
        }

        if (!showLineNumbers) {
            txtLineNumberBeforeChange.setVisibility(View.GONE);
            txtLineNumberAfterChange.setVisibility(View.GONE);
        }

        return rowView;
    }

    private void colorAndHighlightLineIfHasComments(TextView txtContent, LinearLayout lineContainer, DiffedLine diffedLine) {
        int actualPosition = -1;
        if ( diffedLine.getLineType() == DiffLineType.UNCHANGED || diffedLine.getLineType() == DiffLineType.ADDED)
            actualPosition = diffedLine.getNewLineNumber();

        if ( actualPosition > -1 && actualPosition < hasComments.size() &&
                hasComments.get(actualPosition) ) {

            float alpha = 0.7f;
            lineContainer.setAlpha(alpha);
            lineContainer.setBackgroundColor( Color.YELLOW );
            txtContent.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    private void writeLineToTextView(LinearLayout lineContainer, TextView content, TextView linenumBefore, TextView linenumAfter, DiffedLine line) {
        if (line == null) {
            linenumBefore.setText("");
            linenumAfter.setText("");
            content.setText("");
        } else {
            switch (line.getLineType()) {
                        case UNCHANGED:
                            linenumBefore.setText( Integer.toString(line.getOldLineNumber()+1) );
                            linenumAfter.setText( Integer.toString(line.getNewLineNumber()+1) );
                            content.setText(" \t" + line.getContent());
                            break;
                        case ADDED:
                            linenumBefore.setText("");
                            linenumAfter.setText( Integer.toString(line.getNewLineNumber()+1) );
                            content.setText("+\t" + line.getContent());

                            setBackgroundColorForTextViews(lineContainer, Color.parseColor("#D1FFED"));
                            break;
                        case REMOVED:
                            linenumBefore.setText( Integer.toString(line.getOldLineNumber()+1) );
                            linenumAfter.setText("");
                            content.setText("-\t" + line.getContent());

                            setBackgroundColorForTextViews(lineContainer, Color.parseColor("#FFE3EA"));
                            break;
                        case SKIPPED:
                            linenumBefore.setText("");
                            linenumAfter.setText("");

                            content.setText(" \t" + line.getContent());
                            break;
            }
        }
    }

    private void setBackgroundColorForTextViews(LinearLayout lineContainer, int color) {
        float alpha = 0.7f;
        lineContainer.setAlpha(alpha);
        lineContainer.setBackgroundColor(color);
    }

    @Override
    public void showLineNumbers() {
        showLineNumbers = true;
        notifyDataSetChanged();
    }

    @Override
    public void hideLineNumbers() {
        showLineNumbers = false;
        notifyDataSetChanged();
    }
}
