package pl.edu.agh.mobilecodereviewer.model.utilities;

import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.utilities.ConfigurationContainer;
import pl.edu.agh.mobilecodereviewer.utilities.DateUtils;

public class CommentHelper {

    public static Comment commentFromDTO(CommentInfoDTO commentInfoDTO, String path, boolean draft){
        Comment result =  Comment.valueOf(commentInfoDTO.getLine(), path,commentInfoDTO.getMessage(), null, DateUtils.getPrettyDate(commentInfoDTO.getUpdated()));
        if(draft) {
            result.setDraftId(commentInfoDTO.getId());
            result.setDraft(true);
            result.setAuthor(ConfigurationContainer.getInstance().getLoggedUser().getName());
        } else {
            result.setAuthor(commentInfoDTO.getAuthor().getName());
        }
        return result;
    }

}
