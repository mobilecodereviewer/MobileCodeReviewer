package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.model.ApprovalInfo;

public class ReviewersViewListAdapter extends ArrayAdapter<ApprovalInfo> {

    private final Activity context;

    private final List<ApprovalInfo> content;

    public ReviewersViewListAdapter(Activity context, List<ApprovalInfo> approvalInfos){
        super(context, R.layout.layout_reviewers_tab_reviewer_item, approvalInfos);
        this.context = context;
        this.content = approvalInfos;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View itemView = inflater.inflate(R.layout.layout_reviewers_tab_reviewer_item, null, true);

        TextView reviewerName = (TextView) itemView.findViewById(R.id.reviewersTabReviewerItemName);
        TextView textLabel = (TextView) itemView.findViewById(R.id.reviewersTabReviewerItemValueText);
        ImageView imageLabel = (ImageView) itemView.findViewById(R.id.reviewersTabReviewerItemValueImage);


        ApprovalInfo currentApprovalInfo = content.get(position);

        reviewerName.setText(currentApprovalInfo.getApproverName());

        if(currentApprovalInfo.isMaxValueForLabel()) {
            imageLabel.setImageResource(R.drawable.common_accept_icon);
        } else if(currentApprovalInfo.isMinValueForLabel()) {
            imageLabel.setImageResource(R.drawable.common_cancel_icon);
        } else {
            textLabel.setText(currentApprovalInfo.getValue().toString());
        }

        return itemView;
    }
}
