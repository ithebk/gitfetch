package com.ithebk.gitfetcho.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ithebk.gitfetcho.R;
import com.ithebk.gitfetcho.models.IssueDataModel;
import com.ithebk.gitfetcho.repo.RepoFragment;
import com.ithebk.gitfetcho.storage.Preferences;

import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements MainPresenter.MainView, RepoFragment.RepoConnector, IssueAdapter.IssueAdapterConnector {

    Preferences preferences;
    private MainPresenter mainPresenter;
    private TextView tvOrganisation;
    private TextView tvRepo;
    private RadioButton radioOpen;
    private RadioButton radioClosed;
    private FrameLayout loader;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOrganisation = findViewById(R.id.text_view_organisation);
        tvRepo = findViewById(R.id.text_view_repo);
        loader = findViewById(R.id.loader);
        recyclerView = findViewById(R.id.recyclerView);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioOpen = findViewById(R.id.radioOpen);
        radioClosed = findViewById(R.id.radioClosed);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        preferences = Preferences.getInstance();
        preferences.init(this);
        Realm realm = Realm.getDefaultInstance();
        mainPresenter = new MainPresenter(this, preferences, realm);
        mainPresenter.init();

        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.editRepo();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioOpen) {
                    mainPresenter.updateSelectedIssueType("open");
                }
                else {
                    mainPresenter.updateSelectedIssueType("closed");
                }
            }
        });


    }

    private void showRepoView(String orgName, String repoName) {
        RepoFragment repoFragment = RepoFragment.newInstance(orgName, repoName, this);
        repoFragment.show(getSupportFragmentManager(), "repo_fragment");
    }

    @Override
    public void init(String organisationName, String repoName) {
        tvOrganisation.setText(String.format("ORGANISATION : %s", organisationName));
        tvRepo.setText(String.format("REPO : %s", repoName));

    }

    @Override
    public void showAdapter(List<IssueDataModel> issueDataModels) {
        radioOpen.setText("Open");
        radioClosed.setText("Closed");
        if(issueDataModels.size()>0) {
            if(mainPresenter.getIssueType().equalsIgnoreCase("open")) {
                radioOpen.setText(String.format(Locale.getDefault(),"Open (%d)",issueDataModels.size()));
            }
            else {
                radioClosed.setText(String.format(Locale.getDefault(),"Closed (%d)",issueDataModels.size()));
            }
        }
        recyclerView.setAdapter(new IssueAdapter(issueDataModels, MainActivity.this));
    }

    @Override
    public void showRepoSelectorView(String organisationName, String repoName) {
        showRepoView(organisationName, repoName);
    }


    @Override
    public void showRepoSelectorView() {
        showRepoView(null, null);
    }

    @Override
    public void onSubmitRepo(String orgName, String repoName) {
        mainPresenter.updateRepoName(orgName, repoName);
    }

    @Override
    public void loading(boolean isLoading) {
        loader.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showAlert(String alert) {
        Toast.makeText(getApplicationContext(), alert, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPatchLinkClick(String link) {
        if(link!=null){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(browserIntent);
        }
    }
}
