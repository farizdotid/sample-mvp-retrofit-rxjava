package com.farizdotid.samplemvpretrofitrxjava;

import android.view.View;
import android.widget.Toast;

import com.farizdotid.samplemvpretrofitrxjava.apiservice.BaseApiService;
import com.farizdotid.samplemvpretrofitrxjava.apiservice.UtilsApi;
import com.farizdotid.samplemvpretrofitrxjava.model.ResponseRepos;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Fariz Ramadhan.
 * website : https://farizdotid.com/
 * github : https://github.com/farizdotid
 * linkedin : https://www.linkedin.com/in/farizramadhan/
 */
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private BaseApiService mApiService;

    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
        this.mApiService = UtilsApi.getAPIService();
    }

    @Override
    public void requestRepos(String username) {
        mView.isLoading(true);
        mApiService.requestRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ResponseRepos>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ResponseRepos> responseRepos) {
                        /*
                        onNext disini ketika data sudah masuk dan biasanya kita memasukan data API
                        ke lokal ataupun sesuai kebutuhan kamu. Di contoh ini data dari API Server dimasukan
                        dalam interface yang sudah kita set.
                         */
                        mView.isReposFetced(responseRepos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMessage(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mView.isLoading(false);
                        mView.showMessage("Berhasil mengambil data");
                    }
                });
    }
}
