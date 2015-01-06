package pl.edu.agh.mobilecodereviewer.view.activities.utilities.comments;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.CommentsManager;


public class InsertCommentDialog extends Dialog {

    public static final String INSERT_COMMENT_DIALOG_TITLE = "Write comment...";

    private final CommentsManager commentsManager;
    private final int lineNumber;

    public InsertCommentDialog(Context context, String commenting_line_description,
                               final CommentsManager commentsManager,
                               final int lineNumber) {
        super(context);
        this.commentsManager = commentsManager;
        this.lineNumber = lineNumber;

        setTitle(INSERT_COMMENT_DIALOG_TITLE);
        setContentView(R.layout.layout_add_comment);

        TabHost tabHost = setUpTabHost();

        addManualTab(context, tabHost);
        addPredefinedTabs(context, tabHost);

        showCommentedLine(commenting_line_description);
    }

    public TabHost setUpTabHost() {
        TabHost tabHost = (TabHost)findViewById(R.id.typeOfInsertingCommentTabHost);
        tabHost.setup();
        return tabHost;
    }

    public void showCommentedLine(String commenting_line_description) {
        TextView selectedCodeLine = (TextView) findViewById(R.id.selectedCodeLine);
        selectedCodeLine.setText(commenting_line_description);
    }

    public void addPredefinedTabs(Context context, TabHost tabHost) {
        addStyle(context, tabHost);
        addErrorTab(context, tabHost);
        addOtherTab(context, tabHost);
    }

    public void addOtherTab(Context context, TabHost tabhost) {
        final String[] other_comments = {
                "Typo",
                "Obscure Code",
                "Use existing code",
                "Useless comment"
        };
        addCommentTab(context,tabhost,"Other",
                android.R.drawable.ic_menu_help,
                R.id.otherCommentTypeListView,
                other_comments);
    }

    public void addErrorTab(Context context, TabHost tabHost) {
        final String[] error_comments = {
                "Compilation Error",
                "Duplicated Code",
                "Unhandled Exception",
                "Unused argument/parameter",
                "Code is unreachable",
        };
        addCommentTab(context,tabHost,"Error",
                android.R.drawable.ic_menu_close_clear_cancel,
                R.id.errorCommentTypeListView,
                error_comments);
    }

    public void addStyle(Context context, TabHost tabHost) {
        final String[] style_comments = {
                "Magic Number",
                "Inappropriate Name",
                "Extract constant",
                "Extract method",
                "Introduce explaining variable",
        };
        addCommentTab(context,tabHost,"Style",
                      android.R.drawable.ic_menu_report_image,
                      R.id.styleCommentTypeListView,
                      style_comments);
    }


    public void addCommentTab(Context context,TabHost tabHost,
                              String tabName,final int iconResourceId,
                              final int commentListResourceId,final String[] comments) {
        TabHost.TabSpec spec2 = tabHost.newTabSpec(tabName);
        spec2.setIndicator(null,
                context.getResources().getDrawable(iconResourceId));
        spec2.setContent(commentListResourceId);
        tabHost.addTab(spec2);

        ListView styleCommentsListView = (ListView) findViewById(commentListResourceId);

        ArrayAdapter<String> comments_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1,
                comments);
        styleCommentsListView.setAdapter(comments_adapter);
        styleCommentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                commentsManager.insertComment(comments[position],lineNumber);
                dismiss();
            }
        });
    }

    public void addManualTab(Context context, TabHost tabHost) {
        TabHost.TabSpec spec1 = tabHost.newTabSpec("Manual");
        spec1.setIndicator(null,
                           context.getResources().getDrawable(android.R.drawable.ic_menu_edit) );
        spec1.setContent(R.id.commentsByHandLayout);
        final EditText commentContent = (EditText) findViewById(R.id.commentContentTextView);
        final Button saveCommentContent = (Button) findViewById(R.id.saveWrittenCommentButton);
        saveCommentContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = commentContent.getText().toString();
                commentsManager.insertComment(content, lineNumber);
                dismiss();
            }
        });
        tabHost.addTab(spec1);
    }


}
