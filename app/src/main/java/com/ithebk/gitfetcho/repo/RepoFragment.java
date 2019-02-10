package com.ithebk.gitfetcho.repo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ithebk.gitfetcho.R;
import com.ithebk.gitfetcho.main.MainPresenter;
import com.ithebk.gitfetcho.utils.Constants;
import com.ithebk.gitfetcho.utils.Utils;

public class RepoFragment extends BottomSheetDialogFragment implements RepoPresenter.RepoView {
    private static RepoConnector repoConnector;
    private RepoPresenter repoPresenter;
    EditText etOrgName;
    EditText etRepoName;

    public static RepoFragment newInstance(String orgName, String repoName, RepoConnector connector) {
        repoConnector = connector;
        Bundle args = new Bundle();
        args.putString(Constants.ORGANISATION_NAME, orgName);
        args.putString(Constants.REPO_NAME, repoName);
        RepoFragment fragment = new RepoFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(requireContext(), getTheme());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_repo, container, false);
        etOrgName = view.findViewById(R.id.edit_text_organisation);
        etRepoName = view.findViewById(R.id.edit_text_repo);
        repoPresenter = new RepoPresenter(this);
        repoPresenter.init(getArguments());

        view.findViewById(R.id.button_update_repo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orgName = etOrgName.getText().toString();
                String repoName = etRepoName.getText().toString();
                repoPresenter.updateRepo(orgName, repoName);
            }
        });
        return view;

    }


    @Override
    public void init(String orgName, String repoName) {
        etOrgName.setText(orgName);
        etRepoName.setText(repoName);
    }

    @Override
    public void onUpdateRepo(String orgName, String repoName) {
        RepoFragment.this.dismiss();
        repoConnector.onSubmitRepo(orgName, repoName);

    }

    @Override
    public void showAlert(String alert) {
        Toast.makeText(getContext(), alert, Toast.LENGTH_LONG).show();
    }

    public interface RepoConnector {
        void onSubmitRepo(String orgName, String repoName);
    }
}
