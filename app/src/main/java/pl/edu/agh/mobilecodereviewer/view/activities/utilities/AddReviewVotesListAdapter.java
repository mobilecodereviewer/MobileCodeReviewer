package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.model.AccountInfo;
import pl.edu.agh.mobilecodereviewer.model.PermittedLabel;

public class AddReviewVotesListAdapter extends ArrayAdapter<PermittedLabel> {

    private final Activity context;

    private final List<PermittedLabel> permittedLabels;

    private AccountInfo loggedUser;

    public AddReviewVotesListAdapter(Activity context, List<PermittedLabel> permittedLabels, AccountInfo loggedUser) {
        super(context, R.layout.layout_add_review_single_vote, permittedLabels);
        this.context = context;
        this.permittedLabels = permittedLabels;
        this.loggedUser = loggedUser;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View itemView = inflater.inflate(R.layout.layout_add_review_single_vote, null, true);

        PermittedLabel currentLabel = permittedLabels.get(position);

        TextView voteText = (TextView) itemView.findViewById(R.id.singleVoteText);
        voteText.setText(currentLabel.getName());

        prepareVotesSpinner(currentLabel, itemView);

        return itemView;
    }

    private View prepareVotesSpinner(PermittedLabel label, View itemView){
        Spinner spinner = (Spinner) itemView.findViewById(R.id.singleVoteSpinner);

        List<Integer> sortedValues = new ArrayList<Integer>(label.getValues());
        Collections.sort(sortedValues);

        ArrayAdapter<Integer> possibleVotesAdapter = new ArrayAdapter<Integer>(context,
                android.R.layout.simple_spinner_item, sortedValues);
        possibleVotesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(possibleVotesAdapter);
        spinner.setSelection(sortedValues.indexOf(0));

        return itemView;
    }
}
