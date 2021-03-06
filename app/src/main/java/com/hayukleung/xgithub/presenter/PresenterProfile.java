package com.hayukleung.xgithub.presenter;

import com.hayukleung.xgithub.common.wrapper.XLog;
import com.hayukleung.xgithub.contract.ContractProfile;
import com.hayukleung.xgithub.di.module.GitHubApiModule;
import com.hayukleung.xgithub.model.GitHub;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

/**
 * XGitHub
 * com.hayukleung.xgithub.presenter.main
 * PresenterMain.java
 *
 * by hayukleung
 * at 2017-04-02 17:03
 */

public class PresenterProfile extends ContractProfile.IPresenterProfile {

  @Inject public PresenterProfile() {
  }

  public void request(GitHubApiModule gitHubApiModule, ObservableTransformer lifecycleTransformer) {

    // mCompositeDisposable.add(
    gitHubApiModule.providesGitHubApi()
        .api("hayukleung")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(lifecycleTransformer)
        .doOnSubscribe(new Consumer<Disposable>() {
          @Override public void accept(Disposable disposable) throws Exception {
            getMVPView().showLoading();
          }
        })
        .doOnTerminate(new Action() {
          @Override public void run() throws Exception {
            getMVPView().dismissLoading();
          }
        })
        .subscribeWith(new DisposableObserver<GitHub>() {
          @Override public void onNext(GitHub value) {
            checkViewAttached();
            getMVPView().showContent(value);
          }

          @Override public void onError(Throwable e) {
            XLog.e(e);
            getMVPView().dismissLoading();
          }

          @Override public void onComplete() {
            getMVPView().dismissLoading();
          }
        });
    // );
  }
}
