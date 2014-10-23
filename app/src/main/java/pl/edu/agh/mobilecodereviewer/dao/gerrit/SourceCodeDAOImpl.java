package pl.edu.agh.mobilecodereviewer.dao.gerrit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.Base64;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.RestApi;
import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.DiffInfoDTO;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.Line;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;
import pl.edu.agh.mobilecodereviewer.utilities.DateUtils;

/**
 * Data access object for a source code. It is some
 * kind of adapter between data returned by gerrit instance
 * and business model
 */
@Singleton
public class SourceCodeDAOImpl implements SourceCodeDAO {
    /**
     * {@link pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.RestApi}
     */
    private RestApi restApi;

    @Override
    public void initialize(RestApi restApi){
        this.restApi = restApi;
    }

    /**
     * Construct object with default {@link pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.RestApi}
     */
    public SourceCodeDAOImpl() {

    }

    /**
     * Construct object from given {@link pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.RestApi}
     * @param restApi {@link pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.RestApi}
     */
    public SourceCodeDAOImpl(RestApi restApi) {
        this.restApi = restApi;
    }

    /**
     * Download a source code
     * @param change_id Identifier of the change
     * @param revision_id Identifier of the revision
     * @param file_id Identifier of the file
     * @return Source code in Base64 compressed
     */
    protected String downloadSourceCode(final String change_id,final String revision_id,final String file_id) {
        return restApi.getFileContent(change_id,revision_id,file_id);
    }


    /**
     * Convert source code from Base64 to UTF-8
     * @param source Value of the source
     * @return Converted source code in UTF-8
     */
    protected String convertSourceCode(String source) {
        if (source != null) {
            try {
                return new String(Base64.decode(source), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Split source into list of lines
     * @param source Value of the source
     * @return List of lines
     */
    protected List<String> splitSourceIntoLines(String source) {
        // this maybe seem like overkill but bufferedreader has logic to
        // split lines for many types of newlines characters for many operating systems
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

    /**
     * Get all comments for a given file in revision of change
     * @param change_id Identifier of the change
     * @param revision_id Identifier of the revision
     * @param file_id Identifier of the file
     * @return List of {@link pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO}
     */
    public List<CommentInfoDTO> getComments(final String change_id,final String revision_id,
                                               final String file_id) {
        Map<String, List<CommentInfoDTO>> comments = restApi.getComments(change_id, revision_id);
        if ( comments.containsKey(file_id) )
            return comments.get(file_id);
        else
            return new LinkedList<CommentInfoDTO>();
    }

    /**
     * Map list of comments in association between line number and list of comments( sounds legit..)
     * @param comments List of comments
     * @return Mapping between line number and list of comments
     */
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

    /**
     * Create {@link SourceCode} model from lines of files and map between
     * line numbers and comments
     *
     * @param path
     * @param lines Lines of file
     * @param comments Map between line number and list of comments
     * @return {@link pl.edu.agh.mobilecodereviewer.model.SourceCode} model
     */
    protected SourceCode createSourceCode(String path, List<String> lines, Map<Integer, List<CommentInfoDTO>> comments) {
        List<Line> sourceLines = new LinkedList<Line>() ;
        int i = 1;
        for (String line : lines) {
            List<Comment> lineComments = new LinkedList<>();
            if ( comments.containsKey(i) )
                for (CommentInfoDTO commentInfoDTO : comments.get(i) )
                        lineComments.add( new Comment(i,path,commentInfoDTO.getMessage(), commentInfoDTO.getAuthor().getName(), DateUtils.getPrettyDate(commentInfoDTO.getUpdated()) ) );

            Line sourceLine = new Line(i,line,lineComments );
            sourceLines.add(sourceLine);
            i++;
        }
        return new SourceCode(sourceLines);
    }


    /**
     *  Get {@link pl.edu.agh.mobilecodereviewer.model.SourceCode} from parameters
     *  @param change_id Identifier of the change
     *  @param revision_id Identifier of the revision
     *  @param file_id Identifier of the file
     *  @return {@link pl.edu.agh.mobilecodereviewer.model.SourceCode}
     */
    @Override
    public SourceCode getSourceCode(String change_id,String revision_id,String file_id) {
        List<String> lines = splitSourceIntoLines(
                convertSourceCode( downloadSourceCode(change_id,revision_id,file_id) )
        );
        Map<Integer, List<CommentInfoDTO>> commentsInLines = mapCommentsIntoLines(
                getComments(change_id, revision_id, file_id)
        );
        return createSourceCode(file_id,lines,commentsInLines);
    }

    @Override
    public SourceCodeDiff getSourceCodeDiff(String change_id, String revision_id, String file_id) {
        DiffInfoDTO sourceCodeDiff = restApi.getSourceCodeDiff(change_id, revision_id, file_id);
        return new SourceCodeDiff(sourceCodeDiff);
    }


    @Override
    public void putFileComment(String change_id, String revision_id,Comment comment) {
        int line = comment.getLine();
        String message = comment.getContent();
        String path = comment.getPath();
        restApi.putFileComment(change_id,revision_id,line,message,path);
    }
}



















