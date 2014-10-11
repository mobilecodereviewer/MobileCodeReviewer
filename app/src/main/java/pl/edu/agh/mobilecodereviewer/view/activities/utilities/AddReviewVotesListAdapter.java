package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.common.collect.Lists;

import org.objectweb.asm.Label;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.model.AccountInfo;
import pl.edu.agh.mobilecodereviewer.model.ApprovalInfo;
import pl.edu.agh.mobilecodereviewer.model.LabelInfo;

public class AddReviewVotesListAdapter extends ArrayAdapter<LabelInfo> {

    private final Activity context;

    private final List<LabelInfo> labelInfos;

    private AccountInfo loggedUser;

    public AddReviewVotesListAdapter(Activity context, List<LabelInfo> labelInfos, AccountInfo loggedUser) {
        super(context, R.layout.layout_add_review_single_vote, labelInfos);
        this.context = context;
        this.labelInfos = labelInfos;
        this.loggedUser = loggedUser;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View itemView = inflater.inflate(R.layout.layout_add_review_single_vote, null, true);

        LabelInfo currentLabel = labelInfos.get(position);
        List<ApprovalInfo> approvalInfos = currentLabel.getAll();

        TextView voteText = (TextView) itemView.findViewById(R.id.singleVoteText);
        voteText.setText(currentLabel.getName());

        for(ApprovalInfo approvalInfo : approvalInfos){
            if(approvalInfo.getAccountId().equals(loggedUser.getAccountId())){
                if(approvalInfo.getValue() != null) {
                    return authorizedLabel(currentLabel, itemView);
                } else {
                    return unauthorizedLabel(currentLabel, itemView);
                }
            }
        }

        return itemView;
    }

    private View unauthorizedLabel(LabelInfo label, View itemView){

        Spinner spinner = (Spinner) itemView.findViewById(R.id.singleVoteSpinner);
        spinner.setVisibility(View.GONE);
        TextView unauthorizedText = (TextView) itemView.findViewById(R.id.voteUnauthorized);
        unauthorizedText.setVisibility(View.VISIBLE);

        return itemView;
    }

    private View authorizedLabel(LabelInfo label, View itemView){
        Spinner spinner = (Spinner) itemView.findViewById(R.id.singleVoteSpinner);
        spinner.setVisibility(View.VISIBLE);

        List<Integer> sortedValues = new ArrayList<Integer>(label.getValues().keySet());
        Collections.sort(sortedValues);

        ArrayAdapter<Integer> possibleVotesAdapter = new ArrayAdapter<Integer>(context,
                android.R.layout.simple_spinner_item, sortedValues);
        possibleVotesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(possibleVotesAdapter);
        spinner.setSelection(sortedValues.indexOf(0));

        TextView unauthorizedText = (TextView) itemView.findViewById(R.id.voteUnauthorized);
        unauthorizedText.setVisibility(View.GONE);

        return itemView;
    }
}
