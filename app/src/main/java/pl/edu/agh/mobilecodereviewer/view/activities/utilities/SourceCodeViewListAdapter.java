package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.Html;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.model.utilities.SourceCodeHelper;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.syntax.PrettifyHighlighter;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.syntax.SyntaxHighlighter;
import prettify.PrettifyParser;
import prettify.theme.ThemeDefault;
import roboguice.util.Strings;
import syntaxhighlight.ParseResult;
import syntaxhighlight.Parser;

/**
 * SourceCodeViewListAdapter adapts class {@link pl.edu.agh.mobilecodereviewer.model.SourceCode}
 * to the appropriate objects ready to be shown in view.
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public class SourceCodeViewListAdapter extends ArrayAdapter<String> implements SourceCodeListAdapter{
    /**
     * Activity in which source code view list adapter will be used
     */
    private final Activity context;

    private final String[] htmlContent;

    /**
     * List of information if given line has comment
     */
    private final List<Boolean> hasComments;
    private final List<Boolean> hasPendingComments;
    private final SourceCode sourceCode;
    private final String extension;

    boolean showLineNumbers;

    /**
     * Construct Adapter from given activity and sourcecode
     * @param context {@link android.app.Activity}
     * @param extension
     * @param sourceCode {@link pl.edu.agh.mobilecodereviewer.model.SourceCode}
     */
    public SourceCodeViewListAdapter(Activity context,
                                     String extension, SourceCode sourceCode) {
        super(context, R.layout.layout_source_line, SourceCodeHelper.getContent(sourceCode) );
        this.context = context;

        this.hasComments = SourceCodeHelper.getHasLineComments(sourceCode);
        this.hasPendingComments = SourceCodeHelper.getHasLinePendingComments(sourceCode);
        this.sourceCode = sourceCode;
        this.showLineNumbers = false;
        this.extension = extension;

        HtmlPrettifier htmlPretty = new HtmlPrettifier(this.extension,SourceCodeHelper.getContent(sourceCode) );
        this.htmlContent = htmlPretty.buildHtmlContent();
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

        LinearLayout lineContainer = (LinearLayout) rowView.findViewById(R.id.sourceLineMainLayout);

        TextView txtContent = (TextView) rowView.findViewById(R.id.codeText);
        TextView txtNumber = (TextView) rowView.findViewById(R.id.lineNumberText);

        if ( hasComments.get(position) ) {
            float alpha = 0.7f;
            lineContainer.setAlpha(alpha);
            lineContainer.setBackgroundColor( Color.YELLOW );
            txtContent.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        }

        if(hasPendingComments.get(position)) {
            lineContainer.setBackgroundColor(Color.CYAN);
            txtContent.setPaintFlags(txtContent.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        }

        txtNumber.setText( Integer.toString(position+1) );
        setCodeTextViewContent(txtContent," \t", position);

        if (!showLineNumbers)
            txtNumber.setVisibility(View.GONE);
        return rowView;
    }

    @Override
    public void setCodeTextViewContent(TextView txtCodeContent,String prefix,int position) {
        String htmlCode = htmlContent[position];
        txtCodeContent.setText( Html.fromHtml(prefix + htmlCode) );
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
