package pl.edu.agh.mobilecodereviewer.dao.mock;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;

import java.util.Arrays;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.RestApi;
import pl.edu.agh.mobilecodereviewer.dto.DiffContentDTO;
import pl.edu.agh.mobilecodereviewer.dto.DiffInfoDTO;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.Line;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;

/**
 * Simple Stub for SourceCodeDAO with hardcoded values about
 * lines,comments and number of line. The api is expected to change
 * and is expected to use only for mock
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
@Singleton
public class SourceCodeDAOMockImpl implements SourceCodeDAO{

    /**
     * Hardcoded information about lines
     */
    private static List<Line> lines = Arrays.asList(
            Line.valueOf(0,"#include <iostream.h>", Lists.newArrayList( Comment.valueOf(0,"file","Comment for include", null, null) ) ),
            Line.valueOf(1,""),
            Line.valueOf(2,"using namespace sdt;"),
            Line.valueOf(3,""),
            Line.valueOf(4,"int main() {" , Lists.newArrayList( Comment.valueOf(4,"file","int main() {", null, null) )),
            Line.valueOf(5,"\tcout<<\"Hello World\"\n"),
            Line.valueOf(6,"}")
    );

    private static DiffInfoDTO diffInfoDTO =
            new DiffInfoDTO(
                    Arrays.asList(
                            new DiffContentDTO(null, null, Arrays.asList("while (m > 1) {"), 0),
                            new DiffContentDTO(null, null, null, 3),
                            new DiffContentDTO(Arrays.asList("int l;", "int w;"), null, null, 0),
                            new DiffContentDTO(null, Arrays.asList("int h;"), null, 0),
                            new DiffContentDTO(null, null, Arrays.asList("super();"), 0)
                    )
            );



    /**
     * Return {@link pl.edu.agh.mobilecodereviewer.model.SourceCode} with hardcoded
     * values ( {@link pl.edu.agh.mobilecodereviewer.model.Line}. The method is
     * going to have some argument indicating file,project etc
     * @return Source Code( {@link pl.edu.agh.mobilecodereviewer.model.SourceCode} ) assigned to given file
     */
    @Override
    public SourceCode getSourceCode(String change_id,String revision_id,String file_id) {
        return SourceCode.valueOf( lines );
    }

    @Override
    public SourceCodeDiff getSourceCodeDiff(String change_id, String revision_id, String file_id) {
        return new SourceCodeDiff(diffInfoDTO);
    }

    @Override
    public void putFileComment(String change_id, String revision_id, Comment comment) {
        // TODO something to show on the screen....
    }

    @Override
    public void initialize(RestApi restApi) {
        
    }
}
















