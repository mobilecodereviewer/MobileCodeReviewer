package pl.edu.agh.mobilecodereviewer.view.activities.tools;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;

public class SourceCodeViewList extends ArrayAdapter<String> {
    private final Activity context;
    private final List<String> content;
    private final List<Integer> hasComments;

    public SourceCodeViewList(Activity context,
                              List<String> content, List<Integer> hasComments) {
        super(context, R.layout.layout_source_code, content);
        this.context = context;
        this.content = content;
        this.hasComments = hasComments;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.layout_source_code , null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(content.get(position) );
        imageView.setImageResource( hasComments.get(position) );
        return rowView;
    }
}
