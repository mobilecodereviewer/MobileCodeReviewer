package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.model.ChangeMessageInfo;

public class ChangeMessagesViewListAdapter extends ArrayAdapter<ChangeMessageInfo> {

    private Activity context;

    private List<ChangeMessageInfo> changeMessageInfos;

    public ChangeMessagesViewListAdapter(Activity context, List<ChangeMessageInfo> changeMessageInfos){
        super(context,R.layout.layout_change_message, changeMessageInfos);
        this.context = context;
        this.changeMessageInfos = changeMessageInfos;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View changeMessageView = inflater.inflate(R.layout.layout_change_message , null, true);
        TextView authorView = (TextView) changeMessageView.findViewById(R.id.changeMessageAuthorName);
        TextView dateView = (TextView) changeMessageView.findViewById(R.id.changeMessageDate);
        TextView messageContentView = (TextView) changeMessageView.findViewById(R.id.changeMessageContent);

        authorView.setText(changeMessageInfos.get(position).getAuthor());
        dateView.setText(changeMessageInfos.get(position).getDate());
        messageContentView.setText(changeMessageInfos.get(position).getMessage());

        return changeMessageView;
    }

}

