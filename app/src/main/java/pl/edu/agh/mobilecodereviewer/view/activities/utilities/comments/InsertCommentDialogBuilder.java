package pl.edu.agh.mobilecodereviewer.view.activities.utilities.comments;

import android.content.Context;

import pl.edu.agh.mobilecodereviewer.controllers.api.CommentsManager;

public class InsertCommentDialogBuilder {
    private final CommentsManager comments_manager;
    private Context parent_activity_context;
    private final int commenting_line_number;
    private String commenting_line_content;

    public InsertCommentDialogBuilder(Context parent_activity_context, CommentsManager comment_manager,
                                      int commenting_line_number, String commenting_line_content) {

        this.parent_activity_context = parent_activity_context;
        this.comments_manager = comment_manager;
        this.commenting_line_number = commenting_line_number;
        this.commenting_line_content = commenting_line_content;
    }

    public boolean showInsertCommentDialog() {
        String comment_description = commenting_line_number + ":   " + commenting_line_content;

        InsertCommentDialog insertCommentDialog = new InsertCommentDialog(parent_activity_context,
                                                                          comment_description,
                                                                          this.comments_manager,
                                                                          commenting_line_number);
        insertCommentDialog.show();
        return true;
    }
}
