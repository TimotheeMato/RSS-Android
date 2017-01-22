package com.timotheemato.rssfeedaggregator.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.timotheemato.rssfeedaggregator.R;
import com.timotheemato.rssfeedaggregator.base.BaseFragment;
import com.timotheemato.rssfeedaggregator.base.Lifecycle;
import com.timotheemato.rssfeedaggregator.network.RequestManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment implements LoginContract.View {

    private LoginContract.ViewModel loginViewModel;

    @BindView(R.id.email_field)
    EditText emailField;
    @BindView(R.id.password_field)
    EditText passwordField;
    @BindView(R.id.login_button)
    Button loginButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
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

    @OnClick(R.id.login_button)
    public void loginButtonTap(View view) {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        if (email.equals("") || password.equals("")) {
            showMessage("You need to fill every fields");
            return;
        }
        loginViewModel.login(email, password);
    }

    @OnClick(R.id.register_button)
    public void registerButtonTap(View view) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void launchHomeActivity() {

    }
}
