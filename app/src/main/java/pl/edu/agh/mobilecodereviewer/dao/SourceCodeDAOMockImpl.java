package pl.edu.agh.mobilecodereviewer.dao;

import com.google.inject.Singleton;

import pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;

@Singleton
public class SourceCodeDAOMockImpl implements SourceCodeDAO{

    @Override
    public SourceCode getSourceCode() {
        return null;
    }
}
