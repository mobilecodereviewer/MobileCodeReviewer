package pl.edu.agh.mobilecodereviewer.view.api;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.model.ChangeMessageInfo;

public interface ChangeMessagesTabView {
    void showMessages(List<ChangeMessageInfo> messages);
}
