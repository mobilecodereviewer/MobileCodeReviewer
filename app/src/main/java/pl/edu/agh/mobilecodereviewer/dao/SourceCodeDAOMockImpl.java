package pl.edu.agh.mobilecodereviewer.dao;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.Line;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;

@Singleton
public class SourceCodeDAOMockImpl implements SourceCodeDAO{

    @Override
    public SourceCode getSourceCode() {
        SourceCode sourceCode = new SourceCode();
        List<Line> lines = Lists.newLinkedList();
        lines.add( Line.valueOf(0,
                                "#include <iostream.h>",
                                Lists.newArrayList( Comment.valueOf("LOL-Noob") )
                  )
        );
        lines.add( Line.valueOf(1,
                                "int main() {} "
                    )
        );
        sourceCode.setLines( lines );
        return sourceCode;
    }
}
