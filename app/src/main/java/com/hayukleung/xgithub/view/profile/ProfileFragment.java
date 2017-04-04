package com.hayukleung.xgithub.view.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.hayukleung.xgithub.R;
import com.hayukleung.xgithub.di.HasComponent;
import com.hayukleung.xgithub.di.component.DaggerProfileComponent;
import com.hayukleung.xgithub.di.component.ProfileComponent;
import com.hayukleung.xgithub.di.module.ActivityModule;
import com.hayukleung.xgithub.model.GitHub;
import com.hayukleung.xgithub.presenter.ProfilePresenter;
import com.hayukleung.xgithub.view.XFragment;
import javax.inject.Inject;

/**
 * XGitHub
 * com.hayukleung.xgithub.view.profile
 * ProfileFragment.java
 *
 * by hayukleung
 * at 2017-04-03 20:53
 */

public class ProfileFragment extends XFragment<GitHub>
    implements ProfileView, HasComponent<ProfileComponent> {

  @Inject protected ProfilePresenter mProfilePresenter;
  @BindView(R.id.text) TextView mTextView;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getComponent().inject(this);
    mProfilePresenter.attachView(this);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mProfilePresenter.request(getGitHubApiModule());
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override public void onDestroy() {
    mProfilePresenter.detachView();
    super.onDestroy();
  }

  @Override public ProfileComponent getComponent() {
    return DaggerProfileComponent.builder()
        .appComponent(getBaseActivity().getAppComponent())
        .activityModule(new ActivityModule(getBaseActivity()))
        .build();
  }

  @Override protected int getContentView() {
    return R.layout.content_profile;
  }

  @Override protected View.OnClickListener getRetryListener() {
    return null;
  }

  @Override public void showContent(GitHub data) {
    super.showContent(data);
    Toast.makeText(getActivity(), "showContent", Toast.LENGTH_SHORT).show();
  }

  @OnClick({ R.id.text }) void onClick(View view) {

  }
}
