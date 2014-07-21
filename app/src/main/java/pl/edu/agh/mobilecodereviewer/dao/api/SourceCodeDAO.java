package pl.edu.agh.mobilecodereviewer.dao.api;


import pl.edu.agh.mobilecodereviewer.model.SourceCode;

/**
 * Source Code Data Access Object is used for obtaining
 * file with source code,information about it and
 * comments
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public interface SourceCodeDAO {

    /**
     * Method return source code for a given file,at the
     * moment is prepared to be used only for mock,without
     * parameters etc
     * @return {@link pl.edu.agh.mobilecodereviewer.model.SourceCode}
     */
    SourceCode getSourceCode(String change_id,String revision_id,String file_id);

}
