package com.irondev25.facultyachivementform.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irondev25.facultyachivementform.pojo.TokenPojo;
import com.irondev25.facultyachivementform.repository.LoginRepository;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepository loginRepository;
    private LiveData<TokenPojo> tokenLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        loginRepository = new LoginRepository();
        tokenLiveData = loginRepository.getTokenLiveData();
    }

    public void loginRequest(@NonNull String username, @NonNull String password){
        loginRepository.getToken(username,password);
    }

    public LiveData<TokenPojo> getTokenLiveData(){
        return tokenLiveData;
    }
}
