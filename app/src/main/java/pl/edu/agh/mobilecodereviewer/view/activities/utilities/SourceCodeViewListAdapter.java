package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

    /**
     * Construct Adapter from given activity and sourcecode
     * @param context {@link android.app.Activity}
     * @param sourceCode {@link pl.edu.agh.mobilecodereviewer.model.SourceCode}
     */
    public SourceCodeViewListAdapter(Activity context,
                                     SourceCode sourceCode) {
        super(context, R.layout.layout_source_code , SourceCodeHelper.getContent(sourceCode) );
        this.context = context;

        this.content = SourceCodeHelper.getContent(sourceCode);
        this.hasComments = SourceCodeHelper.getHasLineComments(sourceCode);
    }

    /**
     * Adapts data to be shown in layout_source_code layout from given position and view
     * @param position position to adapts view
     * @param view {@link android.view.View}
     * @param parent {@link android.view.ViewGroup}
     * @return Constructed view adapted from given data
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout_source_code , null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.codeText);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.commentImage);
        txtTitle.setText(content.get(position) );
        if ( hasComments.get(position) )
            imageView.setImageResource( R.drawable.source_code_info_icon );
        else
            imageView.setImageDrawable(null);
        return rowView;
    }
}
