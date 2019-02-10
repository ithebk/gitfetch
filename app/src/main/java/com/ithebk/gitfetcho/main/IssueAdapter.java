package com.ithebk.gitfetcho.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ithebk.gitfetcho.R;
import com.ithebk.gitfetcho.models.IssueDataModel;

import java.util.List;
import java.util.Locale;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.IssueViewHolder> {
    private List<IssueDataModel> issueDataModels;
    private IssueAdapterConnector issueAdapterConnector;


    public IssueAdapter(List<IssueDataModel> issueDataModels, IssueAdapterConnector issueAdapterConnector) {
        this.issueDataModels = issueDataModels;
        this.issueAdapterConnector = issueAdapterConnector;
    }

    @NonNull
    @Override
    public IssueViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IssueViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_issue, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IssueViewHolder issueViewHolder, int i) {
        IssueDataModel issueDataModel = issueDataModels.get(i);
        ((TextView) issueViewHolder.itemView.findViewById(R.id.tv_issue_pr_number)).setText(String.format(Locale.getDefault(), "#%d", issueDataModel.getPrNumber()));
        ((TextView) issueViewHolder.itemView.findViewById(R.id.tv_issue_title)).setText(issueDataModel.getTitle());
        ((TextView) issueViewHolder.itemView.findViewById(R.id.tv_issue_user)).setText(issueDataModel.getUser());
        ((TextView) issueViewHolder.itemView.findViewById(R.id.tv_issue_patch_link)).setText(issueDataModel.getPathLink());

        ((TextView) issueViewHolder.itemView.findViewById(R.id.tv_issue_patch_link)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                issueAdapterConnector.onPatchLinkClick(issueDataModel.getPathLink());
            }
        });

    }

    @Override
    public int getItemCount() {
        System.out.println("Length Adapter:"+issueDataModels.size());
        return issueDataModels.size();
    }

    public interface IssueAdapterConnector {
        void onPatchLinkClick(String link);
    }

    class IssueViewHolder extends RecyclerView.ViewHolder {

        public IssueViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
