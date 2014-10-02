package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.model.DiffLine;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;

/**
 * Created by lee on 2014-09-17.
 */
public class SourceCodeDiffViewListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final SourceCodeDiff sourceCodeDiff;

    public SourceCodeDiffViewListAdapter(Activity context,
                                         SourceCodeDiff sourceCodeDiff) {
        super(context, R.layout.layout_source_line, new String[sourceCodeDiff.getLinesCount()]);
        this.context = context;
        this.sourceCodeDiff = sourceCodeDiff;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout_source_line, null, true);

        TextView txtLine = (TextView) rowView.findViewById(R.id.codeText);
        ImageView imgComment = (ImageView) rowView.findViewById(R.id.commentImage);

        if ( sourceCodeDiff != null) {
            writeLineToTextView(txtLine, sourceCodeDiff.getLine(position)) ;
        }
        imgComment.setImageDrawable(null);
        return rowView;
    }

    private void writeLineToTextView(TextView textView,DiffLine line) {
        if (line == null) {
            textView.setText("");
        } else {
            switch (line.getLineType()) {
                        case UNCHANGED:
                            textView.setText(line.getLineNumber() + "|\t" + line.getContent());
                            break;
                        case ADDED:
                            textView.setTextColor( context.getResources().getColor( android.R.color.holo_green_dark ));
                            textView.setText(line.getLineNumber() + "|\t++" + line.getContent());
                            break;
                        case REMOVED:
                            textView.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
                            textView.setText(line.getLineNumber() + "|\t--" + line.getContent());
                            break;
                        case SKIPPED:
                            textView.setText(line.getLineNumber() + "|\t" + line.getContent());
                            break;
                        default:
                            textView.setText("");
            }
        }
    }
}
