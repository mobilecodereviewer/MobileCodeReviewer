package pl.edu.agh.mobilecodereviewer.view.api;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.model.FileInfo;

/**
 * Created by d00d171 on 2014-06-08.
 */
public interface ModifiedFilesView {

    /**
     * Shows given list of modified files
     *
     * @param filesList List of modified files
     */
    void showFiles(List<FileInfo> filesList);

}
