package com.timotheemato.rssfeedaggregator.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.timotheemato.rssfeedaggregator.R;
import com.timotheemato.rssfeedaggregator.base.BaseFragment;
import com.timotheemato.rssfeedaggregator.base.Lifecycle;
import com.timotheemato.rssfeedaggregator.network.RequestManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment implements LoginContract.View {

    private LoginContract.ViewModel loginViewModel;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RequestManager requestManager =
                RequestManager.getInstance(getActivity().getApplicationContext());

        loginViewModel = new LoginViewModel(requestManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {

        return loginViewModel;
    }

    @OnClick(R.id.register_button)
    public void registerButtonTap(View view) {

    }

    @Override
    public void showMessage(Integer idMessage) {

        Toast.makeText(getActivity(), getString(idMessage), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void launchHomeActivity() {

    }
}
