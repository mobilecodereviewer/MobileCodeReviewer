package pl.edu.agh.mobilecodereviewer.view.activities.utilities;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.SourceExplorerController;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.Line;

/**
 * Adapter for a {@link pl.edu.agh.mobilecodereviewer.model.Line} model
 * and popup with comments for a given line
 */
public class SingleLineCommentViewListAdapter extends ArrayAdapter<Comment> {

    private SourceExplorerController controller;

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
    public SingleLineCommentViewListAdapter(Context context,Line line, SourceExplorerController controller) {
        super(context, R.layout.layout_single_line_comment, line.getComments());
        this.line = line;
        this.context = context;
        this.controller = controller;
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


        final View rowView;

        final Comment comment = line.getComments().get(position);

        if(!comment.isDraft()) {
            rowView = inflater.inflate(R.layout.layout_single_line_comment, parent, false);

            TextView commentContent = (TextView) rowView.findViewById(R.id.commentContent);
            TextView authorName = (TextView) rowView.findViewById(R.id.commentAuthorName);
            TextView updated = (TextView) rowView.findViewById(R.id.commentDate);

            commentContent.setText(comment.getContent());
            authorName.setText(comment.getAuthor());
            updated.setText(comment.getUpdated());
        } else {
            rowView = inflater.inflate(R.layout.layout_single_line_pending_comment, parent, false);

            final TextView commentContent = (TextView) rowView.findViewById(R.id.fileCommentComment);
            commentContent.setText(comment.getContent());

            Button editCommentButton = (Button) rowView.findViewById(R.id.editFileCommentButton);
            final EditText editFileComment = (EditText) rowView.findViewById(R.id.editFileComment);
            editFileComment.clearFocus();

            Button cancelCommentButton = (Button) rowView.findViewById(R.id.cancelFileCommentButton);
            Button discardCommentButton = (Button) rowView.findViewById(R.id.discardFileCommentButton);
            Button saveCommentButton = (Button) rowView.findViewById(R.id.saveFileCommentButton);

            editCommentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View buttonView) {
                    changeMode(rowView, false);
                    editFileComment.setText(comment.getContent());
                }
            });

            cancelCommentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeMode(rowView, true);
                }
            });

            discardCommentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.deleteFileComment( comment );
                    controller.clearCache();
                }
            });

            saveCommentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newContent = editFileComment.getText().toString();

                    if(!comment.getContent().equals(newContent)) {
                        commentContent.setText(newContent);
                        controller.updateFileComment(comment, newContent);
                        controller.clearCache();
                    }

                    changeMode(rowView, true);
                }
            });

        }

        return rowView;
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
}
