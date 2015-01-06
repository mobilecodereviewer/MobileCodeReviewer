package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.model.Comment;


public interface CommentsManager {
    void insertComment(String content, int lineNumber);

    void deleteFileComment(Comment comment);

    void updateFileComment(Comment comment, String content);
}
