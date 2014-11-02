package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.ChangeDetailsController;
import pl.edu.agh.mobilecodereviewer.model.Comment;

public class AddReviewFilesExpandableListAdapter extends BaseExpandableListAdapter {

    private ChangeDetailsController controller;

    private final Activity context;

    private final Map<String, List<Comment>> pendingComments;

    private final List<String> files;


    public AddReviewFilesExpandableListAdapter(Activity context, ChangeDetailsController controller, Map<String, List<Comment>> pendingComments) {
        this.context = context;
        this.controller = controller;
        this.pendingComments = pendingComments;
        this.files = new ArrayList<String>(pendingComments.keySet());
    }

    @Override
    public int getGroupCount() {
        return files.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return pendingComments.get(files.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return files.get(i);
    }

    @Override
    public Object getChild(int i, int i2) {
        return pendingComments.get(files.get(i)).get(i2);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i2) {
       return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = context.getLayoutInflater();

        if(view == null){
            view = inflater.inflate(R.layout.layout_add_review_file, null);
        }

        TextView fileNameView = (TextView) view.findViewById(R.id.commentedFileName);
        fileNameView.setText(files.get(i));

        return view;
    }

    @Override
    public View getChildView(final int i, final int i2, boolean b, final View view, ViewGroup viewGroup) {
        LayoutInflater inflater = context.getLayoutInflater();

        final View resultView = inflater.inflate(R.layout.layout_add_review_file_comment, null);

        TextView line = (TextView) resultView.findViewById(R.id.fileCommentLine);
        TextView content = (TextView) resultView.findViewById(R.id.fileCommentComment);

        line.setText( String.valueOf(pendingComments.get(files.get(i)).get(i2).getLine()) );
        content.setText(pendingComments.get(files.get(i)).get(i2).getContent());

        changeMode(resultView, true);

        Button editCommentButton = (Button) resultView.findViewById(R.id.editFileCommentButton);
        final EditText editFileComment = (EditText) resultView.findViewById(R.id.editFileComment);

        Button cancelCommentButton = (Button) resultView.findViewById(R.id.cancelFileCommentButton);
        Button discardCommentButton = (Button) resultView.findViewById(R.id.discardFileCommentButton);
        Button saveCommentButton = (Button) resultView.findViewById(R.id.saveFileCommentButton);

        editCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {
                changeMode(resultView, false);
                editFileComment.setText(pendingComments.get(files.get(i)).get(i2).getContent());
            }
        });

        cancelCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMode(resultView, true);
            }
        });

        discardCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = files.get(i);
                controller.deleteFileComment(path, pendingComments.get(path).get(i2));
            }
        });

        saveCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = files.get(i);
                Comment oldComment = pendingComments.get(path).get(i2);
                String newContent = editFileComment.getText().toString();

                if(!oldComment.getContent().equals(newContent)) {
                    controller.updateFileComment(path, oldComment, newContent);

                } else {
                    changeMode(resultView, true);
                }
            }
        });

        return resultView;
    }

    private void changeMode(View view, boolean viewMode){
        TextView content = (TextView) view.findViewById(R.id.fileCommentComment);
        Button editCommentButton = (Button) view.findViewById(R.id.editFileCommentButton);
        EditText editFileComment = (EditText) view.findViewById(R.id.editFileComment);
        Button cancelCommentButton = (Button) view.findViewById(R.id.cancelFileCommentButton);
        Button discardCommentButton = (Button) view.findViewById(R.id.discardFileCommentButton);
        Button saveCommentButton = (Button) view.findViewById(R.id.saveFileCommentButton);

        if(viewMode){
            content.setVisibility(View.VISIBLE);
            editCommentButton.setVisibility(View.VISIBLE);
            editFileComment.setVisibility(View.INVISIBLE);
            cancelCommentButton.setVisibility(View.INVISIBLE);
            discardCommentButton.setVisibility(View.INVISIBLE);
            saveCommentButton.setVisibility(View.INVISIBLE);
        } else {
            content.setVisibility(View.INVISIBLE);
            editCommentButton.setVisibility(View.INVISIBLE);
            editFileComment.setVisibility(View.VISIBLE);
            cancelCommentButton.setVisibility(View.VISIBLE);
            discardCommentButton.setVisibility(View.VISIBLE);
            saveCommentButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }
}