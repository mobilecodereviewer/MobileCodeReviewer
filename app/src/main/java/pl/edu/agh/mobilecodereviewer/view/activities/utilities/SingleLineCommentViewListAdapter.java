package pl.edu.agh.mobilecodereviewer.view.activities.utilities;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.Line;

/**
 * Adapter for a {@link pl.edu.agh.mobilecodereviewer.model.Line} model
 * and popup with comments for a given line
 */
public class SingleLineCommentViewListAdapter extends ArrayAdapter<String> {
    /**
     * Android context of execution
     */
    private Context context;

    /**
     * Line which will be adapted
     */
    private Line line;

    /**
     * Create Adapter from android context and line
     * @param context android context of execution
     * @param line Line which will be adapter
     */
    public SingleLineCommentViewListAdapter(Context context,Line line) {
        super(context, R.layout.layout_single_line_comment,
                Lists.transform(line.getComments(),new Function<Comment, String>() {
                    @Override
                    public String apply(Comment from) {
                        return from.getContent();
                    }
                }));

        this.line = line;
        this.context = context;
    }

    /**
     * Method fills data in view for a single row
     * @param position line number
     * @param convertView not used in method
     * @param parent not used in method
     * @return constructed row with single comment
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.layout_single_line_comment, parent, false);

        String comment = line.getComments().get(position).getContent();
        TextView textView = (TextView) rowView.findViewById(R.id.singleCommentLineText);
        textView.setText( comment );
        return rowView;
    }
}
