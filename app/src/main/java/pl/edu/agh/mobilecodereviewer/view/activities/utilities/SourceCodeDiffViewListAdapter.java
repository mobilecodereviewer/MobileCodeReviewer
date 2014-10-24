package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.model.DiffedLine;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;
import pl.edu.agh.mobilecodereviewer.model.utilities.SourceCodeDiffHelper;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.syntax.PrettifyHighlighter;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.syntax.SyntaxHighlighter;

public class SourceCodeDiffViewListAdapter extends ArrayAdapter<String>  implements SourceCodeListAdapter {

    private final Activity context;
    private final SourceCodeDiff sourceCodeDiff;
    private boolean showLineNumbers;
    private String htmlContent[];

    public SourceCodeDiffViewListAdapter(Activity context,
                                         String extension,SourceCodeDiff sourceCodeDiff) {
        super(context, R.layout.layout_source_diff_line, SourceCodeDiffHelper.getContent(sourceCodeDiff));
        this.context = context;
        this.sourceCodeDiff = sourceCodeDiff;
        this.htmlContent = buildHtmlContentOfLines( extension , SourceCodeDiffHelper.getContent(sourceCodeDiff) );

        showLineNumbers = false;
    }

    private String[] buildHtmlContentOfLines(String extension,List<String> content) {
        SyntaxHighlighter prettifyHighlighter = new PrettifyHighlighter();
        StringBuilder joinedSourceBuilder = new StringBuilder();
        for (String sourceLine : content) {
            joinedSourceBuilder.append(sourceLine + "\n");
        }
        String joinedSourceCode = joinedSourceBuilder.toString();
        String prettifiedSourceCode = prettifyHighlighter.highlight(joinedSourceCode, extension);
        return prettifiedSourceCode.split("\n");
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout_source_diff_line, null, true);

        TextView txtContent = (TextView) rowView.findViewById(R.id.codeDiffText);
        TextView txtLineNumberBeforeChange = (TextView) rowView.findViewById(R.id.lineNumberBeforeChangeText);
        TextView txtLineNumberAfterChange = (TextView) rowView.findViewById(R.id.lineNumberAfterChangeText);

        LinearLayout lineContainer = (LinearLayout) rowView.findViewById(R.id.diffLineMainLayout);

        if ( sourceCodeDiff != null) {
            DiffedLine line = sourceCodeDiff.getLine(position);
            writeLineToTextView(position,lineContainer, txtContent,txtLineNumberBeforeChange,txtLineNumberAfterChange, line ) ;
        }

        if (!showLineNumbers) {
            txtLineNumberBeforeChange.setVisibility(View.GONE);
            txtLineNumberAfterChange.setVisibility(View.GONE);
        }

        return rowView;
    }

    private void writeLineToTextView(int position,LinearLayout lineContainer,
                                     TextView content, TextView linenumBefore,
                                     TextView linenumAfter, DiffedLine line) {
        if (line == null) {
            linenumBefore.setText("");
            linenumAfter.setText("");
            content.setText("");
        } else {
            switch (line.getLineType()) {
                        case UNCHANGED:
                            linenumBefore.setText( Integer.toString(line.getOldLineNumber()+1) );
                            linenumAfter.setText( Integer.toString(line.getNewLineNumber()+1) );
                            setCodeTextViewContent(content, " \t" ,position);
                            break;
                        case ADDED:
                            linenumBefore.setText("");
                            linenumAfter.setText(Integer.toString(line.getNewLineNumber() + 1));
                            setCodeTextViewContent(content, "+\t" ,position);

                            setBackgroundColorForTextViews(lineContainer, Color.parseColor("#D1FFED"));
                            break;
                        case REMOVED:
                            linenumBefore.setText( Integer.toString(line.getOldLineNumber()+1) );
                            linenumAfter.setText("");
                            setCodeTextViewContent(content, "-\t" ,position);

                            setBackgroundColorForTextViews(lineContainer, Color.parseColor("#FFE3EA"));
                            break;
                        case SKIPPED:
                            linenumBefore.setText("");
                            linenumAfter.setText("");

                            setCodeTextViewContent(content, " \t" , position);
                            break;
            }
        }
    }

    @Override
    public void setCodeTextViewContent(TextView content, String prefix, int position) {
        String linePrettyfied = htmlContent[position];
        content.setText(Html.fromHtml(prefix + linePrettyfied));
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
