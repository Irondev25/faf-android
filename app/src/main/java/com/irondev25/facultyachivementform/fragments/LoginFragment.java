package com.irondev25.facultyachivementform.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.irondev25.facultyachivementform.PreferenceVariables;
import com.irondev25.facultyachivementform.R;
import com.irondev25.facultyachivementform.ViewModel.LoginViewModel;
import com.irondev25.facultyachivementform.pojo.TokenPojo;

public class LoginFragment extends Fragment implements PreferenceVariables {
    private LoginViewModel viewModel;
    public SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        viewModel.init();
        viewModel.getTokenLiveData().observe(this, new Observer<TokenPojo>() {
            @Override
            public void onChanged(TokenPojo token) {
                if(token.getToken() != null){
                    String currUser = sharedPreferences.getString(USERNAME,ANON);
                    if(currUser.equals(ANON)){
                        saveUser(token);
                        Toast.makeText(getContext(), currUser + " successfully logged in", Toast.LENGTH_SHORT).show();
                    }
                    else if(currUser.equals(token.getUsername())){
                        Toast.makeText(getContext(), currUser + " already logged in", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getContext(), currUser + " already logged in, logout first", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.login_fragment,container,false);
        EditText usernameEditText = v.findViewById(R.id.username);
        EditText passwordEditText = v.findViewById(R.id.password);
        Button loginButton = v.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameVal = usernameEditText.getText().toString();
                String passwordVal = passwordEditText.getText().toString();
                if(usernameVal != null && passwordVal != null){
                    if(usernameVal.length() == 0 || passwordVal.length() == 0){
                        if(usernameVal.length() == 0){
                            usernameEditText.setError("Enter Username");
                        }
                        if(passwordVal.length() == 0){
                            passwordEditText.setError("Enter Password");
                        }
                    }
                    else{
                        performLogin(usernameVal,passwordVal);
                    }

                }
                else{
                    if(usernameVal == null){
                        usernameEditText.setError("Enter username");
                    }
                    if(passwordVal == null){
                        passwordEditText.setError("Enter password");
                    }
                }
            }
        });

        loginButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("PREFS", "onLongClick: "+sharedPreferences.getString(USERNAME,ANON));
                return true;
            }
        });
        return v;
    }

    void saveUser(TokenPojo tokenPojo){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME,tokenPojo.getUsername());
        editor.putString(PASSWORD,tokenPojo.getPassword());
        editor.putString(TOKEN,tokenPojo.getToken());
        editor.apply();
    }

    void performLogin(@NonNull String username, @NonNull String password){
        viewModel.loginRequest(username,password);
    }
}
