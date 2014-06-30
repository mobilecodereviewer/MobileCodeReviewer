package pl.edu.agh.mobilecodereviewer.dao.gerrit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.AsynchronousRestApi;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.RestApi;
import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.Line;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;


public class SourceCodeDAOImpl implements SourceCodeDAO {
    private RestApi restApi;

    public SourceCodeDAOImpl() {
        this( new AsynchronousRestApi( new RestApi() ) );
    }

    public SourceCodeDAOImpl(RestApi restApi) {
        this.restApi = restApi;
    }

    protected String downloadSourceCode(final String change_id,final String revision_id,final String file_id) {
        return restApi.getFileContent(change_id,revision_id,file_id);
    }

    protected String convertSourceCode(String source) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(source) );
    }

    // this maybe seem like overkill but bufferedreader has logic to
    // split lines for many types of newlines characters for many operating systems
    protected List<String> splitSourceIntoLines(String source) {
        try {
            BufferedReader buffReader = new BufferedReader(new StringReader(source));
            List<String> lines = new ArrayList<>();
            for (String line = buffReader.readLine(); line != null; line = buffReader.readLine()) {
                lines.add(line);
            }
            return lines;
        } catch (IOException ioexception ) {
            return null; // i cant imagine situation in which this would be invoked...
        }
    }

    protected List<CommentInfoDTO> getComments(final String change_id,final String revision_id,
                                               final String file_id) {
        Map<String, List<CommentInfoDTO>> comments = restApi.getComments(change_id, revision_id);
        if ( comments.containsKey(file_id) )
            return comments.get(file_id);
        else
            return new LinkedList<CommentInfoDTO>();
    }

    protected Map<Integer,List<CommentInfoDTO>> mapCommentsIntoLines(List<CommentInfoDTO> comments) {
        Map<Integer,List<CommentInfoDTO>> mappedComments = new HashMap<>();
        for ( CommentInfoDTO comment : comments) {
            int line = comment.getLine();
            if ( mappedComments.get(line) != null)
                mappedComments.get(line).add(comment );
            else {
                List<CommentInfoDTO> lineComments = new LinkedList<>();
                lineComments.add(comment);
                mappedComments.put(line,lineComments);
            }
        }
        return mappedComments;
    }

    protected SourceCode createSourceCode(List<String> lines, Map<Integer,List<CommentInfoDTO>> comments) {
        List<Line> sourceLines = new LinkedList<Line>() ;
        int i = 1;
        for (String line : lines) {
            List<Comment> lineComments = new LinkedList<>();
            if ( comments.containsKey(i) )
                for (CommentInfoDTO commentInfoDTO : comments.get(i) )
                        lineComments.add( new Comment(commentInfoDTO.getMessage() ) );

            Line sourceLine = new Line(i,line,lineComments );
            sourceLines.add(sourceLine);
            i++;
        }
        return new SourceCode(sourceLines);
    }

    @Override
    public SourceCode getSourceCode(String change_id,String revision_id,String file_id) {
        List<String> lines = splitSourceIntoLines(
                convertSourceCode( downloadSourceCode(change_id,revision_id,file_id) )
        );
        Map<Integer, List<CommentInfoDTO>> commentsInLines = mapCommentsIntoLines(
                getComments(change_id, revision_id, file_id)
        );
        return createSourceCode(lines,commentsInLines);
    }

}



















