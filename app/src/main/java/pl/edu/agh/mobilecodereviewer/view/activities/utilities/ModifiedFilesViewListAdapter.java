package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.model.ApprovalInfo;
import pl.edu.agh.mobilecodereviewer.model.FileInfo;

public class ModifiedFilesViewListAdapter extends ArrayAdapter<FileInfo> {

    private final Activity context;

    private final List<FileInfo> content;

    private List<Boolean> hasPendingComments;

    public ModifiedFilesViewListAdapter(Activity context, List<FileInfo> fileInfoList, List<Boolean> hasPendingComments) {
        super(context, R.layout.layout_modified_file_item, fileInfoList);
        this.context = context;
        this.content = fileInfoList;
        this.hasPendingComments = hasPendingComments;
    }

    public void updateHasPendingComments(List<Boolean> hasPendingComments){
        this.hasPendingComments = hasPendingComments;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View itemView = inflater.inflate(R.layout.layout_modified_file_item, null, true);

        if(hasPendingComments.get(position)){
            itemView.setBackgroundColor(Color.CYAN);
        }

        TextView fileName = (TextView) itemView.findViewById(R.id.fileName);
        ImageView added = (ImageView) itemView.findViewById(R.id.added);
        ImageView deleted = (ImageView) itemView.findViewById(R.id.deleted);


        FileInfo currentFileInfo = content.get(position);

        fileName.setText(currentFileInfo.getFileName());

        Integer maxAddedWidth = 80;
        Integer maxDeletedWidth = 80;
        try {
            Field maxWidthField = ImageView.class.getDeclaredField("mMaxWidth");
            maxWidthField.setAccessible(true);

            maxAddedWidth = (Integer) maxWidthField.get(added);
            maxDeletedWidth = (Integer) maxWidthField.get(deleted);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int insertedLines = currentFileInfo.getLinesInserted();
        added.getLayoutParams().width = maxAddedWidth < insertedLines ? maxAddedWidth : insertedLines;

        int deletedLines = currentFileInfo.getLinesDeleted();
        deleted.getLayoutParams().width = maxDeletedWidth < deletedLines ? maxDeletedWidth : deletedLines;

        return itemView;
    }
}
