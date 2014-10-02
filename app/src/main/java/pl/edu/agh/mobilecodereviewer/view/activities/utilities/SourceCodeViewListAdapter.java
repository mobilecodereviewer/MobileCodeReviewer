package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.model.utilities.SourceCodeHelper;

/**
 * SourceCodeViewListAdapter adapts class {@link pl.edu.agh.mobilecodereviewer.model.SourceCode}
 * to the appropriate objects ready to be shown in view.
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public class SourceCodeViewListAdapter extends ArrayAdapter<String> {
    /**
     * Activity in which source code view list adapter will be used
     */
    private final Activity context;

    /**
     * List of lines content from a given {@link pl.edu.agh.mobilecodereviewer.model.SourceCode}
     */
    private final List<String> content;

    /**
     * List of information if given line has comment
     */
    private final List<Boolean> hasComments;
    private final SourceCode sourceCode;

    /**
     * Construct Adapter from given activity and sourcecode
     * @param context {@link android.app.Activity}
     * @param sourceCode {@link pl.edu.agh.mobilecodereviewer.model.SourceCode}
     */
    public SourceCodeViewListAdapter(Activity context,
                                     SourceCode sourceCode) {
        super(context, R.layout.layout_source_line, SourceCodeHelper.getContent(sourceCode) );
        this.context = context;

        this.content = SourceCodeHelper.getContent(sourceCode);
        this.hasComments = SourceCodeHelper.getHasLineComments(sourceCode);
        this.sourceCode = sourceCode;
    }

    /**
     * Adapts data to be shown in layout_source_line layout from given position and view
     * @param position position to adapts view
     * @param view {@link android.view.View}
     * @param parent {@link android.view.ViewGroup}
     * @return Constructed view adapted from given data
     */
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout_source_line, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.codeText);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.commentImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( sourceCode.getLine(position+1).hasComments() ) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.layout_line_comments);
                    dialog.setTitle("Comments");

                    ListView listView = (ListView) dialog.findViewById(R.id.lineCommentsList);
                    listView.setAdapter(new SingleLineCommentViewListAdapter(context, sourceCode.getLine(position+1) ) );
                    dialog.show();
                }
            }
        });
        txtTitle.setText((position+1) + "|\t" +  content.get(position) );
        if ( hasComments.get(position) )
            imageView.setImageResource( R.drawable.source_explorer_line_comment_icon );
        else
            imageView.setImageDrawable(null);
        return rowView;
    }
}
