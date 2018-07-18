package com.farizdotid.samplemvpretrofitrxjava;

import com.farizdotid.samplemvpretrofitrxjava.model.ResponseRepos;

import java.util.List;

/**
 * Created by Fariz Ramadhan.
 * website : https://farizdotid.com/
 * github : https://github.com/farizdotid
 * linkedin : https://www.linkedin.com/in/farizramadhan/
 */
public interface MainContract {

    interface View {
        void isLoading(boolean isLoading);
        void showMessage(String message);
        void isReposFetced(List<ResponseRepos> responseReposList);
    }

    interface Presenter {
        void requestRepos(String username);
    }
}
