package com.farizdotid.samplemvpretrofitrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.farizdotid.samplemvpretrofitrxjava.model.Repo;
import com.farizdotid.samplemvpretrofitrxjava.model.ResponseRepos;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity
        implements MainContract.View {

    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;
    @BindView(R.id.rvRepos)
    RecyclerView rvRepos;

    Unbinder unbinder;
    MainContract.Presenter mPresenter;
    ReposAdapter mRepoAdapter;

    List<Repo> repoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        unbinder =  ButterKnife.bind(this);
        mPresenter = new MainPresenter(this);

        mRepoAdapter = new ReposAdapter(this, repoList, null);
        rvRepos.setLayoutManager(new LinearLayoutManager(this));
        rvRepos.setItemAnimator(new DefaultItemAnimator());
        rvRepos.setHasFixedSize(true);
        rvRepos.setAdapter(mRepoAdapter);

        etUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*
                EditorInfo.IME_ACTION_SEARCH ini berfungsi untuk men-set keyboard kamu
                agar enter di keyboard menjadi search.
                 */
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String username = etUsername.getText().toString();
                    mPresenter.requestRepos(username);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void isLoading(boolean isLoading) {
        if (isLoading){
            pbLoading.setVisibility(View.VISIBLE);
        } else {
            pbLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void isReposFetced(List<ResponseRepos> responseReposList) {
        for (int i = 0; i < responseReposList.size(); i++) {
            String name = responseReposList.get(i).getName();
            String description = responseReposList.get(i).getDescription();

            repoList.add(new Repo(name, description));
        }

        mRepoAdapter = new ReposAdapter(MainActivity.this, repoList, null);
        rvRepos.setAdapter(mRepoAdapter);
        mRepoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
