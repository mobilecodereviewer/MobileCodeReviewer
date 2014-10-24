package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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

    /**
     * List of lines content from a given {@link pl.edu.agh.mobilecodereviewer.model.SourceCode}
     */
    private final List<String> content;

    private final String[] htmlContent;

    /**
     * List of information if given line has comment
     */
    private final List<Boolean> hasComments;
    private final SourceCode sourceCode;

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

        this.content = SourceCodeHelper.getContent(sourceCode);
        this.hasComments = SourceCodeHelper.getHasLineComments(sourceCode);
        this.sourceCode = sourceCode;
        this.showLineNumbers = false;

        SyntaxHighlighter prettifyHighlighter = new PrettifyHighlighter();
        StringBuilder joinedSourceBuilder = new StringBuilder();
        for (String sourceLine : this.content) {
            joinedSourceBuilder.append(sourceLine + "\n");
        }
        String joinedSourceCode = joinedSourceBuilder.toString();
        String prettifiedSourceCode = prettifyHighlighter.highlight(joinedSourceCode, extension);
        this.htmlContent =  prettifiedSourceCode.split("\n");
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
        TextView txtCodeContent = (TextView) rowView.findViewById(R.id.codeText);
        TextView txtNumber = (TextView) rowView.findViewById(R.id.lineNumberText);
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.commentImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setEnabled(false);
                if ( sourceCode.getLine(position+1).hasComments()) {
                    View lineCommentsView = context.getLayoutInflater().inflate(R.layout.layout_line_comments, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setView(lineCommentsView);

                    ListView listView = (ListView) lineCommentsView.findViewById(R.id.lineCommentsList);
                    listView.setAdapter(new SingleLineCommentViewListAdapter(context, sourceCode.getLine(position+1) ) );

                    AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            imageView.setEnabled(true);
                        }
                    });
                    alertDialog.show();
                }

            }
        });
        txtNumber.setText( Integer.toString(position+1) );
        String htmlCode = htmlContent[position];
        setCodeTextViewContent(txtCodeContent, htmlCode);
        if ( hasComments.get(position) )
            imageView.setImageResource( R.drawable.source_explorer_line_comment_icon );
        else
            imageView.setImageDrawable(null);
        if (!showLineNumbers)
            txtNumber.setVisibility(View.GONE);
        return rowView;
    }

    private void setCodeTextViewContent(TextView txtCodeContent, String highlightCode) {
        txtCodeContent.setText( Html.fromHtml(" \t" + highlightCode) );
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
