package pl.edu.agh.mobilecodereviewer.controllers;

import org.junit.Test;

import pl.edu.agh.mobilecodereviewer.controllers.api.SourceExplorerController;
import pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;
import pl.edu.agh.mobilecodereviewer.view.api.SourceExplorerView;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SourceExplorerControllerImplTest {

    @Test
    public void shouldSourceExplorerObtainAndRenderSourceDiff() throws Exception {
        String change_id = "change";
        String revision_id = "revision";
        String file_id = "file";
        SourceCodeDAO sourceCodeDAO = mock(SourceCodeDAO.class);
        SourceCodeDiff sourceCodeDiff = new SourceCodeDiff(null);
        when( sourceCodeDAO.getSourceCodeDiff(change_id,revision_id,file_id) ).thenReturn(sourceCodeDiff);
        SourceExplorerView sourceExplorerView = mock(SourceExplorerView.class);
        doNothing().when( sourceExplorerView ).showSourceCodeDiff(sourceCodeDiff);
        SourceExplorerController sourceExplorerController = new SourceExplorerControllerImpl(sourceCodeDAO);

        assertNotNull( sourceCodeDAO);
        sourceExplorerController.updateSourceCodeDiff(sourceExplorerView, change_id, revision_id, file_id);

        verify(sourceCodeDAO).getSourceCodeDiff(change_id, revision_id, file_id);
        verify(sourceExplorerView).showSourceCodeDiff(sourceCodeDiff);
    }

}