package pl.edu.agh.mobilecodereviewer.view.api;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.model.FileInfo;

/**
 * Represents the view which will show modified files
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public interface ModifiedFilesView {

    /**
     * Shows given list of modified files
     *
     * @param filesList List of modified files
     */
    void showFiles(List<FileInfo> filesList);

}
